package com.board.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.dto.AddressVO;
import com.board.dto.FileVO;
import com.board.dto.MemberVO;
import com.board.service.BoardService;
import com.board.service.MemberService;
import com.board.util.Page;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	Logger log = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	MemberService service;
	BoardService board_service;
	
	@Autowired //비밀번호 암호화 이존성 주입
	private BCryptPasswordEncoder pwdEncoder;
	
	//사용자 등록 화면 보기
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public void getMemberRegistry() throws Exception { }
	
	//사용자 등록 처리
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String postMemberRegistry(MemberVO member,
			@RequestParam("fileUpload") MultipartFile multipartFile ) {
		
		String path = "c:\\Repository\\profile\\";
		File targetFile;
		
		if(!multipartFile.isEmpty()) {
				
				String org_filename = multipartFile.getOriginalFilename();	
				String org_fileExtension = org_filename.substring(org_filename.lastIndexOf("."));	
				String stored_filename =  UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
								
				try {
					targetFile = new File(path + stored_filename);
					
					multipartFile.transferTo(targetFile);
					
					member.setOrg_filename(org_filename);
					System.out.println("사용자 파일 이름 : "+org_filename);
					member.setStored_filename(stored_filename);
					member.setFilesize(multipartFile.getSize());
																				
				} catch (Exception e ) { e.printStackTrace(); }
				
			
				
		}	
		String inputPassword = member.getPassword();
		String pwd = pwdEncoder.encode(inputPassword); 
		member.setPassword(pwd);
		service.memberInfoRegistry(member);
		return "redirect:/";
	}
	
	//사용자 등록 시 아이디 중복 확인
	@ResponseBody
	@RequestMapping(value="/idCheck",method=RequestMethod.POST)
	public int idCheck(@RequestParam("userid") String userid) throws Exception{
		
		int result = service.idCheck(userid);
		
		return result;
	}
	
	//로그인 화면 보기
	@RequestMapping(value="/loginCheck",method=RequestMethod.GET)
	public void getLogIn() { }
	
	//로그인 처리
	@RequestMapping(value="/loginCheck",method=RequestMethod.POST)

	public String postLogIn(MemberVO loginData,Model model,HttpSession session,
			RedirectAttributes rttr) {
		
		//아이디 존재 여부 확인
		if(service.idCheck(loginData.getUserid()) == 0) {
			rttr.addFlashAttribute("message", "ID_NOT_FOUND");
			return "redirect:/";
		}
		
		MemberVO member = service.login(loginData.getUserid());
		
		//패스워드 확인
		if(!pwdEncoder.matches(loginData.getPassword(),member.getPassword())) {
			rttr.addFlashAttribute("message", "PASSWORD_NOT_FOUND");
			return "redirect:/";
		}else {
		
		//로그인 날짜 등록
		service.logindateUpdate(loginData.getUserid());
		
		//세션 생성
		session.setMaxInactiveInterval(3600*7);
		session.setAttribute("userid", member.getUserid());
		session.setAttribute("username", member.getUsername());
		session.setAttribute("role", member.getRole());

		// <------------------- 과제 ------------------------> 
		//패스워드변경일이 30일이 경과되었는지 확인 후 리다이렉트로 welcome 또는 패스워드 변경 페이지로 이동
		HashMap<Integer,Integer> map = service.pwdModifyCheck(loginData.getUserid());
		int datediff = Integer.parseInt(String.valueOf(map.get("DATEDF")));
		int pwcheck = Integer.parseInt(String.valueOf(map.get("PWCHECK")));
		
		if(datediff > 30*pwcheck) //30일 넘었으면 패스워드 변경 공지 -> 예? pwCheckNotice : welcome
			return "redirect:/member/pwCheckNotice";
		else 
			return "redirect:/member/welcome";
		
		
		}

	}
	
	//비밀번호 변경 30일 초과 공지
	@RequestMapping(value="/pwCheckNotice", method=RequestMethod.GET)
	public void goPwCheckNotice() {}
	
	//비밀번호 변경 화면 
	@RequestMapping(value="/memberInfoPasswordModify", method=RequestMethod.GET)
	public void goModify() {}
	
	//비밀번호 변경 처리
	@RequestMapping(value="/memberInfoPasswordModify", method=RequestMethod.POST)
	public String pwdModifyNow(HttpServletRequest httpServletRequest,HttpSession session) {
		String id = (String)session.getAttribute("userid");
		String password = httpServletRequest.getParameter("userpassword1");		
		String pwd = pwdEncoder.encode(password); 	
		
		service.pwdModifyNow(pwd, id);		
		return "redirect:/";
	}
	
	//30일 뒤에 변경하기
	@RequestMapping(value="/memberInfoPasswordModifyAfter30", method=RequestMethod.GET)
	public void updatePwdAfter30(HttpSession session) {
		String userid = (String)session.getAttribute("userid");
		service.updatePwdAfter30(userid);
	}
	
	
	//welcome 페이지 정보 가져 오기
	@RequestMapping(value="/welcome",method=RequestMethod.GET)
	public void getWelcomeView(HttpSession session,Model model) {
		
		String userid = (String)session.getAttribute("userid");
		String username = (String)session.getAttribute("username");
		
		MemberVO member = service.welcomeView(userid);
		
		model.addAttribute("userid", userid);
		model.addAttribute("username", username);
		model.addAttribute("regdate", member.getRegdate());
		model.addAttribute("lastlogindate", member.getLastlogindate());
		model.addAttribute("lastlogoutdate", member.getLastlogoutdate());
		
	}
	
	//로그아웃
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public void getLogout(HttpSession session,Model model) {
		
		String userid = (String)session.getAttribute("userid");
		String username = (String)session.getAttribute("username");

		//로그 아웃 날짜 등록
		service.logoutUpdate(userid);
		
		model.addAttribute("userid", userid);
		model.addAttribute("username", username);
		
		session.invalidate(); //모든 세션 종료--> 로그아웃...
		
	}
	
	//사용자 정보 보기
	@RequestMapping(value="/memberInfo",method=RequestMethod.GET)
	public void gerMemberInfoView(Model model,HttpSession session) {
		
		String userid = (String)session.getAttribute("userid");
		MemberVO member = service.memberInfoView(userid);
		MemberVO member_date = service.welcomeView(userid);
		
		model.addAttribute("member", member);
		model.addAttribute("member_date", member_date);
		
	}
	
	// <------------------- 과제 ------------------------> 
	//jspBoard 및 servletBoard를 참고하여 아래의 기능을 구현 
	//사용자 기본 정보 변경 화면
	@RequestMapping(value="/memberInfoModify",method=RequestMethod.GET)
	public void goMemberInfoModfiy(Model model,HttpSession session) {
		String userid = (String)session.getAttribute("userid");
		MemberVO member = service.memberInfoView(userid);
		MemberVO member_date = service.welcomeView(userid);
		
		model.addAttribute("member", member);
		model.addAttribute("member_date", member_date);
	}
	
	//사용자 기본 정보 변경 처리 사진까지
	@RequestMapping(value="/memberInfoModify",method=RequestMethod.POST)
	public String memberInfoModify(MemberVO member,HttpServletRequest httpServletRequest, HttpSession session,@RequestParam("fileUpload") MultipartFile multipartFile ) {
		String userid=(String) session.getAttribute("userid");
		String username = httpServletRequest.getParameter("username");
		String zipcode = httpServletRequest.getParameter("zipcode");
		String address = httpServletRequest.getParameter("address");
		System.out.println("zipcode: "+zipcode+", address: "+address);
		String telno = httpServletRequest.getParameter("telno");
		System.out.println("telno: " +telno);
		String email = httpServletRequest.getParameter("email");
		
		String path = "c:\\Repository\\profile\\";
		File targetFile;
		
		//사진 변경 시 파일 지우고 새로운 파일 넣기
		if(!multipartFile.isEmpty()) {
			

				
				String org_filename = multipartFile.getOriginalFilename();	
				System.out.println(org_filename);
				String org_fileExtension = org_filename.substring(org_filename.lastIndexOf("."));	
				String stored_filename =  UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
								
				//기존 파일 삭제
				
				String ex_stored_filename = service.getFileName(userid);
				FileVO fileInfo = new FileVO();
				fileInfo.setStored_filename(ex_stored_filename);
				File file = new File(path + fileInfo.getStored_filename());
				System.out.println("삭제할 파일 경로 : "+path + fileInfo.getStored_filename());
				file.delete();
				
				
				
				try {
					targetFile = new File(path + stored_filename);
					
					multipartFile.transferTo(targetFile);
					
					member.setOrg_filename(org_filename);
					member.setStored_filename(stored_filename);
					member.setFilesize(multipartFile.getSize());
					System.out.println(org_filename);
						
					service.updateUserInfo(username, telno, email, zipcode, address, org_filename, stored_filename,userid);

																				
				} catch (Exception e ) { e.printStackTrace(); }
				
		}else {
			
			String stored_filename = service.getFileName(userid);
			service.updateUserInfo(username, telno, email, zipcode, address, null, stored_filename,userid);

		}
		return "redirect:/member/memberInfo";
	}



	//패스워드 변경 (위에서 함)
	
	//사용자 아이디 찾기
	//이름, 비밀번호 입력 화면
	@RequestMapping(value="/searchID", method=RequestMethod.GET)
	public void goSearchID() {}
	
	//아이디 찾기 결과
	@RequestMapping(value="/IDView", method=RequestMethod.POST)
	public void searchIDView(HttpServletRequest sr, Model model) {
		
		String userid = service.searchID(sr.getParameter("username"), sr.getParameter("telno"));
		model.addAttribute("userid",userid); //아이디 IDView.jsp로 보냄
	}
	
	
	//패스워드 찾기(패스워드 임시 생성)
	//비밀번호 찾기 화면
	@RequestMapping(value="/searchPassword", method=RequestMethod.GET)
	public void goSearchPassword() {}
	
	//아이디, 이름 일치하는 유저가 있는지 확인? 임시비밀번호발급 : searchPassword.jsp?check=fail
	@RequestMapping(value="/tempPWView", method=RequestMethod.GET)
	public void tempPW() {}
	
	@RequestMapping(value="/tempPWView", method=RequestMethod.POST)
	public String tempPW(HttpServletRequest sr, Model model) {
		
		//유저 맞는지 확인
		int id_check = service.isUser(sr.getParameter("userid"), sr.getParameter("username"));
		
		if(id_check==0)
			return "redirect:/member/searchPassword?check=fail";
		else {
			
			//임시비밀번호 발급
			//숫자 + 영문대소문자 8자리 임시패스워드 생성
			StringBuffer tempPW = new StringBuffer();
			
			Random rnd = new Random();
			for (int i = 0; i < 8; i++) {
			    int rIndex = rnd.nextInt(3);
			    switch (rIndex) {
			    case 0:
			        // a-z : 아스키코드 97~122
			    	tempPW.append((char) ((int) (rnd.nextInt(26)) + 97));
			        break;
			    case 1:
			        // A-Z : 아스키코드 65~122
			    	tempPW.append((char) ((int) (rnd.nextInt(26)) + 65));
			        break;
			    case 2:
			        // 0-9
			    	tempPW.append((rnd.nextInt(10)));
			        break;
			    }
			}
			
			//임시 비밀번호 화면에 띄우기
			model.addAttribute("tempPW", tempPW);
			
			//임시 비밀번호 암호화해 DB 저장
			String pwd = pwdEncoder.encode(tempPW); 	
			String id = sr.getParameter("userid");
			
			service.pwdModifyNow(pwd, id);		
		}
			
		return "member/tempPWView";
	}
	
	
	
	//회원탈퇴 - 등록한 게시글, 댓글, 좋아요/싫어요, 첨부파일(프로파일 이미지 포함) 삭제 , @Transaction 기능 활용
	//삭제 재확인
	@RequestMapping(value="/deleteMember", method=RequestMethod.GET)
	public void goDelete(HttpServletRequest sr, HttpSession session) {
	
	}
	
	//회원탈퇴 기능 제약조건으로 다 삭제됨. 저장된 실제 파일 삭제 (게시글 첨부파일, 사용자 프로필 파일)
	@RequestMapping(value="/deleteMemberProc", method=RequestMethod.GET)
	public String deleteMember(HttpServletRequest sr, HttpSession session) throws Exception{
		
		
		String path = "c:\\Repository\\profile\\";
	
		
		//프로필 사진 삭제
		
		String ex_stored_filename = service.getFileName((String)session.getAttribute("userid"));
		FileVO fileInfo = new FileVO();
		fileInfo.setStored_filename(ex_stored_filename);
		File file = new File(path + fileInfo.getStored_filename());
		System.out.println("삭제할 파일 경로 : "+path + fileInfo.getStored_filename());
		file.delete();
		
		
		//게시글에 첨부된 파일 삭제
		String board_path = "c:\\Repository\\file\\";
	

		
		List<FileVO> fileseq = service.deleteAccountFile((String)session.getAttribute("userid"));
		
	
		for(int i=0;i<fileseq.size();i++) {
			FileVO board_fileInfo = new FileVO();
			board_fileInfo.setStored_filename(fileseq.get(i).getStored_filename());
			
			File board_file = new File(board_path + board_fileInfo.getStored_filename());
			board_file.delete();
		}
		
		service.deleteMember((String)session.getAttribute("userid"));
		session.invalidate();
		
		return "redirect:/";
	}
	
	//우편번호 검색
	@RequestMapping(value="/addrSearch",method=RequestMethod.GET)
	public void getSearchAddr(@RequestParam("addrSearch") String addrSearch,
			@RequestParam("page") int pageNum,Model model) throws Exception {
		
		int postNum = 5;
		int startPoint = (pageNum -1)*postNum + 1; //테이블에서 읽어 올 행의 위치
		int endPoint = pageNum*postNum;
		int listCount = 5;
		
		Page page = new Page();
		
		int totalCount = service.addrTotalCount(addrSearch);
		List<AddressVO> list = new ArrayList<>();
		list = service.addrSearch(startPoint, endPoint, addrSearch);

		model.addAttribute("list", list);
		model.addAttribute("pageListView", page.getPageAddress(pageNum, postNum, listCount, totalCount, addrSearch));
		
	}
	
}
