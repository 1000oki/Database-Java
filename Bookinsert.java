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
			System.out.println("�̿��Ͻ� ���񽺸� ����ּ���!");
			System.out.println("1.��� ���� ��Ϻ���, 2.���� �߰�");
			int id = scan.nextInt();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			if(id == 1) {
				System.out.println("���� ���");
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(sql2);
				System.out.printf("%-5s %-15s %-8s %-8s %-15s %-5s\n","������ȣ","�����̸�","�۰�","���ǻ�","���ǳ⵵","���⿩��");
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
						state = "���Ⱑ��";
					}else if(rentState ==1) {
						state = "������";
					}else if (reserveState == 1&& rentState == 0) {
						state = "������";
					}
					System.out.format(" %-5d %-15s %-8s %-8s %-15s %-5s",bnum, bname, author, publisher, bookyear, state);
					System.out.print("\n");
				}
			}else if(id == 2) {
				System.out.println("���� �߰�"); 
            
				PreparedStatement prestmt = conn.prepareStatement(sql);
   
				System.out.print("���� ��ȣ : ");
				int bnum = scan.nextInt();
				scan.nextLine();
				System.out.print("���� �̸� : ");
				String bname = scan.nextLine();
				System.out.print("�۰� : ");
				String author = scan.next();
				
				System.out.print("���ǻ� : ");
				String publisher = scan.next();
				System.out.print("���ǳ⵵ : ");
				String bookyear = scan.next();
			
				prestmt.setInt(1, bnum);
				prestmt.setString(2, bname);
				prestmt.setString(3, author);
				prestmt.setString(4, publisher);
				prestmt.setString(5, bookyear);
            
				int cnt = prestmt.executeUpdate(); 

				System.out.println("������ �߰��Ǿ����ϴ�!");
			}
			
        } catch (Exception e) {
            System.out.println("����̹� �ε� ���� ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }

	}
	
}