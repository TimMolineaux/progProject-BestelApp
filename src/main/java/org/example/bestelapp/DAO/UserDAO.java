// UserDAO.java
package org.example.bestelapp.DAO;

import org.example.bestelapp.Model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserDAO {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
    }

    public void deleteUser(Long id) {
        users.removeIf(u -> u.getId().equals(id));
    }
}
