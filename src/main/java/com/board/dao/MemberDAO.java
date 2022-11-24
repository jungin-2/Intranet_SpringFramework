package com.board.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.board.dto.AddressVO;
import com.board.dto.FileVO;
import com.board.dto.MemberVO;

public interface MemberDAO {

	//���̵� Ȯ��
	public int idCheck(String userid); 

	//�α��� ���� Ȯ��
	public MemberVO login(String userid); 

	//������ �α��� �ð� ���
	public void logindateUpdate(String userid);
	
	//welcome ������ ���� ���� ���� 
	public MemberVO welcomeView(String userid);

	//�α׾ƿ� ��¥ ������Ʈ
	public void logoutUpdate(String userid);

	//����� ���� ���
	public void memberInfoRegistry(MemberVO member);

	//����� ���� ����
	public MemberVO memberInfoView(String userid);

	//�ּ� ��ü ���� ���
	public int addrTotalCount(String addrSearch);

	//�ּ� �˻�
	public List<AddressVO> addrSearch(int startPoint, int endPoint, String addrSearch);
	
	//��й�ȣ ���� 30�� Ȯ��
	public HashMap<Integer,Integer> pwdModifyCheck(String userid);
	
	//��� 30�� �ڿ� �����ϱ�
	public void updatePwdAfter30(String userid);
	
	//��� ���� �����ϱ�
	public void pwdModifyNow(String password, String userid);
	
	//����� ���� �����ϱ�
	public void updateUserInfo(String username, String telno, String email, String zipcode, String address,String org_filename, String stored_filename,String userid);
	//public void updateUserInfo(MemberVO member, String userid);
	
	//���̵� ã��
	public String searchID(String username, String telno);
	
	//��й�ȣ ã�� - ������ �´���
	public int isUser(String userid, String username);
	
	//���� ������ �ӽú�й�ȣ �߱�
	public void saveTempPw(String password, String userid);
	
	//ȸ�� Ż��
	public void deleteMember(String userid);
	
	//���� �̸� ��������
	public String getFileName(String userid);
	
	//Ż�� �� ������ ÷�� ����
	public List<FileVO> deleteAccountFile(String userid) throws Exception;
	
	
}
