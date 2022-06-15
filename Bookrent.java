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

public class Bookrent {
	Scanner scan = new Scanner(System.in);
	private Connection conn;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    
	public Bookrent() {
		String sql = "select * from book where bnum=?";
		String sql2 = "select * from librarymember where mnum=?";
		String sql3 = "INSERT INTO rent(bnum, mnum, rentDate, returnDate, returnState)VALUES(?,?,Now(),?,0)";
		String sql4 = "UPDATE book SET rentState = 1 WHERE bnum in (SELECT bnum FROM rent WHERE book.bnum = rent.bnum and rent.bnum = ?)";
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
	      
	         System.out.print("도서번호 : ");
	         int bnum = scan.nextInt();
	      
	         prestmt.setInt(1, bnum);
	         ResultSet result = prestmt.executeQuery();
	         while(result.next()) {
	            int reserveState = result.getInt("reserveState");
	            int rentState = result.getInt("rentState");
	            if(reserveState == 0 && rentState ==0 ) {
	            	while(true) {
	            
	            	System.out.print("학번 : ");
	            	int mnum = scan.nextInt();              
	            	System.out.print("비밀번호 : ");
	            	String pword = scan.next();
	            		            		                       	
	            	prestmt2.setInt(1, mnum);
	   	         	ResultSet result2 = prestmt2.executeQuery();
	   	         	
	   	         	int checkmnum = 0;
	   	         	String checkpword = null;
	            	
	   	         	while(result2.next()) {
	            		checkmnum = result2.getInt("mnum");
	            		checkpword = result2.getString("pword");
	            	}
	   	        

	            	if(checkmnum == 0) {
	            		System.out.println("\n학번이 올바르지 않습니다.");
	            	}else {
	            		if(checkpword.equals(pword)) {
	            			prestmt3.setInt(1, bnum);
	            			prestmt3.setInt(2, mnum);
	            			prestmt3.setString(3, returndate);
	            			
	            			prestmt3.executeUpdate();
	            			
	            			prestmt4.setInt(1, bnum);
	            			prestmt4.executeUpdate();
	            			
	            			System.out.println("대출되었습니다.");
	            			break;
	            		}else {
	            			System.out.println("비밀번호가 올바르지 않습니다.");
	            		}
	            	}
	            	}
	            }   
	            else if(rentState ==1) {
	               System.out.println("대출 중인 책입니다. 예약하시겠습니까?");
	               System.out.print("1.예, 2.아니오 ");
	               int id = scan.nextInt();
	               if(id == 1) {
	            	   Bookreserve reserve = new Bookreserve();
	               }else {
	            	   First first = new First();
	               }
	            
	            }else if (reserveState == 1 && rentState == 0) {
		               System.out.println("대출 중인 책입니다. 예약하시겠습니까?");
		               System.out.print("1.예, 2.아니오 ");
		               int id = scan.nextInt();
		               if(id == 1) {
		            	   Bookreserve reserve = new Bookreserve();
		               }else {
		            	   First first = new First();
		               }
	            }
	         }		
        } catch (Exception e) {
            System.out.println("드라이버 로딩 실패 ");
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
	}
	
}