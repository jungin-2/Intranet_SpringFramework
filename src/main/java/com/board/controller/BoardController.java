package com.board.controller;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.board.dto.BoardVO;
import com.board.dto.FileVO;
import com.board.dto.LikeVO;
import com.board.dto.ReplyVO;
import com.board.service.BoardService;
import com.board.util.Page;

@Controller
public class BoardController {

	@Autowired
	BoardService service; //의존성 주입
	
	Logger log = LoggerFactory.getLogger(BoardController.class);
	
	//게시물 목록 보기
	@GetMapping("/board/list")
	public void GetList(Model model, @RequestParam(name="page") int pageNum, 
			@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
			@RequestParam(name="keyword", defaultValue="", required=false) String keyword ) throws Exception{
		
		int listCount = 5;
		int postNum = 5; //한 페이지에 보여질 게시물 목록 갯수
		int startPoint = (pageNum -1)*postNum + 1; 
		int endPoint = postNum*pageNum;
	
		Page page = new Page();
		int totalCount = service.totalCount(searchType, keyword);
				
		model.addAttribute("list", service.list(startPoint, endPoint, searchType, keyword));
		model.addAttribute("page", pageNum);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageListView", page.getPageList(pageNum, postNum, listCount, totalCount, searchType, keyword));
		
		
	}

	//게시물 내용 보기
	@GetMapping("/board/view")
	public void GetView(Model model,HttpSession session, @RequestParam("seqno") int seqno,@RequestParam(name="page") int pageNum,
			@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
			@RequestParam(name="keyword", defaultValue="", required=false) String keyword) throws Exception{
		
		BoardVO board = service.view(seqno);
		
		//조회수 증가
		String userid = (String)session.getAttribute("userid");
		if(!userid.equals(board.getUserid())) 
			service.hitnoUpgrade(seqno);

		LikeVO likeCheckView = service.likeCheckView(seqno, userid);
		
		//초기에 좋아요/싫어요 등록이 안되어져 있을 경우 "N"으로 초기화 
		
		if(likeCheckView == null) {
			model.addAttribute("myLikeCheck", "N");
			model.addAttribute("myDislikeCheck", "N");
		} else if(likeCheckView != null) {
			model.addAttribute("myLikeCheck", likeCheckView.getMylikecheck());
			model.addAttribute("myDislikeCheck", likeCheckView.getMydislikecheck());
		}
		model.addAttribute("view", board);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pre_seqno", service.pre_seqno(seqno, searchType, keyword));
		model.addAttribute("next_seqno",service.next_seqno(seqno, searchType, keyword));
		model.addAttribute("fileListView", service.fileListView(seqno));
		model.addAttribute("likeCheckView", likeCheckView);
	}
	
	//게시물 등록 화면 보기
	@GetMapping("/board/write")
	public void GetWrite() { }
	
	//첨부 파일 있는 게시물 등록
	@PostMapping("/board/writeWithFile")
	public String PostWriteWithFile(BoardVO board, HttpServletRequest request) throws Exception{
		
		log.info("<-------------- 첨부 파일 있음 ------------------->");
		int seqno = service.getSeqnoWithNextval();
		board.setSeqno(seqno);
		System.out.println("seqno: "+seqno);
		service.write(board);
		
		return "redirect:/board/list?page=1";
	}

	//첨부 파일 없는 게시물 등록
	@PostMapping("/board/write")
	public String PostWrite(BoardVO board) throws Exception{
		
		log.info("<-------------- 첨부 파일 없음 ------------------->");
		int seqno = service.getSeqnoWithNextval();
		board.setSeqno(seqno);
		
		service.write(board);
		
		return "redirect:/board/list?page=1";
	}
	
