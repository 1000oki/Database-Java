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
			System.out.println("ȸ������"); 
			
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            PreparedStatement prestmt = conn.prepareStatement(sql);
            PreparedStatement prestmt2 = conn.prepareStatement(sql2);
         
            System.out.print("�а� : ");
			String major = scan.next();
			System.out.print("�̸� : ");
			String mname = scan.next();
			
			int mnum = 0;
			while(true) {
				System.out.print("�й� : ");
				mnum = scan.nextInt();
			
				prestmt2.setInt(1, mnum);
				ResultSet result = prestmt2.executeQuery();
			
				int title = 0;
				while(result.next()) {
		               title = result.getInt("mnum");
				}

				if(title == 0) {
					System.out.println("��밡���� �й��Դϴ�.");
					break;
				}else {
					System.out.println("�̹� ȸ�������� �й��Դϴ�.");
				}
				
			}
			
			System.out.print("��й�ȣ : ");
			String pword = scan.next();
			System.out.print("�̸��� : ");
			String email = scan.next();
			System.out.print("������� : ");
			String birth = scan.next();
			
			prestmt.setInt(1, mnum);
			prestmt.setString(2, pword);
			prestmt.setString(3, mname);
			prestmt.setString(4, major);
			prestmt.setString(5, email);
			prestmt.setString(6, birth);
            
			prestmt.executeUpdate(); 

			System.out.println("ȸ�������� �Ǿ����ϴ�.");
			
        } catch (Exception e) {
            System.out.println("����̹� �ε� ���� ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
	}
	
}
