//1910896 통계학과 천옥희
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
    		 System.out.println("눈송이 도서관에 오신걸 환영합니다.\n 이용할 서비스를 선택해 주세요.");
    		 System.out.println("1.회원가입, 2.도서추가, 3.도서 검색 & 대출, 4.나의 도서 현황, 5.종료" );
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
        		 System.out.println("프로그램을 종료합니다.");
        	        System.exit(0);
        	 }
    	 }
        
    }
}