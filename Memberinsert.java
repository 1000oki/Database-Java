import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Memberinsert {
	
	Scanner scan = new Scanner(System.in);
	private Connection conn; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";
	public Memberinsert(){
		String sql = "INSERT INTO libraryMember(mnum, pword, mname, major, email, birth)VALUES(?,?,?,?,?,?)";
		String sql2 = "select * from libraryMember where mnum=?";		
		try {
			System.out.println("회원가입"); 
			
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            PreparedStatement prestmt = conn.prepareStatement(sql);
            PreparedStatement prestmt2 = conn.prepareStatement(sql2);
         
            System.out.print("학과 : ");
			String major = scan.next();
			System.out.print("이름 : ");
			String mname = scan.next();
			
			int mnum = 0;
			while(true) {
				System.out.print("학번 : ");
				mnum = scan.nextInt();
			
				prestmt2.setInt(1, mnum);
				ResultSet result = prestmt2.executeQuery();
			
				int title = 0;
				while(result.next()) {
		               title = result.getInt("mnum");
				}

				if(title == 0) {
					System.out.println("사용가능한 학번입니다.");
					break;
				}else {
					System.out.println("이미 회원가입한 학번입니다.");
				}
				
			}
			
			System.out.print("비밀번호 : ");
			String pword = scan.next();
			System.out.print("이메일 : ");
			String email = scan.next();
			System.out.print("생년월일 : ");
			String birth = scan.next();
			
			prestmt.setInt(1, mnum);
			prestmt.setString(2, pword);
			prestmt.setString(3, mname);
			prestmt.setString(4, major);
			prestmt.setString(5, email);
			prestmt.setString(6, birth);
            
			prestmt.executeUpdate(); 

			System.out.println("회원가입이 되었습니다.");
			
        } catch (Exception e) {
            System.out.println("드라이버 로딩 실패 ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
	}
	
}
