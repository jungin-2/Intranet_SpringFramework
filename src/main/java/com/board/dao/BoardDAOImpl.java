package com.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.board.dto.BoardVO;
import com.board.dto.FileVO;
import com.board.dto.LikeVO;
import com.board.dto.ReplyVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSession sql;
	
	private static String namespace = "com.board.mappers.board";
	
	//게시물 목록 보기
	@Override
	public List<BoardVO> list(int startPoint,int endPoint, String searchType,String keyword) throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		data.put("startPoint", startPoint);
		data.put("endPoint", endPoint);
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		return sql.selectList(namespace + ".list",data); 
	}
	
	@Override
	//전체 게시물 갯수 계산
	public int totalCount(String searchType,String keyword) throws Exception{
		
		Map<String,String> data = new HashMap<>();
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		return sql.selectOne(namespace + ".totalCount", data);
	}
	
	//게시물 내용 보기
	@Override
	public BoardVO view(int seqno) throws Exception {

		return sql.selectOne(namespace + ".view", seqno);
	}

	//게시물 번호 구하기 - 시퀀스의 Last Number 사용
	@Override
	public int getSeqnoWithLastNumber() throws Exception {
		
		return sql.selectOne(namespace + ".getSeqnoWithLastNumber");
	}
	
	//게시물 번호 구하기 - 시퀀스의 nexval 사용
	@Override
	public int getSeqnoWithNextval() throws Exception {
		
		return sql.selectOne(namespace + ".getSeqnoWithNextval");
	}
	
	//게시물 이전 보기
	@Override
	public int pre_seqno(int seqno,String searchType, String keyword) throws Exception {
		Map<String,Object> data = new HashMap<>();
		data.put("seqno", seqno);
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		return sql.selectOne(namespace + ".pre_seqno", data);		
	}
	
	//게시물 다음 보기
	@Override
	public int next_seqno(int seqno,String searchType, String keyword) throws Exception {
		Map<String,Object> data = new HashMap<>();
		data.put("seqno", seqno);
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		return sql.selectOne(namespace + ".next_seqno", data);
	}
	
	//게시물 등록
	@Override
	public void write(BoardVO board) throws Exception {
		
		sql.insert(namespace + ".write", board);
	}

	//파일 업로드 정보 등록
	public void fileInfoRegistry(Map<String,Object> fileInfo) throws Exception {
		sql.insert(namespace + ".fileInfoRegistry", fileInfo);
	}
	
	//게시글 내에서 업로드된 파일 목록 보기
	public List<FileVO> fileListView(int seqno) throws Exception {
		return sql.selectList(namespace + ".fileListView", seqno);
	}
	
	//게시물 수정
	@Override
	public void modify(String mtitle, String mcontent, int seqno) throws Exception {
		Map<String,Object> data = new HashMap<>();
		data.put("mtitle", mtitle);
		data.put("mcontent", mcontent);
		data.put("seqno", seqno);

		sql.update(namespace + ".modify", data);
		
	}

	//게시물 삭제
	@Override
	public void delete(int seqno) throws Exception {
		sql.delete(namespace + ".delete", seqno);
		
	}

	//좋아요/싫어요 확인 가져 오기
	@Override
	public LikeVO likeCheckView(int seqno,String userid) throws Exception {
		
		Map<String,Object> data = new HashMap<>();
		data.put("seqno", seqno);
		data.put("userid", userid);
		return sql.selectOne(namespace + ".likeCheckView", data);
	}
	
	//좋아요/싫어요 갯수 수정하기
	public void boardLikeUpdate(int seqno, int likecnt, int dislikecnt) throws Exception {
		Map<String,Integer> data = new HashMap<>();
		data.put("seqno", seqno);
		data.put("likecnt", likecnt);
		data.put("dislikecnt", dislikecnt);
		sql.update(namespace + ".boardLikeUpdate", data);
	}
	
	//좋아요/싫어요 확인 등록하기
	public void likeCheckRegistry(Map<String,Object> map) throws Exception {
		sql.insert(namespace + ".likeCheckRegistry", map);
	}
	
	//좋아요/싫어요 확인 수정하기
	public void likeCheckUpdate(Map<String,Object> map) throws Exception {
		sql.update(namespace + ".likeCheckUpdate", map);
	}
	
	//댓글 보기
	public List<ReplyVO> replyView(ReplyVO reply) throws Exception{
		
		return sql.selectList(namespace + ".replyView", reply);
	}
	
	//댓글 수정
	public void replyUpdate(ReplyVO reply) throws Exception{
		sql.update(namespace + ".replyUpdate", reply);
	}
	
	//댓글 등록
	public void replyRegistry(ReplyVO reply) throws Exception{
		sql.insert(namespace + ".replyRegistry", reply);
	}
	
	//댓글 삭제
	public void replyDelete(ReplyVO reply) throws Exception{
		sql.delete(namespace + ".replyDelete", reply);
	}
	
	//게시물 조회수 증가
	public void hitnoUpgrade(int seqno) throws Exception{
		System.out.println("seqno = " + seqno);
		sql.update(namespace + ".hitnoUpgrade", seqno);
	}

	@Override
	public void deleteFile(int seqno) throws Exception {
		// TODO Auto-generated method stub
		sql.delete(namespace+".deleteFile", seqno);
	}

	public void deleteBoardFile(int seqno) throws Exception {
		// TODO Auto-generated method stub
		sql.delete(namespace+".deleteBoardFile",seqno);
	}

	@Override
	public FileVO fileInfo(int fileseqno) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace+".fileInfo", fileseqno);
	}

	@Override
	public List<FileVO> fileSeqno(int seqno) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(namespace+".fileSeqno", seqno);
	}


	
	
}
