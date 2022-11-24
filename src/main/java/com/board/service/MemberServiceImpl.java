package com.board.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.dao.MemberDAO;
import com.board.dto.AddressVO;
import com.board.dto.FileVO;
import com.board.dto.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO dao; 
	
	//아이디 확인
	@Override
	public int idCheck(String userid) {
		return dao.idCheck(userid);
	}

	//로그인 정보 확인
	@Override
	public MemberVO login(String userid) {
		return dao.login(userid);
	}

	//마지막 로그인 날짜 등록/수정
	@Override
	public void logindateUpdate(String userid) {
		dao.logindateUpdate(userid);
	}

	//welcome 페이지 정보 가져 오기 
	public MemberVO welcomeView(String userid) {
		return dao.welcomeView(userid);
	}

	//로그 아웃 날짜 등록
	@Override
	public void logoutUpdate(String userid) {
		dao.logoutUpdate(userid);
	}

	//사용자 등록
	@Override
	public void memberInfoRegistry(MemberVO member) {
		dao.memberInfoRegistry(member);
	}

	//사용자 정보 보기
	@Override
	public MemberVO memberInfoView(String userid) {
		return dao.memberInfoView(userid);
	}

	//우편번호 전체 갯수 가져 오기
	@Override
	public int addrTotalCount(String addrSearch) {
		return dao.addrTotalCount(addrSearch);
	}

	//우편번호 검색
	@Override
	public List<AddressVO> addrSearch(int startPoint, int endPoint, String addrSearch) {
		return dao.addrSearch(startPoint, endPoint, addrSearch);
	}

	//비밀번호 변경 30일 체크
	@Override
	public HashMap<Integer, Integer> pwdModifyCheck(String userid) {
		// TODO Auto-generated method stub
		return dao.pwdModifyCheck(userid);	
	}

	//30일 뒤 비번 변경
	@Override
	public void updatePwdAfter30(String userid) {
		// TODO Auto-generated method stub
		 dao.updatePwdAfter30(userid);
	}

	@Override
	public void pwdModifyNow(String password, String userid) {
		// TODO Auto-generated method stub
		dao.pwdModifyNow(password,userid);
	}

	@Override
//	public void updateUserInfo(MemberVO member, String userid) {
//		dao.updateUserInfo(member,userid);
//	}
	public void updateUserInfo(String username, String telno, String email, String zipcode, String address,String org_filename, String stored_filename,String userid) {
		// TODO Auto-generated method stub
		dao.updateUserInfo(username,telno,email,zipcode,address,org_filename,stored_filename,userid);
	}

	@Override
	public String searchID(String username, String telno) {
		// TODO Auto-generated method stub
		return dao.searchID(username, telno);
	}

	@Override
	public int isUser(String userid, String username) {
		// TODO Auto-generated method stub
		return dao.isUser(userid, username);
	}

	@Override
	public void saveTempPw(String password, String userid) {
		// TODO Auto-generated method stub
		dao.saveTempPw(password, userid);
	}

	@Override
	public void deleteMember(String userid) {
		// TODO Auto-generated method stub
		dao.deleteMember(userid);
	}

	@Override
	public String getFileName(String userid) {
		// TODO Auto-generated method stub
		return dao.getFileName(userid);
	}

	@Override
	public List<FileVO> deleteAccountFile(String userid) throws Exception {
		// TODO Auto-generated method stub
		return dao.deleteAccountFile(userid);
	}
}
