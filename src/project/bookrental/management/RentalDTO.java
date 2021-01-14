package project.bookrental.management;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RentalDTO implements Serializable{

	private static final long serialVersionUID = -898030619142310130L;
	private String rtCusId;			// 대여하는 회원 id
	private String rtBookId;		// 대여하는 도서 id
	private String rentalDate;		// 대여일자
	private String returnDate;		// 반납예정일
	private int arrear;				// 연체료
	private CustomerDTO rtCus; 		// 대여한 회원 객체  
	private BookDTO rtBook;			// 대여한 도서 객체 
	
	public RentalDTO() {}
	
	public String getRtCusId() {
		return rtCusId;
	}

	public void setRtCusId(String rtCusId) {
		this.rtCusId = rtCusId;
	}

	public String getRtBookId() {
		return rtBookId;
	}

	public void setRtBookId(String rtBookId) {
		this.rtBookId = rtBookId;
	}

	public String getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	// 날짜를 연도 - 달 - 일 형식으로 설정.
		
		Calendar calendar = new GregorianCalendar(Locale.KOREA);	// 
		
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -5); // 현재날짜로부터 5일을 뺀다.
		
		this.rentalDate = sdf.format(calendar.getTime());
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date date = sdf.parse(rentalDate);			// date 변수에 대여일자를 형변환해서 저장.
		
			Calendar calendar = new GregorianCalendar(Locale.KOREA);	// Calendar 객체 생성
			
			calendar.setTime(date);						// calendar 객체를 대여일자로 설정.
			
			calendar.add(Calendar.DAY_OF_YEAR, 3); 		// 3일 뒤로 더하여 저장.
			
			this.returnDate = sdf.format(calendar.getTime());
			
		} catch (ParseException e) {
			System.out.println("형식이 맞지 않습니다.");
			e.printStackTrace();
		}
		
	}

	public CustomerDTO getRtCus() {
		return rtCus;
	}

	public void setRtCus(CustomerDTO rtCus) {
		this.rtCus = rtCus;
	}

	public BookDTO getRtBook() {
		return rtBook;
	}

	public void setRtBook(BookDTO rtBook) {
		this.rtBook = rtBook;
	}

	public int getArrear() {
		return arrear;
	}

	public void setArrear() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar today = new GregorianCalendar(Locale.KOREA);

		today.setTime(new Date());
		
		Calendar rDay = new GregorianCalendar(Locale.KOREA);
		try {
			rDay.setTime(sdf.parse(returnDate));
			long calDate = (today.getTimeInMillis() - rDay.getTimeInMillis()) / 1000;
			long calD = calDate / (24*60*60);
			
			if (calD > 0) {
				this.arrear = (int)calD * 100;
			}else {
				this.arrear = 0;
			}
		} catch (ParseException e) {
			System.out.println("형식이 맞지 않습니다.");
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
