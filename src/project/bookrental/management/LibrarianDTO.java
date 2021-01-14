package project.bookrental.management;

import java.io.Serializable;
// 사서
public class LibrarianDTO implements Serializable{

	private static final long serialVersionUID = 8856555294027742163L;
	
	private String libId; // 사서 계정 아이디
	private String libPw; // 사서 계정 비밀번호
	
	public LibrarianDTO() {}

	public LibrarianDTO(String libId, String libPw) {
		super();
		this.libId = libId;
		this.libPw = libPw;
	}

	public String getLibId() {
		return libId;
	}

	public void setLibId(String libId) {
		this.libId = libId;
	}

	public String getLibPw() {
		return libPw;
	}

	public void setLibPw(String libPw) {
		this.libPw = libPw;
	}
	
	
	
}
