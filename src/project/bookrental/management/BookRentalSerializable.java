package project.bookrental.management;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BookRentalSerializable {

	// 직렬화하는 메소드 생성하기(메모리상에 올라온 객체를 하드디스크 파일에 저장시키기)
	public int objectToFileSave(Object obj, String saveFilename) {
		
		// === 객체 obj를 파일 saveFilename으로 저장하기 === 
		try {
			FileOutputStream fos = new FileOutputStream(saveFilename, false); // false는 기존에 있던 내용을 모두 지우고 새로 입력한다는 것이며 true는 기존 내용도 유지하며 내용을 추가하겠다는 것임.
			// 출력노드 스트림(빨대꽂기)
			// 파일이름(saveFilename)을 이용해서 FileOutputStream 객체를 생성한다.  
			// 생성된 객체는 두번째 파라미터 boolean append 값에 따라서 true이면 기존파일에 내용을 줕여 추가할 것이고.
			// boolean append가 false 이면 기존 내용은 삭제하고 새로운 내용이 기록되도록 하는 것이다. 
			
			BufferedOutputStream bos = new BufferedOutputStream(fos, 1024); // 1024는 한번에 1kb만큼 읽어들이겠다는 말이며 byte규모는 원하는 대로 설정 가능하다. 
			// 필터스트림(노드 스트림에 오리발 장착하기)								// 다만 자원 낭비(너무 크게 잡을 때)나 속도가 느려지는 것(너무 작게 잡을 때)을 방지하기 위해 전체 데이터 양에 맞게 조절이 필요하다. ㄴ
			
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			// oos는 객체 obj를 파일명 saveFilename에 기록하는(저장하는) 스트림 생성하기.
			
			oos.writeObject(obj);
			// 객체 obj를 파일명 saveFilename에 기록하는 (저장하는) 것이다. 
			
			oos.close(); // 사용된 자원반납하기.(사용된 객체를 메모리 공간에서 삭제하기)
			bos.close(); // 사용된 자원반납하기.(사용된 객체를 메모리 공간에서 삭제하기)
			fos.close(); // 사용된 자원반납하기.(사용된 객체를 메모리 공간에서 삭제하기)
			
			return 1;
			
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			return 0;
		}  catch (IOException e) {
			// e.printStackTrace();
			return 0;
		}      
		
	}
	
	// 역직렬화하는 메소드 생성하기(하드디스크에 저장된 파일을 읽어다가 객체로 만들어 메모리에 올리게 하는 것)
	public Object getobjectFromFile(String fileName) {
		
		// === 파일명 fileName을 읽어서 객체 Object로 변환하기
		
		try {
			FileInputStream fis = new FileInputStream(fileName);
			// 입력노드스트림(빨대꽂기)
			
			BufferedInputStream bis = new BufferedInputStream(fis, 1024);
			// 필터스트림(노드 스트림에 오리발 장착하기)
			
			ObjectInputStream ois = new ObjectInputStream(bis);
			// 파일명 fileName을 읽어서 객체로 만들어 주는 스트림 객체 생성하기


			Object obj = ois.readObject();
			// 파일명 fileName을 읽어서 객체로 만들어 주는 것이다.
		
			ois.close(); // 사용된 자원반납하기.(사용된 객체를 메모리 공간에서 삭제하기)
			bis.close(); // 사용된 자원반납하기.(사용된 객체를 메모리 공간에서 삭제하기)
			fis.close(); // 사용된 자원반납하기.(사용된 객체를 메모리 공간에서 삭제하기)
			
			return obj;
			
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		}catch (IOException e) {
			// e.printStackTrace();
		}
		
		return null;
		
	
	}
	
	
}
