package Service;

import model.User;

public interface UserService {
	User login();
    void viewBookList(User user);
    void readBook(User user);
    void searchBook(User user);
    void viewBookCase(User user);
    void editBookCase(User user);
}
