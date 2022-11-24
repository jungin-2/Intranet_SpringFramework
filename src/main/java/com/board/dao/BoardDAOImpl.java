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
	
	//�Խù� ��� ����
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
	//��ü �Խù� ���� ���
	public int totalCount(String searchType,String keyword) throws Exception{
		
		Map<String,String> data = new HashMap<>();
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		return sql.selectOne(namespace + ".totalCount", data);
	}
	
	//�Խù� ���� ����
	@Override
	public BoardVO view(int seqno) throws Exception {

		return sql.selectOne(namespace + ".view", seqno);
	}

	//�Խù� ��ȣ ���ϱ� - �������� Last Number ���
	@Override
	public int getSeqnoWithLastNumber() throws Exception {
		
		return sql.selectOne(namespace + ".getSeqnoWithLastNumber");
	}
	
	//�Խù� ��ȣ ���ϱ� - �������� nexval ���
	@Override
	public int getSeqnoWithNextval() throws Exception {
		
		return sql.selectOne(namespace + ".getSeqnoWithNextval");
	}
	
	//�Խù� ���� ����
	@Override
	public int pre_seqno(int seqno,String searchType, String keyword) throws Exception {
		Map<String,Object> data = new HashMap<>();
		data.put("seqno", seqno);
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		return sql.selectOne(namespace + ".pre_seqno", data);		
	}
	
	//�Խù� ���� ����
	@Override
	public int next_seqno(int seqno,String searchType, String keyword) throws Exception {
		Map<String,Object> data = new HashMap<>();
		data.put("seqno", seqno);
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		return sql.selectOne(namespace + ".next_seqno", data);
	}
	
	//�Խù� ���
	@Override
	public void write(BoardVO board) throws Exception {
		
		sql.insert(namespace + ".write", board);
	}

	//���� ���ε� ���� ���
	public void fileInfoRegistry(Map<String,Object> fileInfo) throws Exception {
		sql.insert(namespace + ".fileInfoRegistry", fileInfo);
	}
	
	//�Խñ� ������ ���ε�� ���� ��� ����
	public List<FileVO> fileListView(int seqno) throws Exception {
		return sql.selectList(namespace + ".fileListView", seqno);
	}
	
	//�Խù� ����
	@Override
	public void modify(String mtitle, String mcontent, int seqno) throws Exception {
		Map<String,Object> data = new HashMap<>();
		data.put("mtitle", mtitle);
		data.put("mcontent", mcontent);
		data.put("seqno", seqno);

		sql.update(namespace + ".modify", data);
		
	}

	//�Խù� ����
	@Override
	public void delete(int seqno) throws Exception {
		sql.delete(namespace + ".delete", seqno);
		
	}

	//���ƿ�/�Ⱦ�� Ȯ�� ���� ����
	@Override
	public LikeVO likeCheckView(int seqno,String userid) throws Exception {
		
		Map<String,Object> data = new HashMap<>();
		data.put("seqno", seqno);
		data.put("userid", userid);
		return sql.selectOne(namespace + ".likeCheckView", data);
	}
	
	//���ƿ�/�Ⱦ�� ���� �����ϱ�
	public void boardLikeUpdate(int seqno, int likecnt, int dislikecnt) throws Exception {
		Map<String,Integer> data = new HashMap<>();
		data.put("seqno", seqno);
		data.put("likecnt", likecnt);
		data.put("dislikecnt", dislikecnt);
		sql.update(namespace + ".boardLikeUpdate", data);
	}
	
	//���ƿ�/�Ⱦ�� Ȯ�� ����ϱ�
	public void likeCheckRegistry(Map<String,Object> map) throws Exception {
		sql.insert(namespace + ".likeCheckRegistry", map);
	}
	
	//���ƿ�/�Ⱦ�� Ȯ�� �����ϱ�
	public void likeCheckUpdate(Map<String,Object> map) throws Exception {
		sql.update(namespace + ".likeCheckUpdate", map);
	}
	
	//��� ����
	public List<ReplyVO> replyView(ReplyVO reply) throws Exception{
		
		return sql.selectList(namespace + ".replyView", reply);
	}
	
	//��� ����
	public void replyUpdate(ReplyVO reply) throws Exception{
		sql.update(namespace + ".replyUpdate", reply);
	}
	
	//��� ���
	public void replyRegistry(ReplyVO reply) throws Exception{
		sql.insert(namespace + ".replyRegistry", reply);
	}
	
	//��� ����
	public void replyDelete(ReplyVO reply) throws Exception{
		sql.delete(namespace + ".replyDelete", reply);
	}
	
	//�Խù� ��ȸ�� ����
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
