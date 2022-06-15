import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Bookreserve {
	Scanner scan = new Scanner(System.in);
	private Connection conn; 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "try1234";
    private static final String URL = "jdbc:mysql://localhost:3306/library";
	
    public Bookreserve() {
    	String sql = "select * from librarymember where mnum=?";
    	String sql2 = "INSERT INTO reserve (bnum, mnum, reserveDate) VALUES (?,?,NOW())";
    	String sql3 = "UPDATE book SET reserveState=1 WHERE bnum in (SELECT bnum FROM reserve WHERE book.bnum = reserve.bnum and book.bnum = ?)";
    	
    	try {
			Class.forName("com.mysql.jdbc.Driver");
	         conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        
	         PreparedStatement prestmt = conn.prepareStatement(sql);
	         PreparedStatement prestmt2 = conn.prepareStatement(sql2);
	         PreparedStatement prestmt3 = conn.prepareStatement(sql3);

	         
	         System.out.print("도서번호 : ");
	         int bnum = scan.nextInt();
	         System.out.print("학번 : ");
         	 int mnum = scan.nextInt();              
         	 System.out.print("비밀번호 : ");
         	 String pword = scan.next();
	      	            		                       	
	         prestmt.setInt(1, mnum);
	   	     ResultSet result = prestmt.executeQuery();
	   	         	
	   	     int checkmnum = 0;
	   	     String checkpword = null;
	            	
	   	     while(result.next()) {
	   	    	 checkmnum = result.getInt("mnum");
	   	    	 checkpword = result.getString("pword");
	         }
	   	        
	         if(checkmnum == 0) {
	            System.out.println("\n학번이 올바르지 않습니다.");
	          }else {
	            if(checkpword.equals(pword)) {
	            	prestmt2.setInt(1, bnum);
        			prestmt2.setInt(2, mnum);
        			
        			prestmt2.executeUpdate();
        			
        			prestmt3.setInt(1, bnum);
        			prestmt3.executeUpdate();
        			System.out.println("예약되었습니다.");
	            }else {
	            	System.out.println("비밀번호가 올바르지 않습니다.");
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
	

	
