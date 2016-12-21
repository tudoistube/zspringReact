package org.yex01.domain;
//...174p.
//...175p.root-context.xml NameSpace 선택.

import java.util.Date;

/***
 * create table ZTBL_BOARD
(
	BNO	int not null auto_increment
    ,TITLE	varchar(200) not null
    ,CONTENT text null
    ,WRITER	varchar(50) not null
    ,REGDATE	timestamp not null	default	now()
    #,UPDATEDATE	timestamp	default	now()
    ,VIEW_COUNT	int default 0
    ,primary key(BNO)
);
 * @author Administrator
 *
 */

public class BoardVO4JSON {
	
	private Integer bno;
	private String title;
	private String content;
	private String writer;	
		
	public BoardVO4JSON(Integer bno, String title, String content, String writer) {
		super();
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.writer = writer;
	}
	
	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title 
				+ ", content=" + content + ", writer=" + writer + "]";
	}	
	
	

}
