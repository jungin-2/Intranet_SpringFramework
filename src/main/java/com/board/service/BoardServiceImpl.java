package com.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.board.dao.BoardDAO;
import com.board.dto.BoardVO;
import com.board.dto.FileVO;
import com.board.dto.LikeVO;
import com.board.dto.ReplyVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO dao; 
	
	//�Խù� ��� ����
	@Override
	public List<BoardVO> list(int startPoint, int endPoint, String searchType,String keyword) throws Exception {
		
		return dao.list(startPoint,endPoint,searchType,keyword);
	}
	
	//��ü �Խù� ���� ���
	@Override
	public int totalCount(String searchType,String keyword) throws Exception{
		return dao.totalCount(searchType, keyword);
	}

	//�Խù� ���� ����
	@Override
	public BoardVO view(int seqno) throws Exception {
		
		return dao.view(seqno);
	}

	//�Խù� ��� 
	@Override
	public void write(BoardVO board) throws Exception {
		dao.write(board);		
	}

	//�Խù� ��ȣ ���ϱ� - �������� Last Number ���
	public int getSeqnoWithLastNumber() throws Exception {
		return dao.getSeqnoWithLastNumber();
	}
	
	//�Խù� ��ȣ ���ϱ� - �������� nexval ���
	public int getSeqnoWithNextval() throws Exception {
		return dao.getSeqnoWithNextval();
	}

	//�Խù� ���� ����
	public int pre_seqno(int seqno,String searchType, String keyword) throws Exception {
		return dao.pre_seqno(seqno, searchType, keyword);
	}
	
	//�Խù� ���� ����
	public int next_seqno(int seqno,String searchType, String keyword) throws Exception {
		return dao.next_seqno(seqno, searchType, keyword);
	}
	
	//���� ���ε� ���� ���
	public void fileInfoRegistry(Map<String,Object> fileInfo) throws Exception {
		dao.fileInfoRegistry(fileInfo);
	}
	
	//�Խñ� ������ ���ε�� ���� ��� ����
	public List<FileVO> fileListView(int seqno) throws Exception {
		return dao.fileListView(seqno);
	}
	
	//�Խù� ����
	
	public void modify(String mtitle, String mcontent, int seqno) throws Exception {
		dao.modify(mtitle,mcontent,seqno);		
	}

	//�Խù� ����
	@Override
	public void delete(int seqno) throws Exception {
		dao.delete(seqno);		
	}

	//���ƿ�/�Ⱦ�� Ȯ�� ���� ����
	@Override
	public LikeVO likeCheckView(int seqno,String userid) throws Exception {
		return dao.likeCheckView(seqno, userid);
	}
	
	//���ƿ�/�Ⱦ�� ���� �����ϱ�
	public void boardLikeUpdate(int seqno, int likecnt, int dislikecnt) throws Exception {
		dao.boardLikeUpdate(seqno, likecnt, dislikecnt);
	}
	
	//���ƿ�/�Ⱦ�� Ȯ�� ����ϱ�
	public void likeCheckRegistry(Map<String,Object> map) throws Exception {
		dao.likeCheckRegistry(map);
	}
	
	//���ƿ�/�Ⱦ�� Ȯ�� �����ϱ�
	public void likeCheckUpdate(Map<String,Object> map) throws Exception {
		dao.likeCheckUpdate(map);
	}
	
	//��� ����
	public List<ReplyVO> replyView(ReplyVO reply) throws Exception{
		return dao.replyView(reply);
	}
	
	//��� ����
	public void replyUpdate(ReplyVO reply) throws Exception{
		dao.replyUpdate(reply);
	}
	
	//��� ��� 
	public void replyRegistry(ReplyVO reply) throws Exception{
		dao.replyRegistry(reply);
	}
	
	//��� ����
	public void replyDelete(ReplyVO reply) throws Exception{
		dao.replyDelete(reply);
	}
	
	//�Խù� ��ȸ�� ����
	public void hitnoUpgrade(int seqno) throws Exception{
		dao.hitnoUpgrade(seqno);				
	}

	@Override
	public void deleteFile(int seqno) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteFile(seqno);
	}

	@Override
	public void deleteBoardFile(int seqno) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteBoardFile(seqno);
	}

	@Override
	public FileVO fileInfo(int fileseqno) throws Exception {
		// TODO Auto-generated method stub
		return dao.fileInfo(fileseqno);
	}

	@Override
	public List<FileVO> fileSeqno(int seqno) throws Exception {
		// TODO Auto-generated method stub
		return dao.fileSeqno(seqno);
	}







	
}
