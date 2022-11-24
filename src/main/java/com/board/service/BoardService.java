package com.board.service;

import java.util.List;
import java.util.Map;

import com.board.dto.BoardVO;
import com.board.dto.FileVO;
import com.board.dto.LikeVO;
import com.board.dto.ReplyVO;

public interface BoardService {

	//�Խù� ��Ϻ���
	public List<BoardVO> list(int startPoint, int endPoint, String searchType,String keyword) throws Exception;

	//��ü �Խù� ���� ���
	public int totalCount(String searchType,String keyword) throws Exception;
	
	//�Խù� ���� ����
	public BoardVO view(int seqno) throws Exception;
	
	//�Խù� ��ȣ ���ϱ� - �������� Last Number ���
	public int getSeqnoWithLastNumber() throws Exception;
	
	//�Խù� ��ȣ ���ϱ� - �������� nexval ���
	public int getSeqnoWithNextval() throws Exception;
	
	//�Խù� ���� ����
	public int pre_seqno(int seqno,String searchType, String keyword) throws Exception;
	
	//�Խù� ���� ����
	public int next_seqno(int seqno,String searchType, String keyword) throws Exception;
		
	//���� ���ε� ���� ���
	public void fileInfoRegistry(Map<String,Object> fileInfo) throws Exception;
	
	//�Խñ� ������ ���ε�� ���� ��� ����
	public List<FileVO> fileListView(int seqno) throws Exception;
	
	//�Խù� ���
	public void write(BoardVO board) throws Exception;
	
	//�Խù� ����
	public void modify(String mtitle, String mcontent, int seqno) throws Exception;
	
	//�Խù� ����
	public void delete(int seqno) throws Exception;
	
	//���ƿ�/�Ⱦ�� Ȯ�� ���� ����
	public LikeVO likeCheckView(int seqno,String userid) throws Exception;
	
	//���ƿ�/�Ⱦ�� ���� �����ϱ�
	public void boardLikeUpdate(int seqno, int likecnt, int dislikecnt) throws Exception;
	
	//���ƿ�/�Ⱦ�� Ȯ�� ����ϱ�
	public void likeCheckRegistry(Map<String,Object> map) throws Exception;
	
	//���ƿ�/�Ⱦ�� Ȯ�� �����ϱ�
	public void likeCheckUpdate(Map<String,Object> map) throws Exception;
		
	//��� ����
	public List<ReplyVO> replyView(ReplyVO reply) throws Exception;
	
	//��� ����
	public void replyUpdate(ReplyVO reply) throws Exception;
	
	//��� ��� 
	public void replyRegistry(ReplyVO reply) throws Exception;
	
	//��� ����
	public void replyDelete(ReplyVO reply) throws Exception;
	
	//�Խù� ��ȸ�� ����
	public void hitnoUpgrade(int seqno) throws Exception;
	
	//÷������ ����
	public void deleteFile(int seqno) throws Exception;
	
	//�Խñ� ���� �� ���� ���� ����
	public void deleteBoardFile(int seqno) throws Exception;
	
	//���� �ٿ�ε� ���� 
	public FileVO fileInfo(int fileseqno) throws Exception;
	
	//fileseqno ��������
	public List<FileVO> fileSeqno(int seqno) throws Exception;
	

}
