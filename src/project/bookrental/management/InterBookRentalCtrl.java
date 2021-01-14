package project.bookrental.management;

import java.util.Scanner;

public interface InterBookRentalCtrl {

	// 사서 전용 메뉴
	void librarianMenu(Scanner sc);
	
	// 사서 전용 메뉴 중 사서 가입 메서드
	void libRegister(Scanner sc);
	
	// 사서 전용 메뉴 중 사서 로그인 메서드
	LibrarianDTO libLogin(Scanner sc);
	
	// 사서 전용 메뉴 중 도서정보등록 메서드
	void isbnRegister(Scanner sc);
	
	// 사서 전용 메뉴 중 개별도서등록 메서드
	void bookIdRegister(Scanner sc);
	
	// 사서 전용 메뉴 중 도서대여해주기 메서드
	void bookRent(Scanner sc);
	
	// 사서 전용 메뉴 중 대여중인 도서 조회 
	void searchRentedBook();
	
	// 사서 전용 메뉴 중 도서반납해주기
	void returnBook(Scanner sc);
	
	//////////////////////////////////////////////////////////
	
	// 일반회원 전용 메뉴 
	void customerMenu(Scanner sc);
	
	// 회원가입 메서드
	void CusRegister(Scanner sc);
	
	// 로그인 메서드
	CustomerDTO cusLogin(Scanner sc);
	
	// 도서검색하기 메서드
	void searchBook(Scanner sc);
	
	// 나의 대여 현황 보기
	void myRentedBook(CustomerDTO loginCus);
	
	
}
