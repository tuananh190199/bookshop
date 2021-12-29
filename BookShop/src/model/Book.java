package model;

import java.util.Scanner;

public class Book {
	private int idBook;
	private String bookTitle;
	private String author;
	private String brief;
	private String publisher;
	private String content;
	private String category;

	public Book(int idBook, String bookTitle, String author, String brief, String publisher, String content,
			String category) {
		super();
		this.idBook = idBook;
		this.bookTitle = bookTitle;
		this.author = author;
		this.brief = brief;
		this.publisher = publisher;
		this.content = content;
		this.category = category;
	}

	public Book() {
		super();
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "Book [idBook=" + idBook + ", bookTitle=" + bookTitle + ", author=" + author + ", brief=" + brief
				+ ", publisher=" + publisher + ", category=" + category + "]";
	}
	
	public void input() {
		System.out.println("Please enter the number: ");
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Enter the name: ");
		bookTitle = sc.nextLine();

		System.out.println("2. Enter the author: ");
		author = sc.nextLine();

		System.out.println("4. Enter the brief: ");
		brief = sc.nextLine();

		System.out.println("5. Enter the publisher:");
		publisher = sc.nextLine();

		System.out.println("6. Enter the content: (End by enter: '/end')");
		StringBuilder content1 = new StringBuilder("");
		while (true) {
			String a = sc.nextLine();
			if (a.equals("/end"))
				break;
			else {
				content1.append(a);
				content1.append("\n");
			}
		}
		content = content1.toString();
		System.out.println("7. Enter the category:");
		category = sc.nextLine();
		
	}

}
