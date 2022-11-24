package com.board.dto;

import java.time.LocalDateTime;

public class MemberVO {

	private String userid;
	private String username;
	private String password;
	private String telno;
	private String email;
	private String zipcode;
	private String address;
	private LocalDateTime regdate;
	private String lastlogindate;
	private String lastlogoutdate;
	private LocalDateTime lastpwdate;
	private int pwcheck;
	private String role;
	private String org_filename;
	private String stored_filename;
	private long filesize;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDateTime getRegdate() {
		return regdate;
	}
	public void setRegdate(LocalDateTime regdate) {
		this.regdate = regdate;
	}
	public String getLastlogindate() {
		return lastlogindate;
	}
	public void setLastlogindate(String lastlogindate) {
		this.lastlogindate = lastlogindate;
	}
	public String getLastlogoutdate() {
		return lastlogoutdate;
	}
	public void setLastlogoutdate(String lastlogoutdate) {
		this.lastlogoutdate = lastlogoutdate;
	}
	public LocalDateTime getLastpwdate() {
		return lastpwdate;
	}
	public void setLastpwdate(LocalDateTime lastpwdate) {
		this.lastpwdate = lastpwdate;
	}
	public int getPwcheck() {
		return pwcheck;
	}
	public void setPwcheck(int pwcheck) {
		this.pwcheck = pwcheck;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOrg_filename() {
		return org_filename;
	}
	public void setOrg_filename(String org_filename) {
		this.org_filename = org_filename;
	}
	public String getStored_filename() {
		return stored_filename;
	}
	public void setStored_filename(String stored_filename) {
		this.stored_filename = stored_filename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	
}	