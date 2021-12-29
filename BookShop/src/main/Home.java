package main;

import java.util.Scanner;

import Service.AdminServiceImpl;
import Service.UserServiceImpl;
import model.User;

public class Home {
	static User user = new User();

	// Ham main
	public static void main(String[] args) {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
		user = userServiceImpl.login();
		CheckUser(adminServiceImpl, userServiceImpl);
	}

	// Ham check account xem la user hay admin tu do chay cac function cua user hoac
	// admin
	public static void CheckUser(AdminServiceImpl adminServiceImpl, UserServiceImpl userServiceImpl) {
		if (user.getAuthority().equals("admin") == true) {
			FunctionAdmin(adminServiceImpl, userServiceImpl);
		} else {
			FunctionUser(adminServiceImpl, userServiceImpl);
		}
	}

	// Ham chuc nang cua admin
	public static void FunctionAdmin(AdminServiceImpl adminServiceImpl, UserServiceImpl userServiceImpl) {
		String choose;
		Scanner scanner = new Scanner(System.in);
		do {
			
			System.out.println("Hello Admin, Please select a function bellow by entering the corresponding number.");
			System.out.println("1. Create Book\r\n" + "2. Update Content Book\r\n" + "3. Delete Book\r\n" + "4. Logout");
			
			System.out.println("Choose: ");
			choose = scanner.nextLine().trim();
			switch (choose) {
			case "1": {
				adminServiceImpl.CreateBook();
				break;
			}
			case "2": {
				adminServiceImpl.UpdateBook();
				break;
			}
			case "3": {
				adminServiceImpl.DeleteBook();
				break;
			}
			default :
				System.out.println("Please select again!");
			}
		} while (choose.equals("4") == false);
		if (choose.equals("4") == true) {
			user = userServiceImpl.login();
			CheckUser(adminServiceImpl, userServiceImpl);
		}
	}

	// Ham chuc nang cua user
	public static void FunctionUser(AdminServiceImpl adminServiceImpl, UserServiceImpl userServiceImpl) {
		String choose;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("Hello User, Please select a function bellow by entering the corresponding number.");
			System.out.println("1. View List Books\r\n" + "2. Search Books\r\n" + "3. Read Book\r\n"
					+ "4. View Your BookCase\r\n" + "5. Edit Your BookCase\r\n" + "6. Logout");
			
			System.out.println("Choose: ");
			choose = scanner.nextLine().trim();
			switch (choose) {
			case "1": {
				userServiceImpl.viewBookList(user);
				break;
			}
			case "2": {
				userServiceImpl.searchBook(user);
				break;
			}
			case "3": {
				userServiceImpl.readBook(user);
				break;
			}
			case "4": {
				userServiceImpl.viewBookCase(user);
				break;
			}
			case "5": {
				userServiceImpl.editBookCase(user);
				break;
			}
			default:
				System.out.println("Please select again!");
			}
		} while (choose.equals("6") == false);
		if (choose.equals("6") == true) {
			user = userServiceImpl.login();
			CheckUser(adminServiceImpl, userServiceImpl);
		}
	}

}
