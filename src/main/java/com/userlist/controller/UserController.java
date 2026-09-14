package com.userlist.controller;

import com.userlist.dao.UserDao;
import com.userlist.dao.UserDaoImpl;
import com.userlist.model.User;
import com.userlist.service.UserService;
import com.userlist.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String userList(ModelMap model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping(value = "/addUser")
    public String addUser(ModelMap model) {
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, ModelMap model) {
        user.setId(ThreadLocalRandom.current().nextLong());
        boolean isAddUserSuccess = userService.addUser(user);
        if (isAddUserSuccess) {
            model.addAttribute("message", "User add successfully");
        } else {
            model.addAttribute("message", "User not added");
        }
        return "addUser";
    }

    @GetMapping(value = "/removeUser")
    public String removeUser(ModelMap model) {
        return "removeUser";
    }

    @PostMapping(value = "/removeUser")
    public String removeUser(@RequestParam Long id, ModelMap model) {
        boolean isRemoveUserSuccess = userService.removeUserById(id);
        if (isRemoveUserSuccess) {
            model.addAttribute("message", "User removed successfully");
        } else {
            model.addAttribute("message", "User not found");
        }
        return "removeUser";
    }

    @GetMapping(value = "/updateUser")
    public String updateUser(ModelMap model) {
        return "updateUser";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute User user, ModelMap model) {
        boolean isUpdateUserSuccess = userService.updateUser(user);
        if (isUpdateUserSuccess) {
            model.addAttribute("message", "User update successfully");
        } else {
            model.addAttribute("message", "User not found");
        }
        return "updateUser";
    }



    // Private

    private void testBD () {
        System.out.println("-!!!-");

        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);

        System.out.println("--------");

        userService.getUsers().forEach(user -> {
            System.out.println(user.toString());
        });

        System.out.println("--------");

        userService.addUser(
                new User(2, "a1", "b2", 22, "c2")
        );
        userService.getUsers().forEach(user -> {
            System.out.println(user.toString());
        });

        System.out.println("--------");

        userService.updateUser(
                new User(2, "a1", "b2", 33, "c2")
        );
        userService.getUsers().forEach(user -> {
            System.out.println(user.toString());
        });

        System.out.println("--------");

        userService.removeUserById(1);
        userService.getUsers().forEach(user -> {
            System.out.println(user.toString());
        });
    }
}
