package project.bookrental.management;

import java.io.Serializable;
// 개별도서
public class BookDTO implements Serializable{

	private static final long serialVersionUID = -2407392580935446793L;
	private ISBN isbn;			// 도서정보
	private String bId; 		// 개별도서번호 (ex) 국제표준도서번호-001,국제표준도서번호-002)
	private boolean state;   	// 대여상태
//	private String statue;
	
	public BookDTO() {}

	public BookDTO(String bId, ISBN isbn) {
		super();
		this.bId = bId;
		this.isbn = isbn;
		this.state = true;
		//this.statue = "비치중";
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public ISBN getIsbn() {
		return isbn;
	}

	public void setIsbn(ISBN isbn) {
		this.isbn = isbn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public String getBookState() {
		if (state == true) return "비치중";
		else return "대여중";
	}
/*
	public String getStatue() {
		return statue;
	}

	public void setStatue(String statue) {
		this.statue = statue;
	}
	*/
	
	
	
}
