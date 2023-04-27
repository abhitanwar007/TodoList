package com.skill.lync.todolist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class MainApp {
	static Connection con;

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlpractice","root","root");
			System.out.println("************connected to the database ************");
			System.out.println("Please select an option\n"
					+"1. Register as new User\n"
					+"2. Login\n"
					+"3. Exit");
			Scanner sc =new Scanner(System.in);
			int choice1 = sc.nextInt();
			if (choice1==1) {
				Users.userRegister();
			}
			else if(choice1==2){
				Users.userLogin();
			}else if(choice1==3) {
				
				System.exit(0);
			}
			else {
				System.out.println("Wrong Option selected Refresh the page");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
