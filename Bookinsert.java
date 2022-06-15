import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Bookinsert {
	
	Scanner scan = new Scanner(System.in);
	private Connection conn; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";
	public Bookinsert(){
		String sql = "INSERT INTO Book(bnum, bname, author, publisher, bookyear, rentState, reserveState)VALUES(?,?,?,?,?,0,0)";
		String sql2 = "select * from Book";
		try {
			System.out.println("이용하실 서비스를 골라주세요!");
			System.out.println("1.모든 도서 목록보기, 2.도서 추가");
			int id = scan.nextInt();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			if(id == 1) {
				System.out.println("도서 목록");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(sql2);
				System.out.printf("%-5s %-15s %-8s %-8s %-15s %-5s\n","도서번호","도서이름","작가","출판사","출판년도","대출여부");
				System.out.printf("-".repeat(70));
				System.out.println("");
				while(result.next()) {
					int bnum = result.getInt("bnum");
					String bname = result.getString("bname");
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
			}else if(id == 2) {
				System.out.println("도서 추가"); 
            
				PreparedStatement prestmt = conn.prepareStatement(sql);
   
				System.out.print("도서 번호 : ");
				int bnum = scan.nextInt();
				scan.nextLine();
				System.out.print("도서 이름 : ");
				String bname = scan.nextLine();
				System.out.print("작가 : ");
				String author = scan.next();
				
				System.out.print("출판사 : ");
				String publisher = scan.next();
				System.out.print("출판년도 : ");
				String bookyear = scan.next();
			
				prestmt.setInt(1, bnum);
				prestmt.setString(2, bname);
				prestmt.setString(3, author);
				prestmt.setString(4, publisher);
				prestmt.setString(5, bookyear);
            
				int cnt = prestmt.executeUpdate(); 

				System.out.println("도서가 추가되었습니다!");
			}
			
        } catch (Exception e) {
            System.out.println("드라이버 로딩 실패 ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }

	}
	
}