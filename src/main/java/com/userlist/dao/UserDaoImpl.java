package com.userlist.dao;

import com.userlist.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private List<User> users = new ArrayList<>(List.of(
            new User(1, "a", "b", 11, "c")
    ));

    public List<User> getUsers() {
        return users;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean removeUserById(long id) {
        return users.removeIf(user -> user.getId() == id);
    }

    public boolean updateUser(User updatedUser) {
        return users.stream()
                .filter(user -> user.getId() == updatedUser.getId())
                .findFirst()
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setAge(updatedUser.getAge());
                    user.setEmail(updatedUser.getEmail());
                    return true;
                })
                .orElse(false);
    }
}
