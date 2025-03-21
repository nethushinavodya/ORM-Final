package dao.custom;

import dao.CrudDAO;
import entity.User;

import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    boolean registerUser(User user);

    User loginUser(String username);

    boolean ifHaveAdmin();

    User search(String id);

}
