import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Reserverank {
	Scanner scan = new Scanner(System.in);
	private Connection conn; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";

	public Reserverank() {
		String sql = "select mnum, book.bnum, rentState,rank() over(order by Renum) As ranking from reserve, book where reserve.bnum = ? and reserve.bnum = book.bnum and rnum is null";
		String sql2 = "SELECT * FROM libraryMember where mnum = ?";
		String sql3 = "INSERT INTO rent(bnum, mnum, rentDate, returnDate, returnState)VALUES(?,?,Now(),?,0)";
		String sql4 = "UPDATE book SET rentState = 1 WHERE bnum = ?";
		String sql5 = "UPDATE reserve, rent SET reserve.rnum= rent.rnum WHERE rent.mnum = ? AND rent.bnum = ? AND rent.returnState = 0 AND reserve.bnum = rent.bnum AND reserve.mnum = rent.mnum AND reserve.rnum is null";
		String sql6 = "UPDATE book, reserve SET reserveState = 0 where book.bnum = ? and not exists (select Renum from reserve where reserve.bnum = ? and reserve.bnum = book.bnum and reserve.rnum is null)";
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, 10);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String returndate = df.format(cal.getTime());
		try {
			Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            PreparedStatement prestmt = conn.prepareStatement(sql);
            PreparedStatement prestmt2 = conn.prepareStatement(sql2);
            PreparedStatement prestmt3 = conn.prepareStatement(sql3);
            PreparedStatement prestmt4 = conn.prepareStatement(sql4);
            PreparedStatement prestmt5 = conn.prepareStatement(sql5);
            PreparedStatement prestmt6 = conn.prepareStatement(sql6);

            
            System.out.println("예약 순위");
            System.out.print("도서 번호 : ");
			int bnum = scan.nextInt();

            prestmt.setInt(1, bnum);
			ResultSet result = prestmt.executeQuery();
			
			System.out.format("%-10s %-3s %-6s\n","학번","순위","대출여부");
			System.out.printf("-".repeat(30));
			System.out.println("");
			
			int mnumcheck = 0;
			int mnum1 = 0;
			while(result.next()) {
				int mnum = result.getInt("mnum");
				int rentState = result.getInt("rentState");
				int ranking  = result.getInt("ranking");
				String rent = null;
				if(rentState == 0 && ranking == 1) {
					rent = "대출가능";
				}else {
					rent = "대출대기";
				}
				System.out.format("%-10d %-5d %-6s", mnum, ranking, rent);
				System.out.println("");
				if (rent.equals("대출가능")) {
					mnumcheck=1;
					mnum1 = result.getInt("mnum");
				}
			}
			 System.out.println(mnum1+"님 입니까? 1. 네, 2. 아니오");
			 int id = scan.nextInt();
			 if(id == 1) {
				 System.out.println("대출하시겠습니까? 1. 네, 2. 아니오");		
				 int id2 = scan.nextInt();
				 if(id2 == 1) {
				 if(mnumcheck == 1) {
					 while(true) {					
					System.out.print("비밀번호 : ");
					String pword = scan.next();
				
					prestmt2.setInt(1, mnum1);
					ResultSet result2 = prestmt2.executeQuery();
				
					while(result2.next()) {	
						String pwordcheck = result2.getString("pword");
						if(pword.equals(pwordcheck)) {
							prestmt3.setInt(1, bnum);
							prestmt3.setInt(2, mnum1);
							prestmt3.setString(3, returndate);    			
							prestmt3.executeUpdate();
						
							prestmt4.setInt(1, bnum);					   			
							prestmt4.executeUpdate();
						
							prestmt5.setInt(1, mnum1);		
							prestmt5.setInt(2, bnum);					   			
							prestmt5.executeUpdate();
						
							prestmt6.setInt(1, bnum);		
							prestmt6.setInt(2, bnum);					   			
							prestmt6.executeUpdate();
						
							System.out.println("대출되었습니다.");
							break;
						}
						else {
							System.out.println("비밀번호가 올바르지 않습니다.");
						}
					}
				}
			}
				 }else {
					 First first= new First();
				 }
			 }else {
				 First first= new First();
			 }
                    
        } catch (Exception e) {
            System.out.println("드라이버 로딩 실패 ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
	}
	
}