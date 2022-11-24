package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.board.dto.BoardVO;

@Mapper
public interface BoardMapper {

	@Select("select seqno,mtitle,mwriter,hitno,mregdate from tbl_board order by seqno desc")
	public List<BoardVO> list();
	
}
