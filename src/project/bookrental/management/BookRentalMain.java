package project.bookrental.management;

import java.util.Scanner;

public class BookRentalMain {

	public static void main(String[] args) {

		BookRentalCtrl ctrl = new BookRentalCtrl();

		Scanner sc = new Scanner(System.in);

		String choice = null;

		do {

			System.out.println("\n\t===> 도서대여 프로그램 <===");
			System.out.println("1. 사서 전용 메뉴   2. 일반회원 전용메뉴   3. 프로그램 종료");
			System.out.print("=> 메뉴번호선택 : ");
			choice = sc.nextLine();

			switch (choice) {
			case "1":
				ctrl.librarianMenu(sc);
				break;
			case "2":
				ctrl.customerMenu(sc);
				break;
			case "3":
				break;
			default:
				System.out.println("~~~~ 오류: 선택지에 없는 번호입니다. 다시 입력해주세요!!! ");
				break;
			}

		} while(!choice.equals("3"));
		
		sc.close();

	}

}
