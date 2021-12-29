package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Book;
import connection.SQLServerConnection;

public class AdminServiceImpl implements AdminService {
	private static Scanner sc = new Scanner(System.in);

	//Ham tao moi sach
	@Override
	public void CreateBook() {
		Connection connection = SQLServerConnection.getConnection();
		Book b = new Book();
		b.input();

		String sql = "INSERT INTO Book VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, b.getBookTitle());
			ps.setString(2, b.getAuthor());
			ps.setString(3, b.getBrief());
			ps.setString(4, b.getPublisher());
			ps.setString(5, b.getContent());
			ps.setString(6, b.getCategory());
			ps.execute();
			ps.close();
			connection.close();
			System.out.println("book successfully created!");
		} catch (SQLException e) {
			System.out.println("book fail created!");
		}

	}

	//Ham Update Book
	@Override
	public void UpdateBook() {
		Connection connection = SQLServerConnection.getConnection();
		List<Book> list = getListBook();
		showListBook(list);

		int id = 0;
		while (true) {
			System.out.println("Enter Book' id: ");
			String id1 = sc.nextLine();
			if (validator.Validator.isId(id1)) {
				id = Integer.parseInt(id1);
				break;
			} else
				System.out.println("id is not correct. Please try again!");
		}

		if (validator.Validator.checkExistId(id)) {
			Book b = new Book();
			b.input();

			String sql = "UPDATE Book SET book_title = ?,author = ?,brief = ?,publisher = ?,content = ?,category = ? WHERE book_id = ?";

			try {
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, b.getBookTitle());
				ps.setString(2, b.getAuthor());
				ps.setString(3, b.getBrief());
				ps.setString(4, b.getPublisher());
				ps.setString(5, b.getContent());
				ps.setString(6, b.getCategory());
				ps.setInt(7, id);
				ps.executeUpdate();
				ps.close();
				connection.close();
				System.out.println("book successfully updated!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("This book is not exist!");
		}

	}

	
	//Ham Delete Book
	@Override
	public void DeleteBook() {
		Connection connection = SQLServerConnection.getConnection();
		List<Book> list = getListBook();
		showListBook(list);

		int id = 0;
		while (true) {
			System.out.println("Enter book's id: ");
			String id1 = sc.nextLine();
			if (validator.Validator.isId(id1)) {
				id = Integer.parseInt(id1);
				break;
			} else
				System.out.println("id is not correct. Please try again!");
		}

		if (validator.Validator.checkExistId(id)) {
			String sql1 = "delete  from Contain where book_id = ?";
			String sql = "DELETE FROM Book WHERE book_id = ?";
			try {
				PreparedStatement ps = connection.prepareStatement(sql);
				PreparedStatement ps1 = connection.prepareStatement(sql1);
				ps1.setInt(1, id);
				ps1.executeUpdate();
				ps1.close();
				ps.setInt(1, id);
				ps.executeUpdate();
				ps.close();
				connection.close();
				System.out.println("book successfully deleted!");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else
			System.out.println("This book is not exist!");

	}

	//Ham show danh sach book
	public static void showListBook(List<Book> list) {
		for (Book b : list)
			System.out.println(b.toString());
	}
	
	//ham lay danh sach Book
	public static List<Book> getListBook() {
		Connection connection = SQLServerConnection.getConnection();
		List<Book> list = new ArrayList<>();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Book");
			while (rs.next()) {
				Book b = new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				list.add(b);
			}
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
