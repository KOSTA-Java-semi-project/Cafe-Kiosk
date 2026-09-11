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
				//MenuView.register(); // 가입
				break;
			case 2 :
				MainView.login();// 로그인
				break;
			case 3 :
				
				break;

			case 9 : 
				System.exit(0);
			}
		}

	}
	
	public static void printMenu() {
		System.out.println("=== KOSTA CAFE ===");
		System.out.println("1. 회원   |   2. 비회원   |   3. 관리자   |  9. 종료");
	}
	
	
	public static void printUserMenu(String userId) {
		while(true) {
			Session ss = Session.getInstance(); //TODO 	싱글톤으로 구현할것
			
			System.out.println("-----" +userId+ " 로그인 중 -----");
			System.out.println(" 1.로그아웃 |  2.상품보기  |  3.주문하기  | 4. 주문내역보기  |  5.장바구니담기  |  6.장바구니보기 ");
			int menu =Integer.parseInt( sc.nextLine());
			switch(menu) {
				case 1 :
					logout(userId);// 
					return; //함수를 빠져나가라.
					//break;
					
				case 2 :
					break;
				case 3 :
					printInputOrder(userId);
					break;
				case 4 :
					break;
				case 5 :
					MainView.putCart(userId);// 
					break;	
		
				case 6 : 
					viewCart(userId);
					break;
				}
		}
		
	}
	
	public static void printSubMenu() {
		System.out.println("1. 수정   |  2.탈퇴   | 9. 나가기");
	}
	
	public static void printAdminMenu() {
		System.out.println("-- 관리자 메뉴 --");
		System.out.println("1. 메뉴 검색   |  2. 이름으로 검색  | 3.전체 검색  |  9. 나가기");
		
	}
	
	/**
	 * 로그인 메뉴
	 * */
	public static void login() {
		 System.out.print("전화번호 : ");
		 String phone = sc.nextLine();
		 
		 System.out.print("이름 : ");
		 String name = sc.nextLine();
		 
		 UserController.login(phone, name); 
	}
	
	/**
	 * 로그아웃
	 * */
	public static void logout(String userId) {
//		Session session = new Session(userId);
//		ss.remove(session);	
	}
	
	/**
	 * 주문하기
	 * */
    public static void printInputOrder(String userId) {
    }
    
    /**
     * 장바구니 담기
     * */
    public static void putCart(String id) {
		System.out.println("--장바구니 담기 작업 --");
		System.out.print("상품번호 : ");
		String goodsId = sc.nextLine();
		System.out.print("수량 : ");
		int qty = Integer.parseInt(sc.nextLine());
		
//		CartController.putCart(id,goodsId,qty);
	
		
	}
	
    /**
     * 장바구니 보기
     * */
	public static void viewCart(String id) {
	}
}
