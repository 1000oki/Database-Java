import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Reservedelete {
	Scanner scan = new Scanner(System.in);
	private Connection conn; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";

	public Reservedelete() {
		String sql = "DELETE FROM reserve where Renum = ?";
		String sql2 = "UPDATE book, reserve SET reserveState = 0 where book.bnum = ? and not exists (select Renum from reserve where reserve.bnum = ? and reserve.bnum = book.bnum and reserve.rnum is null)";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            PreparedStatement prestmt = conn.prepareStatement(sql);
            PreparedStatement prestmt2 = conn.prepareStatement(sql2);

            
            System.out.println("���� ���� ���");
            System.out.print("���� ��ȣ : ");
			int Renum = scan.nextInt();
			System.out.print("���� ��ȣ : ");
			int bnum = scan.nextInt();

            prestmt.setInt(1, Renum);
			prestmt.executeUpdate();
			
			prestmt2.setInt(1, bnum);
			prestmt2.setInt(2, bnum);
		    prestmt2.executeUpdate();
			
			System.out.println("������ ��ҵǾ����ϴ�.!");
         
           
        } catch (Exception e) {
            System.out.println("����̹� �ε� ���� ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
	}
}
