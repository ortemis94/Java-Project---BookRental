package project.bookrental.management;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookRentalCtrl implements InterBookRentalCtrl {

	private final String ISBNLISTFILENAME = "C:/iotestdata/project/bookrental/isbnlist.dat";
	private final String BOOKLISTFILENAME = "C:/iotestdata/project/bookrental/booklist.dat";
	private final String LIBLISTFILENAME = "C:/iotestdata/project/bookrental/liblist.dat";
	private final String CUSLISTFILENAME = "C:/iotestdata/project/bookrental/cuslist.dat";
	private final String RENTLISTFILENAME = "C:/iotestdata/project/bookrental/rentlist.dat";

	private BookRentalSerializable serial = new BookRentalSerializable();

	// 사서 전용 메뉴
	@Override
	public void librarianMenu(Scanner sc) {

		LibrarianDTO loginLib = null; // 로그인하고 있는 계정.
		String choice = null;

		do {

			if (loginLib != null) { // 로그인 중이면
				System.out.println("\n>>>> 사서 전용 메뉴 [" + loginLib.getLibId() + "로그인중..]<<<<");
			}else {					// 로그인 중이 아니면
				System.out.println("\n>>>> 사서 전용 메뉴 <<<<");
			}
			System.out.println("1.사서가입  2.로그인  3.로그아웃  4.도서정보등록  5.개별도서등록");
			System.out.println("6.도서대여해주기  7.대여중인도서조회  8.도서반납해주기  9.나가기");
			System.out.print("=> 메뉴번호선택 : ");
			choice = sc.nextLine();

			switch (choice) {
			case "1": // 사서 가입 
				libRegister(sc);
				break;
			case "2": // 사서 로그인
				if (loginLib == null) loginLib = libLogin(sc);
				else System.out.println("이미 로그인 중입니다!!!");
				break;
			case "3": // 사서 로그아웃
				loginLib = null;
				break;
			case "4": // 도서정보등록
				if (loginLib != null) isbnRegister(sc);
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "5": // 개별도서등록
				if (loginLib != null) bookIdRegister(sc);
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "6": // 도서대여해주기
				if (loginLib != null) bookRent(sc);
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "7": // 대여중인도서조회
				if (loginLib != null) searchRentedBook();
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "8": // 도서반납해주기
				if (loginLib != null) returnBook(sc);
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "9": // 나가기 
				break;
			default:
				System.out.println("~~~~ 오류: 선택지에 없는 번호입니다. 다시 입력해주세요!!! ");
			}

		} while(!choice.equals("9"));


	} // 사서 전용 메뉴 end


	// 사서 가입 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void libRegister(Scanner sc) {

		LibrarianDTO lib = new LibrarianDTO(); // 새로운 사서 객체를 생성

		System.out.println("\n== 사서가입하기 ==");

		/*			do { // id와 pw가 제대로 입력되어질 때까지 반복할 부분.
			System.out.print("▶ 사서 ID: ");
			String id = sc.nextLine();

			System.out.print("▶ 암호: ");
			String pw = sc.nextLine();


			if (id.trim().isEmpty() || pw.trim().isEmpty()) { // 입력한 id와 pw중 하나라도 공백이 있다면
				System.out.println("ID와 암호는 공백이 안됩니다!!!");
				System.out.println("반드시 입력해주세요!!!\n");
			}else if(!idCheck(id)) { // id와 pw를 모두 입력했으나 이미 존재하는 id라면
				System.out.println("이미 존재하는 ID입니다 다시 입력해주세요!!!\n");
			}else { // 중복 id도 없다면
				lib.setLibId(id); // 입력한 id를 새로운 사서 객체의 id에 저장.
				lib.setLibPw(pw); // 입력한 pw를 새로운 사서 객체의 pw에 저장.
				break; // 반복문 탈출.
			}
		} while(true);
		 */

		do{
			String id = emptyCheck("사서ID", sc);
			String pw = emptyCheck("암호", sc);
			if (!libIdCheck(id)) {
				System.out.println("이미 존재하는 ID입니다 다시 입력해주세요!!!\n");
			}else {
				lib.setLibId(id); // 입력한 id를 새로운 사서 객체의 id에 저장.
				lib.setLibPw(pw); // 입력한 pw를 새로운 사서 객체의 pw에 저장.
				break; // 반복문 탈출.
			}

		}while(true);


		File file = new File(LIBLISTFILENAME); // 파일 객체 생성

		List<LibrarianDTO> libList = null; // 파일에 저장할 사서 리스트 선언

		if (!file.exists()) { // 만일 기존 파일이 없다면
			libList = new ArrayList<LibrarianDTO>(); // libList에 ArrayList를 새로 할당해줌. 
			libList.add(lib); // 새로운 사서 객체를 사서 리스트(libList)에 넣어줌. 
		}else {	// 기존 파일이 있다면 
			libList = (ArrayList<LibrarianDTO>)serial.getobjectFromFile(LIBLISTFILENAME); // 파일에 있는 Object 객체를 불러와 ArrayList로 형변환 하여 libList에 넣어줌.
			libList.add(lib); // 새로운 사서 객체를 사서 리스트(libList)에 넣어줌.
		}

		int n = serial.objectToFileSave(libList, LIBLISTFILENAME); // 사서 객체(계정)를 새로 저장한 libList를 다시 파일에 저장함. 

		if (n == 1) { // 만일 파일에 저장이 정상적으로 완료되면
			System.out.println(">>> 사서 등록 성공!! <<<");
		}else if(n == 0){ // 파일에 저장이 정상적으로 완료되지 못하면
			System.out.println(">>> 사서 등록 실패 <<<");
		}

	} // 사서 가입 메서드 end


	// 사서 또는 일반회원 회원가입시 id 중복 확인 메서드
	@SuppressWarnings("unchecked")
	boolean libIdCheck(String id) {

		boolean flag = true; // 회원가입이 가능한지 여부확인. 최조 가입시에는 중복될 id가 없으므로 true가 되게 함. 

		List<LibrarianDTO> list = (ArrayList<LibrarianDTO>)serial.getobjectFromFile(LIBLISTFILENAME); // 파일에 저장되어있는 사서 리스트를 불러옴.

		if (list != null) {					 	// 사서리스트가 비어있지 않다면 
			for (LibrarianDTO li : list) { 	 	// 사서리스트에 있는 계정을 하나하나 확인하며 
				if (id.equals(li.getLibId())) { // 만일 입력한 id가 이미 리스트에 존재한다면
					flag = false; 				// 회원가입이 가능하지 못하게 flag 변수를 false로 저장.
				}
			}
		}

		return flag;
	} // 사서 회원가입시 id 중복 확인 메서드 end


	// 사서 로그인 메서드
	@SuppressWarnings("unchecked")
	@Override
	public LibrarianDTO libLogin(Scanner sc) {

		List<LibrarianDTO> libList = (ArrayList<LibrarianDTO>)serial.getobjectFromFile(LIBLISTFILENAME);

		if (libList == null) { // 만일 불러온 파일에 리스트가 없다면
			System.out.println("로그인 할 계정이 없습니다. 사서 가입을 해주세요!!!");
		}else {				   // 파일에 리스트가 있다면
			System.out.println("\n== 로그인 하기 ==");
			String id = emptyCheck("사서 아이디", sc);		
			String pw = emptyCheck("사서 암호", sc);

			for (LibrarianDTO lib : libList) { // 리스트에 있는 사서DTO를 하나씩 확인하며 
				if (lib.getLibId().equals(id) && lib.getLibPw().equals(pw)) { // 입력한 id와 암호가 모두 일치하면
					System.out.println(">>> 로그인 성공!!! <<<"); 
					return lib; 	// 해당 계정을 리턴하며 메서드 나감
				} // if문 end
			} // for문 end

			System.out.println("로그인에 실패하였습니다."); // id와 pw가 일치하는게 없다면
		} // else문 end

		return null;				// 기존 사서 리스트가 없거나 로그인을 실패하면 null을 리턴하며 메서드 빠져나감.
	} // 사서 로그인 메서드 end


	// 도서정보 등록 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void isbnRegister(Scanner sc) {

		ISBN isbn = new ISBN(); // 새로운 도서객체 생성 

		System.out.println("\n== 도서정보 등록하기 ==");

		do {
			String is = emptyCheck("국제표준도서번호(ISBN)", sc); // 국제표준도서번호를 공백없이 잘 입력하면 is에 저장해줌. 

			if (isbnCheck(is) != null) { // 만일 중복되는 번호가 있으면
				System.out.println("~~~ " + is + " 는 이미 존재하므로 다른 국제표준도서번호(ISBN)를 입력하세요!!");
			}else {
				isbn.setIsbn(is); // 중복되는 번호도 없으면 새로운 도서객체에 저장해줌.
				break;			  // 반복문 탈출
			}
		}while(true);

		isbn.setbKind(emptyCheck("도서분류카테고리", sc));		// 도서분류카테고리를 공백없이 잘 입력하면 새로운 도서객체에 저장해줌.

		isbn.setbName(emptyCheck("도서명", sc));				// 도서명을 공백없이 잘 입력하면 새로운 도서객체에 저장해줌.

		isbn.setbAuthor(emptyCheck("작가명", sc));			// 작가명을 공백없이 잘 입력하면 새로운 도서객체에 저장해줌.

		isbn.setbCompany(emptyCheck("출판사", sc));			// 출판사를 공백없이 잘 입력하면 새로운 도서객체에 저장해줌.

		do {
			String price = emptyCheck("가격", sc);			// 가격을 공백없이 잘 입력하면 새로운 도서객체에 저장해줌.
			try {
				int nPrice = Integer.parseInt(price);		// 입력한 가격을 int형으로 형변환.
				isbn.setbPrice(nPrice);						// 형변환이 잘 되면 새로운 도서객체에 저장해줌.
				break;										// 반복문 탈출
			} catch (Exception e) {							// 형변환 시 예외 발생하면 오류알림 출력하고 다시 반복. 
				System.out.println("~~~~ 오류 : 가격은 숫자로만 입력하세요!!!");
			}

		}while(true);

		File file = new File(ISBNLISTFILENAME);				// 파일존재를 확인할 수 있는 객체 생성

		List<ISBN> isbnList = null;							// 새로운 도서목록리스트 선언 

		if (!file.exists()) { 								// 만일 도서목록 파일이 존재하지 않는다면
			isbnList = new ArrayList<ISBN>();				// 새로운 도서목록리스트를 생성하여 메모리를 할당해주고
			isbnList.add(isbn);								// 리스트에 도서객체를 넣어줌.
		}else {												// 이미 파일에 도서목록 리스트가 존재한다면
			isbnList = (ArrayList<ISBN>)serial.getobjectFromFile(ISBNLISTFILENAME);	// 파일로부터 불러온 리스트를 불러와
			isbnList.add(isbn);								// 리스트에 도서객체를 넣어줌.
		}

		int n = serial.objectToFileSave(isbnList, ISBNLISTFILENAME); // 새로운 도서객체를 넣어준 도서목록 리스트를 파일에 저장.

		if (n == 1) { 										// 파일 저장이 정상완료되면  
			System.out.println(">>> 도서정보등록 성공!! <<<");
		}else {												// 파일 저장이 정상완료되지 않으면 
			System.out.println(">>> 도서정보등록 실패!! <<<");
		}


	} // 도서정보 등록 메서드 end

	// 도서등록 시 국제표준도서번호 중복 확인 메서드
	@SuppressWarnings("unchecked")
	ISBN isbnCheck(String id) { // 중복되는 국제표준도서번호가 있다면 해당 객체를 리턴하는 메서드

		List<ISBN> list = (ArrayList<ISBN>)serial.getobjectFromFile(ISBNLISTFILENAME); // 파일에 저장되어있는 ISBN리스트를 불러옴.

		if (list != null) { 									// 도서리스트가 비어있지 않다면 
			for (ISBN isbn : list) { 							// 리스트에 있는 계정을 하나하나 확인하며 
				if (id.equals(isbn.getIsbn())) {				// 만일 입력한 번호가 이미 리스트에 존재한다면
					return isbn;								// 해당 도서 객체를 리턴
				}
			}
		}
		return null; // 도서목록 리스트가 비어있으면 null을 리턴
	} // 도서등록 시 국제표준도서번호 중복 확인 메서드 end


	// 개별도서 등록 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void bookIdRegister(Scanner sc) {

		System.out.println("\n== 개별도서 등록하기 ==");

		String is = emptyCheck("국제표준도서번호(ISBN)", sc); // 국제표준번호를 입력받음.

		if (isbnCheck(is) == null) { 				// 기존 도서목록에 중복되는 국제표준번호가 없다면
			System.out.println(">>> 등록된 ISBN이 아닙니다. 도서등록 실패!! <<<\n");
		}else {										// 등록된 번호가 있다면 

			ISBN isbn = isbnCheck(is);				// isbn에 기존에 등록된 도서목록 객체를 넣는다.
			String bId = null;
			do {
				bId = emptyCheck("도서아이디", sc); // 도서 아이디를 입력받아

				if (bookCheck(bId) != null) {				// 중복되는 아이디가 있다면
					System.out.println("~~~ " + bId + " 는 이미 존재하므로 다른 도서아이디를 입력하세요!!\n");
				}else {								// 중복되는 아이디가 없다면
					break;							// 반복문 탈출
				}
			}while(true);

			BookDTO book = new BookDTO(bId, isbn);	// 개별도서 아이디와 기존 isbn 데이터를 이용하여 개별도서 객체 생성.

			File file = new File(BOOKLISTFILENAME);	// 파일 객체 생성

			List<BookDTO> bookList = null;			// 개별도서 리스트를 선언하고

			if (!file.exists()) {					// 기존 개별도서 파일이 없다면
				bookList = new ArrayList<BookDTO>();// 개별도서 리스트에 새로운 리스트를 생성해주고
			}else {									// 기존 개별도서 파일이 있다면
				bookList = (ArrayList<BookDTO>)serial.getobjectFromFile(BOOKLISTFILENAME); // 파일에서 개별도서 리스트를 불러와
			}
			bookList.add(book);						// 개별도서 객체를 리스트에 넣어줌.

			int n = serial.objectToFileSave(bookList, BOOKLISTFILENAME); // 개별도서 리스트를 파일에 저장. 

			if (n == 1)	System.out.println(">>> 도서등록 성공!! <<<"); // 저장이 정상완료되면
			else System.out.println(">>> 도서등록 실패 <<<"); // 저장이 정상완료되지 않으면

		} // else문(국제표준도서번호가 등록되어있다면) end 

	} // 개별도서 등록 메서드 end


	// 개별도서등록 시 도서아이디 중복 확인 메서드
	@SuppressWarnings("unchecked")
	BookDTO bookCheck(String id) { // 중복되는 도서아이디가 있다면 해당 도서 객체를 리턴하는 메서드

		List<BookDTO> list = (ArrayList<BookDTO>)serial.getobjectFromFile(BOOKLISTFILENAME); // 파일에 저장되어있는 개별도서 리스트를 불러옴.

		if (list != null) { 									// 도서리스트가 비어있지 않다면 
			for (BookDTO book : list) { 						// 리스트에 있는 계정을 하나하나 확인하며 
				if (id.equals(book.getbId())) {					// 만일 입력한 도서아이디가 이미 리스트에 존재한다면
					return book;								// 해당 도서 객체를 리턴
				}
			}
		}
		return null; // 도서목록 리스트가 비어있거나 중복되는 id가 없다면 null을 리턴
	} // 개별도서등록 시 도서아이디 중복 확인 메서드 end



	// 도서 대여해주기 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void bookRent(Scanner sc) {


		File bookFile = new File(BOOKLISTFILENAME);		

		List<BookDTO> bookList = null;

		if (!bookFile.exists()) {									// 도서 파일이 있는지 확인하여 없으면 
			System.out.println("도서가 없습니다. 도서등록을 먼저 해주세요.");	// 도서등록을 먼저 하라고 한 후 나감.
			return;
		}else {
			bookList = (ArrayList<BookDTO>)serial.getobjectFromFile(BOOKLISTFILENAME);	// 기존 도서리스트 파일을 불러옴.
		}


		System.out.println("\n>>> 도서대여하기 <<<");

		String id = null;

		do {
			id = emptyCheck("회원ID", sc); 		// 대여할 회원 id를 입력받아

			if (cusIdCheck(id) == null) { 		// 만일 입력한 id가 회원 리스트에 없는 id라면 
				System.out.println("~~~ 등록된 회원ID가 아닙니다 ~~~");
			}else {								// 입력한 id가 회원 리스트에 있는 id라면
				break;							// 반복문 탈출
			}
		} while (true);

		File rentFile = new File(RENTLISTFILENAME);

		List<RentalDTO> rentList = null;								// rental 리스트를 선언.

		if (!rentFile.exists()) rentList = new ArrayList<RentalDTO>();  // 파일이 존재하지 않는다면 새로운 리스트를 생성.
		else {
			rentList = (ArrayList<RentalDTO>)serial.getobjectFromFile(RENTLISTFILENAME);	// 기존 파일이 존재한다면 끌고 와서 넣어줌. 

			for (RentalDTO rent : rentList) {
				if (rent.getRtCusId().equals(id)) {		// 입력한 회원id가 대여 리스트에 있는 회원이라면

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					Calendar today = new GregorianCalendar(Locale.KOREA);

					today.setTime(new Date());

					Calendar returnDay = new GregorianCalendar(Locale.KOREA);

					try {
						returnDay.setTime(sdf.parse(rent.getReturnDate()));
					} catch (ParseException e) {
						System.out.println("형식이 맞지 않습니다.");
						e.printStackTrace();
					}
					int compare = returnDay.compareTo(today); // 반납일과 오늘 날짜를 비교. compare가 0보다 크면 returnDay가 더 나중이고 0보다 작으면 오늘 날짜가 더 나중임.
					if (compare < 0) {						  // 오늘 날짜가 반납일보다 나중이면
						System.out.println("~~~ 반납예정일을 넘긴 도서가 존재하므로 도서대여가 불가능합니다.!!!");
						return;								  // 메서드를 나감.
					}

				}	// if문 end

			} // for문 end

		} // else문 end 




		int nRentCnt = 0;
		do {
			String rentCnt = emptyCheck("총대여권수", sc);
			try {
				nRentCnt = Integer.parseInt(rentCnt);
				if (nRentCnt < 1 || nRentCnt > 5) {			// 대여권수를 1보다 작거나 5보다 크게 넣었다면 다시 입력하도록 함.
					System.out.println("대여는 한번에 1~5권만 가능합니다!!!");
				}else {
					break;
				}
			} catch (Exception e) {
				System.out.println("숫자로 입력해주세요!!!");
			}
		} while (true);


		for (int i = 0; i < nRentCnt; i++) {				// 입력한 대여권수만큼 반복 진행.

			RentalDTO rent = new RentalDTO();				// 새로운 대여객체 생성.

			do {											// 존재하는 도서 id를 입력할 때까지 검색하게 함.

				String bookId = emptyCheck("도서ID", sc);		// 대여한 도서 id를 입력 받아

				if (bookCheck(bookId) != null) {				// 만일 입력한 도서 아이디가 도서리스트에 있고
					if (bookCheck(bookId).isState() == true) {	// 해당 도서가 비치중인 상태라면		
//					if (bookCheck(bookId).getStatue() == "비치중") {	// 해당 도서가 비치중인 상태라면		
						rent.setRtCusId(id);						// 대여객체에 입력했던 회원 id 저장. 
						rent.setRtBookId(bookId);					// 대여객체에 입력했던 도서 id 저장.
						rent.setRentalDate();						// 대여객체에 대여 날짜 저장. 
						rent.setReturnDate();						// 대여객체에 반납 날짜 저장.

						rentList.add(rent);							// rental 리스트에 대여객체 저장.

						for (BookDTO b : bookList) {				// 도서 리스트를 돌려 
							if (b.getbId().equalsIgnoreCase(bookId)) {	// 도서 id로 해당 도서를 찾아
								b.setState(false);					// 상태를 대여중으로 변경
								break;								// for문 탈출
							}
						}
						break; 										// do~while 반복문 탈출.
					}
					else {										// 해당 도서가 대여중인 상태라면 다시 입력하게함.
						System.out.println("~~~ 이미 대여중인 도서입니다. 다시 입력하세요!! ~~~\n");
						//System.out.println("~~~ 이미 대여중이거나 예약중인 도서입니다. 다시 입력하세요!! ~~~\n");
					}
				}else {											// 입력한 도서 아이디가 리스트에 없다면 다시 입력하게 함.
					System.out.println("~~~ 존재하지 않는 도서 ID입니다. 다시 입력하세요!! ~~~\n");
				}

			} while (true);

		} // for문 end (입력한 대여권수만큼 반복 진행.)

		int n1 = serial.objectToFileSave(rentList, RENTLISTFILENAME); // 대여 정보가 저장된 rentList를 파일에 다시 저장.
		int n2 = serial.objectToFileSave(bookList, BOOKLISTFILENAME); // 도서 정보가 저장된 bookList를 파일에 다시 저장.

		if (n1 == 1) System.out.println(">>> 대여등록 성공 <<<");
		else System.out.println(">>> 대여등록 실패 <<<");

		if (n2 == 1) System.out.println(">>> 대여도서 비치중에서 대여중으로 변경함 <<<");
		else System.out.println(">>> 대여도서 비치중에서 대여중으로 변경 안됨 <<<");

	} // 도서 대여해주기 메서드 end

	// 대여중인 도서 조회 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void searchRentedBook() {


		System.out.println("==================================================================================================================================================================================================");
		System.out.println("도서ID\t\t\tISBN\t\t\t도서명\t작가명\t출판사\t회원ID\t회원명\t연락처\t\t대여일자\t\t반납예정일");
		System.out.println("==================================================================================================================================================================================================");

		File file = new File(RENTLISTFILENAME);

		if (!file.exists()) {									// 도서대여 파일이 아예 없다면
			System.out.println("~~~ 대여중인 도서가 없습니다.!!!");
		}else {													// 도서대여 파일이 있는데
			List<RentalDTO> rentList = (ArrayList<RentalDTO>)serial.getobjectFromFile(RENTLISTFILENAME);	// 파일에 저장중인 리스트를 불러옴. 
			if (rentList.isEmpty()) {							// 파일안에 값이 없다면
				System.out.println("~~~ 대여중인 도서가 없습니다.!!!");
			}else {												// 파일안에 값이 있다면

				for (int i = 0; i < rentList.size(); i++) {		// 파일안에 있는 객체 크기 만큼 반복

					BookDTO book = bookCheck(rentList.get(i).getRtBookId()); // 대여 리스트의 i번째 객체의 도서id와 일치하는 도서를 도서리스트에서 찾아 book에 저장. 
					CustomerDTO cus = cusIdCheck(rentList.get(i).getRtCusId()); // 대여 리스트의 i번째 객체의 회원 id와 일치하는 계정을 회원리스트에서 찾아 cus에 저장. 

					System.out.println(book.getbId() +"\t"+ book.getIsbn().getIsbn() +"\t"+ book.getIsbn().getbName() +"\t"+
							book.getIsbn().getbAuthor() +"\t"+ book.getIsbn().getbCompany() +"\t"+ cus.getCusId() +"\t"+ 
							cus.getCusName() +"\t"+ cus.getCusPhone() +"\t"+ rentList.get(i).getRentalDate() +"\t"+ rentList.get(i).getReturnDate());
				}

			}

		}



	} // 대여중인 도서 조회 메서드 end

	// 도서 반납 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void returnBook(Scanner sc) {

		File rentFile = new File(RENTLISTFILENAME);
		File bookFile = new File(BOOKLISTFILENAME);

		if (!bookFile.exists()) {
			System.out.println("등록된 도서가 없습니다.");
			return;
		}
		
		if (!rentFile.exists()) {
			System.out.println("대여중인 도서가 없습니다.");
			return;
		}else {
			List<RentalDTO> rentList = (ArrayList<RentalDTO>)serial.getobjectFromFile(RENTLISTFILENAME);

			System.out.println("\n >>> 도서반납하기 <<<");

			int nCnt = 0;

			do {
				String cnt = emptyCheck("총반납권수", sc);
				try {
					nCnt = Integer.parseInt(cnt);
					break;
				} catch (Exception e) {
					System.out.println("총반납권수는 숫자만 입력 가능합니다!!!");
				}

			} while (true);

			List<BookDTO> bookList = (ArrayList<BookDTO>)serial.getobjectFromFile(BOOKLISTFILENAME);
			int totalArr = 0;

			for (int i = 0; i < nCnt; i++) {
				do {
					String returnBId = emptyCheck("반납도서ID", sc);
					boolean contain = false;

						for (RentalDTO rent : rentList) {
							if (rent.getRtBookId().equals(returnBId)) {		// 입력한 도서 id가 대여 객체에 속한 id라면
								contain = true;
								
								rent.setArrear();
								System.out.print("도서별 연체료: " + rent.getArrear() + "원\n");
								totalArr += rent.getArrear();
								
								rentList.remove(rent);
								
								for (BookDTO book : bookList) {
									if (book.getbId().equals(returnBId)) {	// 도서리스트에서 반납할 도서 id로 객체를 찾아
										book.setState(true);				// 다시 비치중으로 상태를 바꾼다.
										break;
									}
								}// for문 end
								break;
							}// if문 end

						}// for문 end
						if (contain == false) System.out.println("해당 도서는 대여중인 도서가 아닙니다!!!\n");
						else break;
				} while (true);

			}//for문 end

			int n1 = serial.objectToFileSave(rentList, RENTLISTFILENAME);
			int n2 = serial.objectToFileSave(bookList, BOOKLISTFILENAME);
			
			if (n1 == 1) System.out.println(">>> 도서반납 성공!! <<<");
			if (n2 == 1) System.out.println(">>> 대여도서 대여중에서 비치중으로 변경함 <<<");
			
			System.out.println("▶ 연체료 총계: " + totalArr + "원");
		
		}//else문 end
		
		
		
		
		
	} // 도서 반납 메서드 end

	// 일반 회원 메뉴 
	@Override
	public void customerMenu(Scanner sc) {

		CustomerDTO loginCus = null;
		String choice = null;

		do {

			if (loginCus == null) System.out.println("\n>>>> 일반회원 전용 Menu <<<<");
			else System.out.println("\n>>>> 일반회원 전용 Menu [" + loginCus.getCusName() + " 로그인중..] <<<<");

			System.out.println("1.일반회원가입  2.로그인  3.로그아웃  4.도서검색하기  5.나의대여현황보기  6.나가기");
		//	System.out.println("1.일반회원가입  2.로그인  3.로그아웃  4.도서검색하기  5.나의대여현황보기  6.도서예약하기 7.나가기");
			System.out.print("=> 메뉴번호 선택 : ");
			choice = sc.nextLine();

			switch (choice) {
			case "1": // 일반회원가입
				CusRegister(sc);
				break;
			case "2": // 로그인
				if (loginCus == null) loginCus = cusLogin(sc);
				else System.out.println("이미 로그인 중입니다!!!");
				break;
			case "3": // 로그아웃
				loginCus = null;
				break;
			case "4": // 도서검색하기
				if (loginCus == null) System.out.println("로그인 먼저 진행해주세요!!!");
				else searchBook(sc);
				break;
			case "5": // 나의대여현황보기
				if (loginCus == null) System.out.println("로그인 먼저 진행해주세요!!!");
				else myRentedBook(loginCus);
				break;
/*			case "6":
			if (loginCus == null) System.out.println("로그인 먼저 진행해주세요!!!");
				else bookBook(loginCus, sc);
				break;*/
			case "6": // 나가기
				break;
			default:
				System.out.println("~~~~ 오류: 선택지에 없는 번호입니다. 다시 입력해주세요!!! ");
				break;
			}

		} while (!choice.equals("6"));


	} // 일반 회원 메뉴  end

	// 일반 회원 가입 메서드 
	@SuppressWarnings("unchecked")
	@Override
	public void CusRegister(Scanner sc) {

		System.out.println("== 일반회원 가입하기 ==");

		CustomerDTO cus = new CustomerDTO(); 		// 새로운 일반회원 객체 생성.

		do {
			String id = emptyCheck("회원ID", sc);	// 회원 ID입력 받아
			if (cusIdCheck(id) != null) {			// 만일 중복 id가 있다면 
				System.out.println("~~~ " + id + " 는 이미 존재하므로 다른 회원 ID를 입력하세요!!");
			}else {									// 중복 id가 없다면
				cus.setCusId(id);					// 일반회원 객체에 id를 저장 후
				break;								// 반복문 탈출.
			}
		} while (true);

		cus.setCusPw(emptyCheck("암호", sc));		// 암호를 입력받아 객체에 저장.	

		cus.setCusName(emptyCheck("성명", sc));		// 이름을 입력받아 객체에 저장.

		cus.setCusPhone(emptyCheck("연락처", sc));	// 연락처를 입력받아 객체에 저장.

		List<CustomerDTO> cusList = null;			// 일반회원 리스트를 선언.

		File file = new File(CUSLISTFILENAME);		// 파일 객체 생성하여

		if (!file.exists()) {						// 만일 기존 파일이 없으면
			cusList = new ArrayList<CustomerDTO>(); // 새로 리스트를 생성하고
		}else {										// 기존 파일이 있으면
			cusList = (ArrayList<CustomerDTO>)serial.getobjectFromFile(CUSLISTFILENAME); // 기존 파일을 불러옴.
		}
		cusList.add(cus);							// 일반회원 객체를 리스트에 삽입

		int n = serial.objectToFileSave(cusList, CUSLISTFILENAME); // 파일에 회원 리스트를 저장.

		if (n == 1) System.out.println(">>> 회원등록 성공!! <<<"); 		// 파일 저장 정상 완료시
		else System.out.println(">>> 회원등록 실패!! <<<");		   		// 파일 저장 비정상 완료시

	} // 일반 회원 가입 메서드  end

	// 일반 회원 id 중복 확인 메서드
	@SuppressWarnings("unchecked")
	CustomerDTO cusIdCheck(String id) {

		List<CustomerDTO> list = (ArrayList<CustomerDTO>)serial.getobjectFromFile(CUSLISTFILENAME); // 파일에 저장되어있는 회원 리스트를 불러옴.

		if (list != null) { 					// 회원리스트가 비어있지 않다면 
			for (CustomerDTO li : list) { 		// 리스트에 있는 계정을 하나하나 확인하며 
				if (id.equals(li.getCusId())) { // 만일 입력한 id가 이미 리스트에 존재한다면
					return li;					// 해당 회원 객체를 리턴
				}
			}
		}
		return null;							// 입력한 id의 계정이 없다면 null을 리턴.
	}

	// 일반 회원 로그인 메서드
	@SuppressWarnings("unchecked")
	@Override
	public CustomerDTO cusLogin(Scanner sc) {

		List<CustomerDTO> cusList = (ArrayList<CustomerDTO>)serial.getobjectFromFile(CUSLISTFILENAME); // 기존 회원 파일을 불러옴.

		if (cusList == null) { 						// 기존 일반회원 리스트가 없다면
			System.out.println("로그인 할 계정이 없습니다. 회원 가입을 해주세요!!!");
		}else {										// 기존 회원 리스트가 있다면
			System.out.println("\n== 로그인 하기 ==");
			String id = emptyCheck("회원아이디", sc);	// id를 입력받고
			String pw = emptyCheck("암호", sc);		// pw도 입력받아
			for (CustomerDTO cus : cusList) {		// 기존 회원 리스트와 비교하여
				if (cus.getCusId().equals(id) && cus.getCusPw().equals(pw)) { // 둘다 일치하는 계정이 있다면
					System.out.println(">>> 로그인 성공!!! <<<");
					return cus;						// 해당 계정을 리턴하며 메서드 빠져나감
				}
			} // for문 end 
			System.out.println("로그인에 실패하였습니다.");
		} // else문 end

		return null;								// 기존 회원 리스트가 없거나 로그인을 실패하면 null을 리턴하며 메서드 빠져나감.  
	} // 일반 회원 로그인 메서드 end


	// 도서검색하기 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void searchBook(Scanner sc) {

		System.out.println("\n >>> 도서검색하기 <<<");
		System.out.println("[주의사항] 검색어를 입력하지 않고 엔터를 하면 검색대상에서 제외됩니다.");

		System.out.print("▶ 도서분류카테고리(Programming, DataBase 등): ");
		String category = sc.nextLine();

		System.out.print("▶ 도서명: ");
		String bookName = sc.nextLine();

		System.out.print("▶ 작가명: ");
		String autName = sc.nextLine();

		System.out.print("▶ 출판사명: ");
		String comName = sc.nextLine();

		List<BookDTO> bookList = (ArrayList<BookDTO>)serial.getobjectFromFile(BOOKLISTFILENAME);

		if (bookList != null) {								// 개별도서 리스트가 있어야만 아래 코드를 수행한다. 
			int[] index = new int[bookList.size()]; 		// 도서 리스트중 일치하는 도서 index를 확인할 도서 리스트 크기만큼의 배열 생성. 

			if (!category.trim().isEmpty()) { 				// 만일 카테고리 검색이 공백이 아니라면 
				for (int i = 0; i < bookList.size(); i++) { // 리스트를 하나하나 돌며
					if (!bookList.get(i).getIsbn().getbKind().equalsIgnoreCase(category)) { // 검색한 카테고리와 일치하지 않는 도서가 나오면 
						index[i] = 1;						// 배열에서 해당 인덱스값을 1로 저장. 
					}
				}
			}

			if (!bookName.trim().isEmpty()) { 				// 만일 도서명 검색이 공백이 아니라면 
				for (int i = 0; i < bookList.size(); i++) { // 리스트를 돌려서 
					if (!bookList.get(i).getIsbn().getbName().equalsIgnoreCase(bookName)) { // 검색한 도서명과 일치하지 않는 도서가 나오면
						index[i] = 1;						// 배열에서 해당 인덱스값을 1로 저장. 
					}
				}
			}

			if (!autName.trim().isEmpty()) { 				// 만일 작가명 검색이 공백이 아니라면 
				for (int i = 0; i < bookList.size(); i++) { // 리스트를 돌려서 
					if (!bookList.get(i).getIsbn().getbAuthor().equalsIgnoreCase(autName)) { // 검색한 작가명과 일치하지 않는 도서가 나오면
						index[i] = 1;						// 배열에서 해당 인덱스값을 1로 저장. 
					}
				}
			}

			if (!comName.trim().isEmpty()) { 				// 만일 출판사명 검색이 공백이 아니라면 
				for (int i = 0; i < bookList.size(); i++) { // 리스트를 돌려서 
					if (!bookList.get(i).getIsbn().getbCompany().equalsIgnoreCase(comName)) { // 검색한 출판사와 일치하지 않는 도서가 나오면
						index[i] = 1;						// 배열에서 해당 인덱스값을 1로 저장. 
					}
				}
			}

			System.out.println("============================================================================================");
			System.out.println("ISBN\t\t\t도서아이디\t\t\t도서명\t작가명\t출판사\t가격\t대여상태");
			System.out.println("============================================================================================");

			boolean flag = false;
			for (int i : index) {	// 모든 조건에 일치하는 도서가 하나도 없었는지 확인.
				if (i == 0) {		// 조건에 모두 일치하여 0인 인덱스값이 나오면  
					flag = true;
					break;
				}
			}

			if (!flag) { 			// 만일 도서 리스트에 일치하는 도서가 없다면
				System.out.println("~~~~ 검색에 일치하는 도서가 없습니다. ~~~~");
			}else {										// 도서 리스트에 데이터가 있다면 
				for (int i = 0; i < bookList.size(); i++) { // ISBN, 도서아이디, 도서명, 작가명, 출판사, 가격, 대여상태를 출력하는데
					if (index[i] == 0) {					// 배열에서 초기값인 0으로 저장된 인덱스만 출력  
						System.out.println(bookList.get(i).getIsbn().getIsbn() +"\t"+ bookList.get(i).getbId() 
								+"\t"+ bookList.get(i).getIsbn().getbName() +"\t"+ bookList.get(i).getIsbn().getbAuthor() 
								+"\t"+ bookList.get(i).getIsbn().getbCompany() +"\t"+ bookList.get(i).getIsbn().getPriceComma() +"\t"+ bookList.get(i).getBookState());
					}
				} // for문 end
			} // else문 end
		} // if문 end

	} // 도서검색하기 메서드 end


	// 나의 대여 현황 보기 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void myRentedBook(CustomerDTO loginCus) {

		System.out.println("=============================================================================================================");
		System.out.println("도서ID\t\t\tISBN\t\t\t도서명\t작가명\t출판사\t회원ID\t대여일자\t\t반납예정일");
		System.out.println("=============================================================================================================");

		File file = new File(RENTLISTFILENAME);

		if (!file.exists()) {
			System.out.println("~~~ 대여해가신 도서가 없습니다. ~~~");
		}else {
			List<RentalDTO> rentList = (ArrayList<RentalDTO>)serial.getobjectFromFile(RENTLISTFILENAME);
			boolean contain = false;
			for (int i = 0; i < rentList.size(); i++) {
				if (rentList.get(i).getRtCusId().equals(loginCus.getCusId())) {	// 로그인 중인 회원id와 일치하는 대여객체가 있다면 

					BookDTO book = bookCheck(rentList.get(i).getRtBookId()); // 대여 리스트의 i번째 객체의 도서id와 일치하는 도서를 도서리스트에서 찾아 book에 저장. 

					System.out.println(book.getbId() +"\t"+ book.getIsbn().getIsbn() +"\t"+ book.getIsbn().getbName() +"\t"+ book.getIsbn().getbAuthor() +"\t"+ book.getIsbn().getbCompany() +"\t"+
							loginCus.getCusId() +"\t"+ rentList.get(i).getRentalDate() +"\t"+ rentList.get(i).getReturnDate());
					contain = true;
				}
			} // for문 end 
			if (contain == false) {
				System.out.println("~~~ 대여해가신 도서가 없습니다. ~~~");
			}
		} // else문 end

	} // 나의 대여 현황 보기 메서드 end

	// 공백 확인 메서드
	String emptyCheck(String title, Scanner sc) {

		do {
			System.out.print("▶ " + title + ": ");
			String string = sc.nextLine();
			if (string.trim().isEmpty()) { // 공백을 입력하면
				System.out.println("공백은 입력할 수 없습니다.");
				System.out.println("~~~ " + title +"을(를) 입력하세요!!\n");
			}else { // 공백이 아니면
				return string; // 해당 문자열 리턴
			}
		}while(true);

	} // 공백 확인 메서드 end
	
	
/*	// 도서 예약 메서드
	@SuppressWarnings("unchecked")
	void bookBook(CustomerDTO loginCus, Scanner sc) {

		System.out.println("\n >>> 도서 예약 <<<");
		File file = new File(BOOKLISTFILENAME);
		
		List<BookDTO> bookList = null;
		
		if (!file.exists()) {
			System.out.println("등록된 도서가 없습니다.");
			return;
		}else {
			bookList = (ArrayList<BookDTO>)serial.getobjectFromFile(BOOKLISTFILENAME);
			
				String bookId = emptyCheck("예약할 도서ID", sc);

				boolean contain = false;
				
				for (BookDTO book : bookList) {
					if (bookId.equals(book.getbId()) && book.getStatue().equals("비치중")) {
						book.setStatue("예약중");
						contain = true;
						break;
					}
				}
				
				if (contain == false) {
					System.out.println("해당 도서는 예약할 수 없습니다.");
					return;
				}
		}
		
		int n = serial.objectToFileSave(bookList, BOOKLISTFILENAME);
		
		if (n == 1) System.out.println(">>> 도서 예약 완료 <<<");
		
		
	} // 도서 예약 메서드 end
*/	
	

}
