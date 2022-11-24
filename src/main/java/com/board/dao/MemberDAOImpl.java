package com.board.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.board.dto.AddressVO;
import com.board.dto.FileVO;
import com.board.dto.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{

	@Autowired
	SqlSession sql;
	
	private static String namespace = "com.board.mappers.member";
	
	//아이디 확인
	@Override
	public int idCheck(String userid) {
		return sql.selectOne("com.board.mappers.member.idCheck", userid);
	}

	//로그인 정보 확인
	@Override
	public MemberVO login(String userid) {
		return sql.selectOne(namespace + ".login", userid);
	}

	//로그인 시 마지막 로그인 날짜 등록
	@Override
	public void logindateUpdate(String userid) {
		sql.update(namespace + ".logindateUpdate", userid);
	}	

	//welcome 페이지 정보 가져 오기 
		public MemberVO welcomeView(String userid) {
			return sql.selectOne(namespace + ".welcomeView", userid);
		}

	//로그 아웃 날짜 등록
	@Override
	public void logoutUpdate(String userid) {
		sql.insert(namespace + ".logoutUpdate",userid);
		
	}

	//사용자 정보 등록
	@Override
	public void memberInfoRegistry(MemberVO member) {
		sql.insert(namespace + ".memberInfoRegistry", member);
	}

	//사용자 정보 보기
	@Override
	public MemberVO memberInfoView(String userid) {
		return sql.selectOne(namespace + ".memberInfoView", userid);
	}

	//우편번호 최대 행수 계산
	@Override
	public int addrTotalCount(String addrSearch) {
		return sql.selectOne(namespace+".addrTotalCount",addrSearch);
	}

	//우편번호 검색 15
	@Override
	public List<AddressVO> addrSearch(int startPoint, int endPoint, String addrSearch) {

		Map<String,Object> data = new HashMap<>();
		data.put("startPoint", startPoint);
		data.put("endPoint",endPoint);
		data.put("addrSearch", addrSearch);
		
		return sql.selectList(namespace + ".addrSearch", data);
	}

	@Override
	public HashMap<Integer, Integer> pwdModifyCheck(String userid) {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace+".pwdModifyCheck", userid);
	}

	@Override
	public void updatePwdAfter30(String userid) {
		// TODO Auto-generated method stub
		sql.update(namespace + ".updatePwdAfter30", userid);

	}

	@Override
	public void pwdModifyNow(String password, String userid) {
		// TODO Auto-generated method stub
		Map<String,String> data = new HashMap<>();
		data.put("userid", userid);
		data.put("password", password);
		
		sql.update(namespace+".pwdModifyNow", data);
	}

	@Override
//	public void updateUserInfo(MemberVO member,String userid) {
//		Map<String,Object> data = new HashMap<>();
//		data.put("member", member);
//		data.put("userid", userid);
//		sql.update(namespace+".updateUserInfo",data);
//	}
	public void updateUserInfo(String username, String telno, String email, String zipcode, String address,String org_filename, String stored_filename,String userid) {
		// TODO Auto-generated method stub
		Map<String,String> data = new HashMap<>();
		data.put("username", username);
		data.put("telno", telno);
		data.put("email", email);
		data.put("zipcode", zipcode);
		data.put("address", address);	
		data.put("org_filenmae", org_filename);
		data.put("stored_filename", stored_filename);
		data.put("userid", userid);
		sql.update(namespace+".updateUserInfo", data);
		
	}

	@Override
	public String searchID(String username, String telno) {
		// TODO Auto-generated method stub
		Map<String,String> data = new HashMap<>();
		data.put("username", username);
		data.put("telno", telno);
		
		return sql.selectOne(namespace+".searchID",data);
	}

	@Override
	public int isUser(String userid, String username) {
		// TODO Auto-generated method stub
		Map<String,String> data = new HashMap<>();
		data.put("userid", userid);
		data.put("username", username);
		return sql.selectOne(namespace+".isUser",data);
	}

	@Override
	public void saveTempPw(String password, String userid) {
		// TODO Auto-generated method stub
		Map<String,String> data = new HashMap<>();
		data.put("password", password);
		data.put("userid", userid);
		
		sql.update(namespace+".saveTempPw",data);
		
	}

	@Override
	public void deleteMember(String userid) {
		// TODO Auto-generated method stub
		sql.delete(namespace+".deleteMember",userid);
	}

	@Override
	public String getFileName(String userid) {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace+".getFileName", userid);
	}
	
	@Override
	public List<FileVO> deleteAccountFile(String userid) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(namespace+".deleteAccountFile", userid);
	}
}
