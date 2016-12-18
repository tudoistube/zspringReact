package org.jone.zdomain;
//...과제22-5.A. DB : ZBOOK, Table : ZT_MEMBER 생성확인.
//...과제22-5.B. /z09_sts372/src/main/java/org/jone/zdomain/MemberVO.java 생성.
import java.util.Date;
/***
 * ...http://stackoverflow.com/questions/4489548/why-there-can-be-only-one-timestamp-column-with-current-timestamp-in-default-cla
create table ZTBL_MEMBER
(
	USERID	varchar(50)	not null
    ,USERPW	varchar(50)	not null
    ,USERNAME	varchar(50)	not null
    ,EMAIL	varchar(100)
    ,REGDATE	timestamp default '0000-00-00 00:00:00'
    ,UPDATEDATE	timestamp default now() on update now()
    ,primary key(USERID)
);
 * @author Administrator
 *
 */

public class MemberVO {
	private String userid;
	private String userpw;
	private String username;
	private String email;
	private Date regdate;
	private Date updatedate;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	@Override
	public String toString() {
		return "MemberVO [userid=" + userid 
					 + ", userpw=" + userpw 
					 + ", username=" + username 
					 + ", email=" + email
					 + ", regdate=" + regdate 
					 + ", updatedate=" + updatedate + "]";
	}
	
	
	
}
