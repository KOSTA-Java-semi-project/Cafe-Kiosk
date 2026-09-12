package kosta.kiosk.view;

import java.util.Scanner;

import kosta.kiosk.controller.UserController;
import kosta.kiosk.session.Session;

public class MainView {
	private static Scanner sc = new Scanner(System.in);
	
	public static void menu() {
		while(true) {
			Session ss = Session.getInstance();
//			System.out.println("ss.getSet() = "+ss.getSet());

			MainView.printMenu();

			int menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
			case 1 :
				MainView.login();// TODO 로그인 재훈님 View 함수 호출
				break;
			case 2 :
				OrderView.orderMenu();
				break;
			case 3 :
				// TODO 건우님 View 함수호출
				break;

			case 9 : 
				System.exit(0);
			}
		}

	}
	
	public static void printMenu() {
		System.out.println("=== KOSTA CAFE ===");
		System.out.println("1. 회원 주문(가입)   |   2. 비회원 주문   |   3. 관리자   |  9. 종료");
	}
	
	
	/**
	 * 로그인 메뉴
	 * */
	public static void login() {
		 System.out.print("전화번호 : ");
		 String phone = sc.nextLine();
		 
		 UserController.login(phone);
	}
	
	/**
	 * 로그아웃
	 * */
	public static void logout(String userId) {
//		Session session = new Session(userId);
//		ss.remove(session);	
	}
}
