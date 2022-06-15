import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Booksearch {
	Scanner scan = new Scanner(System.in);
	private Connection conn; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";

	public Booksearch() {
		String sql = "select * from book where bname=?";
		
		try {
			System.out.println("도서 검색"); 
			
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            PreparedStatement prestmt = conn.prepareStatement(sql);
         
           
			System.out.print("책 제목 : ");
			String bname = scan.nextLine();
			
			prestmt.setString(1, bname);
			ResultSet result = prestmt.executeQuery();
			System.out.printf("%-5s %-15s %-8s %-8s %-15s %-5s\n","도서번호","도서이름","작가","출판사","출판년도","대출여부");
			System.out.printf("-".repeat(70));
			System.out.println("");
			
			while(result.next()) {
				int bnum = result.getInt("bnum");
				String author = result.getString("author");
				String publisher = result.getString("publisher");
				String bookyear = result.getString("bookyear");
				int rentState = result.getInt("rentState");
				int reserveState = result.getInt("reserveState");
				String state = null;
				if(reserveState == 0 && rentState ==0 ) {
					state = "대출가능";
				}else if(rentState ==1) {
					state = "대출중";
				}else if (reserveState == 1&& rentState == 0) {
					state = "예약중";
				}
				System.out.format(" %-5d %-15s %-8s %-8s %-15s %-5s",bnum, bname, author, publisher, bookyear, state);
				System.out.print("\n");
			}
			
			System.out.println("대출하시겠습니까?\n1.예, 2.아니오");
			int id = scan.nextInt();
			if(id == 1) {
					Bookrent rent = new Bookrent();
			}else {
				First first = new First();
			}		
        } catch (Exception e) {
            System.out.println("드라이버 로딩 실패 ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
	}
	
}
