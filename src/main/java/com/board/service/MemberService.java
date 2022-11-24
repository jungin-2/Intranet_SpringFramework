package com.board.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import com.board.dto.AddressVO;
import com.board.dto.FileVO;
import com.board.dto.MemberVO;


public interface MemberService {

	//아이디 확인
	public int idCheck(String userid); 

	//로그인 정보 확인
	public MemberVO login(String userid); 
	
	//마지막 로드인 시간 등록
	public void logindateUpdate(String userid);
	
	//welcome 페이지 정보 가져 오기 
	public MemberVO welcomeView(String userid);

	//로그아웃 날짜 업데이트
	public void logoutUpdate(String userid);

	//사용자 정보 등록
	public void memberInfoRegistry(MemberVO member);
	
	//사용자 정보 보기
	public MemberVO memberInfoView(String userid);
	
	//주소 전체 갯수 계산
	public int addrTotalCount(String addrSearch);
	
	//주소 검색
	public List<AddressVO> addrSearch(int startPoint, int endPoint, String addrSearch);
	
	//비밀번호 변경 30일 확인
	public HashMap<Integer, Integer> pwdModifyCheck(String userid);
	
	//비번 30일 뒤에 변경하기
	public void updatePwdAfter30(String userid);
	
	//비번 지금 변경하기
	public void pwdModifyNow(String password, String userid);
	
	//사용자 정보 변경하기
	public void updateUserInfo(String username, String telno, String email, String zipcode, String address,String org_filename, String stored_filename,String userid);
	//public void updateUserInfo(MemberVO member, String userid);
	
	//아이디 찾기
	public String searchID(String username, String telno);
	
	//비밀번호 찾기 - 유저가 맞는지
	public int isUser(String userid, String username);
	
	//유저 맞으면 임시비밀번호 발급
	public void saveTempPw(String password, String userid);
	
	//회원 탈퇴
	public void deleteMember(String userid);
	
	//파일 이름 가져오기
	public String getFileName(String userid);
	
	//탈퇴 시 삭제할 첨부 파일
	public List<FileVO> deleteAccountFile(String userid) throws Exception;
}
