package validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Book;
import Service.AdminServiceImpl;

public class Validator {
	private static final String VALID_IDBOOK_REGEX = "\\d{4,}";

	public static boolean checkExistId(int id) {
		List<Book> list = AdminServiceImpl.getListBook();
		for (Book b : list) {
			if (b.getIdBook() == id) {
				return true;
			}	
		}
		return false;
	}

	public static boolean isId(String id) {
		Pattern pattern = Pattern.compile(VALID_IDBOOK_REGEX);
		Matcher matcher = pattern.matcher(id);
		return matcher.matches();
	}

}
