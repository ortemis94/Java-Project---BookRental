package project.bookrental.management;

import java.io.Serializable;
// 일반회원
public class CustomerDTO implements Serializable{

	private static final long serialVersionUID = 5400083787332381123L;
	
	private String CusId;
	private String CusPw;
	private String CusName;
	private String CusPhone;
	
	public CustomerDTO() {}

	public CustomerDTO(String cusId, String cusPw, String cusName, String cusPhone) {
		super();
		CusId = cusId;
		CusPw = cusPw;
		CusName = cusName;
		CusPhone = cusPhone;
	}

	public String getCusId() {
		return CusId;
	}

	public void setCusId(String cusId) {
		CusId = cusId;
	}

	public String getCusPw() {
		return CusPw;
	}

	public void setCusPw(String cusPw) {
		CusPw = cusPw;
	}

	public String getCusName() {
		return CusName;
	}

	public void setCusName(String cusName) {
		CusName = cusName;
	}

	public String getCusPhone() {
		return CusPhone;
	}

	public void setCusPhone(String cusPhone) {
		CusPhone = cusPhone;
	}
	
	
	
}
