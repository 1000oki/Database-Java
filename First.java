//1910896 ����а� õ����
//2022-05-22

import java.sql.*;
import java.util.Scanner;

 
public class First {
      
    public First() {
    	 Scanner scan = new Scanner(System.in);
    	 int id = 0;
    	 
    	 while(true) {
    		 System.out.println("");
    		 System.out.printf("=".repeat(55));
	         System.out.println("");
    		 System.out.println("������ �������� ���Ű� ȯ���մϴ�.\n �̿��� ���񽺸� ������ �ּ���.");
    		 System.out.println("1.ȸ������, 2.�����߰�, 3.���� �˻� & ����, 4.���� ���� ��Ȳ, 5.����" );
    		 id = scan.nextInt();
    		 System.out.printf("=".repeat(50));
	         System.out.println("");
    		 if(id == 1) {
        		 Memberinsert member = new Memberinsert();
        	 }else if(id == 2) {
        		 Bookinsert book = new Bookinsert();
        	 }else if(id == 3) {
        		 Booksearch bsearch = new Booksearch();
        	 }else if(id == 4) {
        		 History history = new History();
        	 }else {
        		 System.out.println("���α׷��� �����մϴ�.");
        	        System.exit(0);
        	 }
    	 }
        
    }
}