	//파일 다운로드
	@GetMapping("/board/fileDownload")
	public void fileDownload(@RequestParam(name="fileseqno" , required=false) int fileseqno, HttpServletResponse rs) throws Exception {
		
		String path = "c:\\Repository\\file\\";
		
		FileVO fileInfo = service.fileInfo(fileseqno);
		String org_filename = fileInfo.getOrg_filename();
		String stored_filename = fileInfo.getStored_filename();
		
		byte fileByte[] = FileUtils.readFileToByteArray(new File(path+stored_filename));
		
		rs.setContentType("application/octet-stream");
		rs.setContentLength(fileByte.length);
		rs.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(org_filename, "UTF-8")+"\";");
		rs.getOutputStream().write(fileByte);
		rs.getOutputStream().flush();
		rs.getOutputStream().close();
	}
		
	//파일 업로드
	@ResponseBody
	@PostMapping("/board/fileUpload")
	public void postFileUpload(@RequestParam("SendToFileList") List<MultipartFile> multipartFile, 
			@RequestParam("kind") String kind,@RequestParam(name="seqno", defaultValue="0", required=false) int seqno,
			HttpSession session, BoardVO board) throws Exception{
		
		log.info("파일 전송...");
		String path = "c:\\Repository\\file\\"; 
		String userid = (String)session.getAttribute("userid"); 
		if(kind.equals("I")) 
			seqno = service.getSeqnoWithLastNumber();
		else if(kind.equals("U"))
			seqno = board.getSeqno();
		
		
		File targetFile = null; 
		
		Map<String,Object> fileInfo = null;
		
		for(MultipartFile mpr:multipartFile) {
		
			String org_filename = mpr.getOriginalFilename();	
			String org_fileExtension = org_filename.substring(org_filename.lastIndexOf("."));	
			String stored_filename = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
			long filesize = mpr.getSize() ;
			
			log.info("org_filename={}", org_filename);
			log.info("stored_filename={}", stored_filename);
			
			targetFile = new File(path + stored_filename);
			mpr.transferTo(targetFile);
			
			fileInfo = new HashMap<>();
			fileInfo.put("seqno", seqno);
			fileInfo.put("userid", userid);
			fileInfo.put("org_filename",org_filename);
			fileInfo.put("stored_filename", stored_filename);
			fileInfo.put("filesize", filesize);
			System.out.println(seqno);

	
			service.fileInfoRegistry(fileInfo);

		}
	}
	
	//파일 삭제
	@ResponseBody
	@PostMapping("/board/fileDelete")	
	public void deleteFiles(@RequestParam(value = "deleteArr[]") int[] deletes) throws Exception{
		
		//String deletes = deletes.getParameterValues("deleteFileList");
//		System.out.println("삭제");
//		int[] delete = new int[deletes.length];
//		
//		for(int i=0;i<deletes.length;i++) {
//			delete[i] = Integer.parseInt(deletes[i]);
//		}
//		
//		for(int i=0;i<delete.length;i++) {
//			
//			service.deleteFile(delete[i]);
//			System.out.println("delete : "+delete[i]);
//		}
		

	}
	
	
	//게시물 수정 화면 보기
	@GetMapping("/board/modify")
	public void GetModify(Model model,@RequestParam("seqno") int seqno) 
			throws Exception {
		
		model.addAttribute("view", service.view(seqno));
		model.addAttribute("fileListView", service.fileListView(seqno));
	}
	
	@PostMapping("/board/modify")
	public String PostModify(HttpServletRequest request, @RequestParam("seqno") int seqno, @RequestParam("page") int page,
			@RequestParam(name="deleteFileList",required=false)int [] deleteFileList) throws Exception{
		
		
		// <------------------- 과제 ------------------------> 
		// 게시물 내용 수정, 추가된 첨부 파일 업로드, 선택한 첨부 파일 삭제
		String mtitle = request.getParameter("mtitle");
		String mcontent = request.getParameter("mcontent");
		
		//int page = Integer.parseInt(request.getParameter("page"));
		
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		
		service.modify(mtitle,mcontent,seqno);
			
		if(deleteFileList !=null) {
			
			String path = "c:\\Repository\\file\\";
			for(int i=0; i <deleteFileList.length; i++) {
				
				FileVO fileInfo = new FileVO();
				fileInfo = service.fileInfo(deleteFileList[i]);
				File file = new File(path + fileInfo.getStored_filename());
				file.delete();
			
				service.deleteFile(deleteFileList[i]);
			}
	}
		return "redirect:/board/view?seqno="+seqno+"&page="+page;
	}
	
	//게시물 삭제
	@GetMapping("/board/delete")
	public String GetDelete(@RequestParam("seqno") int seqno) throws Exception{

		// <------------------- 과제 ------------------------> 
		// 게시물 삭제 시 댓글, 첨부파일, 좋아요/싫어요 정보 삭제
		//@Transaction 사용

		
		//실제 파일 폴더에서 지우기
		//int[] fileseqno = service.fileSeqno(seqno);
		
		String path = "c:\\Repository\\file\\";
		
		List<FileVO> fileseq = service.fileSeqno(seqno);
		
		
		
		
		for(int i=0;i<fileseq.size();i++) {
			FileVO fileInfo = new FileVO();
			fileInfo = service.fileInfo(fileseq.get(i).getFileseqno());
			//System.out.println("파일인포 : "+service.fileInfo(fileseqno.indexOf(fileseqno)));
			File file = new File(path + fileInfo.getStored_filename());
			file.delete();
		}

		service.delete(seqno);
		service.deleteBoardFile(seqno);
		service.deleteFile(seqno);
		return "redirect:/board/list?page=1";
	}

	//좋아요/싫어요 관리
	@ResponseBody
	@PostMapping(value = "/board/likeCheck")
	public Map<String, Object> postLikeCheck(@RequestBody Map<String, Object> likeCheckData) throws Exception {
		
		int seqno = (int)likeCheckData.get("seqno");
		String userid = (String)likeCheckData.get("userid");
		int checkCnt = (int)likeCheckData.get("checkCnt");

		//현재 날짜, 시간 구해서 좋아요/싫어요 한 날짜/시간 입력 및 수정
		String likeDate = "";
		String dislikeDate = "";
		if(likeCheckData.get("mylikecheck").equals("Y")) 
			likeDate = LocalDateTime.now().toString();
		else if(likeCheckData.get("mydislikecheck").equals("Y")) 
			dislikeDate = LocalDateTime.now().toString();

		likeCheckData.put("likedate", likeDate);
		likeCheckData.put("dislikedate", dislikeDate);

		//TBL_LIKE 테이블 입력/수정
		LikeVO likeCheckView = service.likeCheckView(seqno,userid);
		if(likeCheckView == null) service.likeCheckRegistry(likeCheckData);
			else service.likeCheckUpdate(likeCheckData);

		//TBL_BOARD 내의 likecnt,dislikecnt 입력/수정 
		BoardVO board = service.view(seqno);
		
		int likeCnt = board.getLikecnt();
		int dislikeCnt = board.getDislikecnt();
			
		switch(checkCnt){
	    	case 1 : likeCnt --; break;
	    	case 2 : likeCnt ++; dislikeCnt --; break;
	    	case 3 : likeCnt ++; break;
	    	case 4 : dislikeCnt --; break;
	    	case 5 : likeCnt --; dislikeCnt ++; break;
	    	case 6 : dislikeCnt ++; break;
		}
		
		service.boardLikeUpdate(seqno,likeCnt,dislikeCnt);
		
		//AJAX에 전달할 map JSON 데이터 만들기
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seqno", seqno);
		map.put("likeCnt", likeCnt);
		map.put("dislikeCnt", dislikeCnt);
		
		return map;
	}
	
	//댓글 처리
	@ResponseBody
	@PostMapping("/board/reply")
	public List<ReplyVO> postReply(ReplyVO reply,@RequestParam("option") String option)throws Exception{
		
		switch(option) {
		
		case "I" : service.replyRegistry(reply); //댓글 등록
				   break;
		case "U" : 
					service.replyUpdate(reply); //댓글 수정
				   break;
		case "D" : service.replyDelete(reply); //댓글 삭제
				   break;
		}

		return service.replyView(reply);
	}
}
