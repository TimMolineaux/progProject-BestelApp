// UserService.java
package org.example.bestelapp.Service;

import org.example.bestelapp.DAO.UserDAO;
import org.example.bestelapp.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public void addUser(User user) {
        dao.addUser(user);
    }

    public void deleteUser(Long id) {
        dao.deleteUser(id);
    }
}
