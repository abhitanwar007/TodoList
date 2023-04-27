package com.skill.lync.todolist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class To_Do_Lists {
	static String  user_name;
	static Connection con = MainApp.con;
	private static PreparedStatement pstmt;
	static Scanner sc = new Scanner(System.in);
	private static ResultSet res;
	
	
	static void taskList(String user){
		user_name=user;
		System.out.println("Select an option\n"
				+ "1. Add task\n"
				+ "2. View tasks\n"
				+ "3. Update tasks\n"
				+ "4. Remove tasks\n"
				+ "5. filter tasks\n"
				+ "6. Logout");
		int choice = sc.nextInt();
		switch (choice) {
		case 1: {
			newTask();
			break;
		}
		case 2: {
			viewTasks();
			break;
		}
		case 3: {
			updateTask();
			break;
		}
		case 4: {
			removeTask();
			break;
		}
		case 5: {
			filterTask();
			break;
		}
		case 6: {
			MainApp.main(null);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
	}

	 static void filterTask() {
		 try {
				String sql ="select * from todo_list where description=? and user_name=?";
				pstmt = con.prepareStatement(sql);
				System.out.println("Enter the description:");
				pstmt.setString(1, sc.next());
				pstmt.setString(2, user_name);
				res = pstmt.executeQuery();
				while (res.next()==true) {
					System.out.println("Task Id \t: "+res.getInt(1));
					System.out.println("Task Name \t: "+res.getString(2));
					System.out.println("Task Description : "+res.getString(3));
					System.out.println("Task Status \t: "+res.getString(4));
					System.out.println("--------------------------------");
				}
				taskList(user_name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

	static void removeTask() {
		try {
			String sql ="delete from todo_list where id=?";
			pstmt = con.prepareStatement(sql);
			System.out.println("Enter the task id");
			pstmt.setInt(1, sc.nextInt());
			
			int x = pstmt.executeUpdate();
			if(x>0) {
				System.out.println("Task removed");
				taskList(user_name);
			}else {
				System.out.println("Task deletion Failed");
				taskList(user_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	static void updateTask() {
		try {
			String sql ="update todo_list set status=? where user_name=? and id=? ";
			pstmt = con.prepareStatement(sql);
			System.out.println("Enter the task id");
			pstmt.setInt(3, sc.nextInt());
			pstmt.setString(2, user_name);
			System.out.println("Enter the task Status");
			pstmt.setString(1, sc.next());
			
			int x = pstmt.executeUpdate();
			if(x>0) {
				System.out.println("Task Updated");
				taskList(user_name);
			}else {
				System.out.println("Task Updation Failed");
				taskList(user_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	static void viewTasks() {
		try {
			String sql ="select * from todo_list where user_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);
			res = pstmt.executeQuery();
			while (res.next()==true) {
				System.out.println("Task Id \t: "+res.getInt(1));
				System.out.println("Task Name \t: "+res.getString(2));
				System.out.println("Task Description : "+res.getString(3));
				System.out.println("Task Status \t: "+res.getString(4));
				System.out.println("--------------------------------");
			}
			taskList(user_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void newTask() {
		try {
			System.out.println(con);
			System.out.println("Enter the task name:");
			String task_name = sc.next();
			System.out.println("Enter the Description:");
			String desc = sc.next();
			String sql = "insert into todo_list (task_name,description,user_name) values(?,?,?)";
			pstmt  = con.prepareStatement(sql);
			pstmt.setString(1, task_name);
			pstmt.setString(2, desc);
			pstmt.setString(3, user_name);
			int x = pstmt.executeUpdate();
			if (x>0) {
				System.out.println("Task Added Successfully.\n");
				taskList(user_name);
			} else {
				System.out.println("User action Failed.");
				taskList(user_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
