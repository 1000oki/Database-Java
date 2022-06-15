import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Bookreturn {
	Scanner scan = new Scanner(System.in);
	private Connection conn;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";

	public Bookreturn() {
		String sql = "UPDATE rent SET returnState = 1 WHERE rnum = ?";
		String sql2 = "UPDATE book, rent SET rentState = 0 WHERE rnum = ? AND book.bnum = rent.bnum";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            PreparedStatement prestmt = conn.prepareStatement(sql);
            PreparedStatement prestmt2 = conn.prepareStatement(sql2);

            
            System.out.println("도서 반납");
            System.out.print("대출 번호 : ");
			int rnum = scan.nextInt();

            prestmt.setInt(1, rnum);
			prestmt.executeUpdate();
			
			prestmt2.setInt(1, rnum);
		    prestmt2.executeUpdate();
			
			System.out.println("반납되었습니다!");
         
           
        } catch (Exception e) {
            System.out.println("드라이버 로딩 실패 ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
	}
	
}

