package project.bookrental.management;

import java.io.Serializable;
import java.text.DecimalFormat;
// 도서정보
public class ISBN implements Serializable{

	private static final long serialVersionUID = -4893483357510572206L;
	private String bISBN; 		// 국제표준도서번호
	private String bKind;		// 도서분류카테고리
	private String bName;		// 도서명
	private String bAuthor;		// 작가명
	private String bCompany;	// 출판사
	private int bPrice;			// 가격
	
	public ISBN() {}
	

	public ISBN(String bISBN, String bKind, String bName, String bAuthor, String bCompany, int bPrice) {
		super();
		this.bISBN = bISBN;
		this.bKind = bKind;
		this.bName = bName;
		this.bAuthor = bAuthor;
		this.bCompany = bCompany;
		this.bPrice = bPrice;
	}


	public String getIsbn() {
		return bISBN;
	}


	public void setIsbn(String isbn) {
		this.bISBN = isbn;
	}


	public String getbKind() {
		return bKind;
	}


	public void setbKind(String bKind) {
		this.bKind = bKind;
	}


	public String getbName() {
		return bName;
	}


	public void setbName(String bName) {
		this.bName = bName;
	}


	public String getbAuthor() {
		return bAuthor;
	}


	public void setbAuthor(String bAuthor) {
		this.bAuthor = bAuthor;
	}


	public String getbCompany() {
		return bCompany;
	}


	public void setbCompany(String bCompany) {
		this.bCompany = bCompany;
	}


	public int getbPrice() {
		return bPrice;
	}


	public void setbPrice(int bPrice) {
		this.bPrice = bPrice;
	}

	public String getPriceComma() {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(bPrice) + "원";
	}

	@Override
	public String toString() {
		return "ISBN [bISBN=" + bISBN + ", bKind=" + bKind + ", bName=" + bName + ", bAuthor=" + bAuthor + ", bCompany="
				+ bCompany + ", bPrice=" + bPrice + "]";
	}

	
	
	
	
}
