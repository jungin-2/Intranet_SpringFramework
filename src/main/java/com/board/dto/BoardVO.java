package com.board.dto;

public class BoardVO {

	private int seqno;
	private int seq;
	private String userid;
	private String mwriter;
	private String mtitle;
	private String mregdate;
	private String mcontent;
	private int hitno;
	private int likecnt;
	private int dislikecnt;

	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMwriter() {
		return mwriter;
	}
	public void setMwriter(String mwriter) {
		this.mwriter = mwriter;
	}
	public String getMtitle() {
		return mtitle;
	}
	public void setMtitle(String mtitle) {
		this.mtitle = mtitle;
	}
	public String getMregdate() {
		return mregdate;
	}
	public void setMregdate(String mregdate) {
		this.mregdate = mregdate;
	}
	public String getMcontent() {
		return mcontent;
	}
	public void setMcontent(String mcontent) {
		this.mcontent = mcontent;
	}
	public int getHitno() {
		return hitno;
	}
	public void setHitno(int hitno) {
		this.hitno = hitno;
	}
	public int getLikecnt() {
		return likecnt;
	}
	public void setLikecnt(int likecnt) {
		this.likecnt = likecnt;
	}
	public int getDislikecnt() {
		return dislikecnt;
	}
	public void setDislikecnt(int dislikecnt) {
		this.dislikecnt = dislikecnt;
	}
 
	
}
