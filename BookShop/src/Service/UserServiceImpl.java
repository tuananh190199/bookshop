package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import connection.SQLServerConnection;
import model.User;

public class UserServiceImpl implements UserService {

	// Ham dang nhap
	@Override
	public User login() {
		System.out.println("Welcome to Read Book Application!Please enter your username and password.");
		String username, password;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("User name: ");
			username = scanner.nextLine();
			System.out.println("Password: ");
			password = scanner.nextLine();
			try {
				Connection connection = SQLServerConnection.getConnection();
				String query1 = "select user_id,user_name,password,Role_.authority from User_ \n"
						+ "inner join Role_ on  User_.role_id=role_.role_id\n"
						+ "where user_name = ? and password = ? ; ";
				PreparedStatement ps = connection.prepareStatement(query1);
				ps.setString(1, username);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				} else
					System.out.println("Wrong Username or Password! Please try again!");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Ham View Book List
	@Override
	public void viewBookList(User user) {
		try {
			int stt = 1;
			Connection connection = SQLServerConnection.getConnection();
			String query = "select book_id, book_title, author, brief, publisher, category from Book";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs == null) {
				System.out.println("There is not any book in application");
			} else {
				System.out.format("%20s %20s %20s %20s %20s %20s %20s", "STT", "id", "Book Title", "Author", "Category",
						"Brief", "Publisher");
				System.out.println("\r\n");
				while (rs.next()) {
					
					System.out.format("%20s %20s %20s %20s %20s %20s %20s", stt, rs.getInt("book_id"),
							rs.getString("book_title"), rs.getString("author"), rs.getString("brief"),
							rs.getString("publisher"), rs.getString("category"));
					System.out.println("\n");
					stt++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//Ham read book
	@Override
	public void readBook(User user) {
		System.out.println("Please enter the book's id:");
		Scanner scanner = new Scanner(System.in);
		int idBook = scanner.nextInt();
		try {
			Connection connection = SQLServerConnection.getConnection();
			String query = "select content from Book where book_id = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, idBook);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == true) {
				System.out.println(rs.getString("content"));
			} else {
				System.out.println("This book is not exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//Ham search book
	@Override
	public void searchBook(User user) {
		System.out.println("Please select search type:\r\n" + "1.By name\r\n" + "2.By author\r\n" + "3.By category");
		Scanner scanner = new Scanner(System.in);
		group: while (true) {
			switch (scanner.nextInt()) {
			case 1: {
				SearchByColumn("name");
				break group;
			}
			case 2: {
				SearchByColumn("author");
				break group;
			}
			case 3: {
				SearchByColumn("category");
				break group;
			}
			default:
				System.out.println("Your choice is invalide! Try again!");
				break;
			}
		}
	}

	//Ham Search Book By Column (Name or author or category)
	public static void SearchByColumn(String attribute) {
		System.out.println("Please enter book's " + attribute + ": ");
		Scanner scanner = new Scanner(System.in);
		String keyWord = scanner.nextLine();
		if ("name".equals(attribute))
			attribute = "book_title";
		int stt = 1;
		try {
			Connection connection = SQLServerConnection.getConnection();
			String query1 = "select count(*) AS CountBook from Book where " + attribute + " like ?";
			String query2 = "select book_title, author, brief, publisher, category from Book where " + attribute
					+ " like ?";
			PreparedStatement ps1 = connection.prepareStatement(query1);
			PreparedStatement ps2 = connection.prepareStatement(query2);
			ps1.setString(1, "%" + keyWord + "%");
			ps2.setString(1, "%" + keyWord + "%");
			ResultSet rs1 = ps1.executeQuery();
			ResultSet rs2 = ps2.executeQuery();
			if (rs1.next() == false) {
				System.out.println("about 0 results found");
			} else {
				System.out.println("about " + rs1.getInt("CountBook") + " results");
				System.out.format("%20s %20s %20s %20s %20s %20s", "STT", "Book Title", "Author", "Category", "Brief",
						"Publisher\n");
				while (rs2.next()) {
					System.out.format("%20s %20s %20s %20s %20s %20s", stt, rs2.getString("book_title"),
							rs2.getString("author"), rs2.getString("category"), rs2.getString("brief"),
							rs2.getString("publisher"));
					System.out.println("\n");
					stt++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Ham View Book Case
	@Override
	public void viewBookCase(User user) {
		try {
			Connection connection = SQLServerConnection.getConnection();
			String query2 = "select count(*) AS CountBook from Contain where user_id = ?";
			String query3 = "select * from Book where book_id IN (select Contain.book_id from Contain where Contain.user_id = ?)";
			PreparedStatement ps2 = connection.prepareStatement(query2);
			PreparedStatement ps3 = connection.prepareStatement(query3);
			ps2.setInt(1, user.getUserId());
			ResultSet rs2 = ps2.executeQuery();
			if (rs2.next() == false) {
				System.out.println("Your BookCase is empty!");
			} else {
				int STT = 1;
				System.out.println("Your BookCase has " + rs2.getInt("CountBook") + " books:");
				System.out.format("%20s %20s %20s %20s %20s %20s", "STT", "Id", "Name", "Author", "Category",
						"Publisher\n");
				ps3.setInt(1, user.getUserId());
				ResultSet rs3 = ps3.executeQuery();
				while (rs3.next()) {
					System.out.format("%20s %20s %20s %20s %20s %20s", STT, rs3.getInt("book_id"),
							rs3.getString("book_title"), rs3.getString("author"), rs3.getString("category"),
							rs3.getString("publisher"));
					System.out.println("\n");
					STT++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Ham edit book case
	@Override
	public void editBookCase(User user) {
		System.out.println("Please enter the number:\r\n" + "1. Add a new book\r\n" + "2. Remove a book\r\n"
				+ "3. Clear BookCase");
		Scanner scanner = new Scanner(System.in);
		group: while (true) {
			switch (scanner.nextInt()) {
			case 1: {
				AddNewBook(user);
				break group;
			}
			case 2: {
				RemoveBook(user);
				break group;
			}
			case 3: {
				ClearBookCase(user);
				break group;
			}
			default:
				System.out.println("Your choice is invalide! Try again!");
				break;
			}
		}
	}

	//Ham add a new book to your bookcase
	public static void AddNewBook(User user) {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Your bookcase: ");
			UserServiceImpl userServiceImpl = new UserServiceImpl();
			userServiceImpl.viewBookList(user);
			Connection connection = SQLServerConnection.getConnection();
			int idBook = 0;
			while (true) {
				System.out.println("Please enter the Book's id:");
				String id1 = scanner.nextLine();
				if (validator.Validator.isId(id1)) {
					idBook = Integer.parseInt(id1);
					break;
				} else
					System.out.println("id is not correct. Please try again!");
			}
			if (validator.Validator.checkExistId(idBook)) {

				String query = "select book_id from Contain where book_id = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, idBook);
				ResultSet rs = ps.executeQuery();
				if (rs.next() == true) {
					System.out.println("This book is existed in your book case!");
				} else {
					String query1 = "Insert into Contain values(?,?)";
					PreparedStatement ps1 = connection.prepareStatement(query1);
					ps1.setInt(1, user.getUserId());
					ps1.setInt(2, idBook);
					ps1.execute();
					ps1.close();
					System.out.println("Add successfully!");
				}

			} else {
				System.out.println("This book is not exist!");
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	//Ham remove a book in your bookcase
	public static void RemoveBook(User user) {

		System.out.println("Please enter the book's id:");
		Scanner scanner = new Scanner(System.in);
		int idBook = 0;
		while (true) {
			System.out.println("Enter book's id: ");
			String id = scanner.nextLine();
			if (validator.Validator.isId(id)) {
				idBook = Integer.parseInt(id);
				break;
			} else
				System.out.println("id is not correct. Please try again!");
		}
		try {
			Connection connection = SQLServerConnection.getConnection();

			String query = "select book_id from Contain where user_id = ? and book_id = ?";
			String query2 = "delete from Contain where book_id = ? and user_id = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			ps.setInt(2, idBook);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == true) {
				PreparedStatement ps2 = connection.prepareStatement(query2);
				ps2.setInt(1, idBook);
				ps2.setInt(2, user.getUserId());
				ps2.executeUpdate();
				ps2.close();
				System.out.println("Remove is successfully!");
			} else {
				System.out.println("This book is not exist in your book case!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//Ham clear your bookcase
	public static void ClearBookCase(User user) {
		int idUser = 0;
		try {
			Connection connection = SQLServerConnection.getConnection();
			String query = "delete from Contain where user_id = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			ps.executeUpdate();
			System.out.println("Clear successfully!\r\n" + "Your BookCase is empty!");
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
