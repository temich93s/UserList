package com.userlist.dao;

import com.userlist.exception.UserNotFoundException;
import com.userlist.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getUsers() {
        return entityManager
                .createQuery("from User", User.class)
                .getResultList();
    }

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
           throw new UserNotFoundException("User with id " + id + " not found");
        }
        entityManager.remove(user);
    }

    public void updateUser(User user) {
        User foundUser = entityManager.find(User.class, user.getId());
        if (foundUser == null) {
            throw new UserNotFoundException("User with id " + user.getId() + " not found");
        }
        entityManager.merge(user);
    }
}
