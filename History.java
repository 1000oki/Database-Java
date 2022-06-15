import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class History {
	Scanner scan = new Scanner(System.in);
	private Connection conn; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    
	public History() {
		String sql ="SELECT rnum, book.bnum, bname, author, publisher, rentDate, returnDate, returnState "
				+ "FROM book, rent, librarymember "
				+ "WHERE librarymember.mnum = ? "
				+ "AND librarymember.pword = ? "
				+ "AND rent.mnum = librarymember.mnum "
				+ "AND book.bnum = rent.bnum";
		
		String sql2 = "SELECT reserve.Renum, book.bnum, bname, author, publisher, reserveDate, rentDate"
				+ "    FROM reserve"
				+ "    left join rent"
				+ "        on reserve.mnum = rent.mnum"
				+ "        and reserve.rnum = rent.rnum"
				+ "    left join book"
				+ "        on reserve.bnum = book.bnum "
				+ "    INNER JOIN librarymember"
				+ "        on reserve.mnum = librarymember.mnum"
				+ "        and librarymember.mnum = ?"
				+ "        and librarymember.pword = ?";
		
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
	         conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	         
	         PreparedStatement prestmt = conn.prepareStatement(sql);
	         PreparedStatement prestmt2 = conn.prepareStatement(sql2);

	         System.out.println("이용할 서비스를 선택해 주세요.");
	         System.out.print("1.대출내역보기, 2.예약내역보기 ");
	         int id = scan.nextInt();
        
	         System.out.print("학번 : ");
        	 int mnum = scan.nextInt();              
        	 System.out.print("비밀번호 : ");
        	 String pword = scan.next();
	         
	         if(id == 1) {        	 
	        	 prestmt.setInt(1, mnum);
	        	 prestmt.setString(2, pword);
	        	 ResultSet result = prestmt.executeQuery();
        	
	        	 System.out.printf("%-5s %-5s %-15s %-8s %-8s %-15s %-15s %-5s\n","대출번호","도서번호","도서이름","작가","출판사","예약일","대출일","반납");
	        	 System.out.printf("-".repeat(100));
		         System.out.println("");
		        	while(result.next()) {
		        		int rnum = result.getInt("rnum");
		        		int bnum = result.getInt("bnum");
		        		String bname = result.getString("bname");
		        		String author = result.getString("author");
		        		String publisher = result.getString("publisher");
		        		String rentDate = result.getString("rentDate");
		        		String returnDate = result.getString("returnDate");
		        		int returnState = result.getInt("returnState");
		        		String state = null;
		        		if(returnState == 0) {
		        			state = "반납X";
		        		}else {
		        			state = "반납O";
		        	}
				System.out.format("%-5s %-5d %-15s %-8s %-8s %-15s %-15s %-5s",rnum, bnum, bname, author, publisher, rentDate,returnDate, state);
				System.out.print("\n");
		       }
				
				System.out.print("1.반납하기, 2.처음으로 돌아가기 ");
		         int id2 = scan.nextInt();
		         if(id2 == 1) {
		        	 Bookreturn bookreturn = new Bookreturn(); 
		         }else {
		        	 First first = new First();
	        	}
			}else {
				prestmt2.setInt(1, mnum);
	            prestmt2.setString(2, pword);
	            ResultSet result2 = prestmt2.executeQuery();
	               
	            System.out.printf("%-5s %-5s %-15s %-8s %-8s %-15s %-15s %-5s\n","예약번호","도서번호","도서이름","작가","출판사","예약일","대출일","반납");
	            System.out.printf("-".repeat(100));
	            System.out.println("");
	            while(result2.next()) {
	               int Renum = result2.getInt("Renum");
	               int bnum = result2.getInt("bnum");
	               String bname = result2.getString("bname");
	               String author = result2.getString("author");
	               String publisher = result2.getString("publisher");
	               String reserveDate = result2.getString("reserveDate");
	               String rentDate = result2.getString("rentDate");
	               String state = null;
	               if(rentDate == null) {
	                  state = "취소O";
	               }else {
	                  state = "취소X";
	            }
	            System.out.format("%-5d %-5d %-15s %-8s %-8s %-15s %-15s %-5s",Renum, bnum, bname, author, publisher, reserveDate, rentDate, state);
	            System.out.print("\n");
	          }
	            
	            System.out.print("1.예약 취소 하기, 2.예약 순위 보기 3. 처음으로 돌아가기 ");
		         int id3 = scan.nextInt();
		         if(id3 == 1) {
		        	 Reservedelete reservedelete = new Reservedelete(); 
		         }else if(id3 == 2){
		        	 Reserverank rank = new Reserverank();		        
	        	}else {
		        	 First first = new First();			         
	        	}
			
        }
	}catch (Exception e) {
        System.out.println("드라이버 로딩 실패 ");
        try {
            conn.close();
        } catch (SQLException e1) {    }
    }
}

}

