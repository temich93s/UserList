package com.userlist.controller;

import com.userlist.exception.UserNotFoundException;
import com.userlist.model.User;
import com.userlist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String userList(ModelMap model) {
        try {
            List<User> users = userService.getUsers();
            model.addAttribute("users", users);
            model.addAttribute("loadSuccess", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("message", "Server error, try later");
            model.addAttribute("loadSuccess", false);
        }
        return "index";
    }

    @GetMapping(value = "/addUser")
    public String addUser(ModelMap model) {
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.addUser(user);
            redirectAttributes.addFlashAttribute("message", "User added successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Server error, try later");
        }
        return "redirect:/";
    }

    @PostMapping(value = "/removeUser/{id}")
    public String removeUser(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            userService.removeUserById(id);
            redirectAttributes.addFlashAttribute("message", "User removed successfully");
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", "User not found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Server error, try later");
        }
        return "redirect:/";
    }

    @GetMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable long id, ModelMap model) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            model.addAttribute("message", "User not found");
        }
        return "updateUser";
    }

    @PostMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable long id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            user.setId(id);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "User updated successfully");
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", "User not found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Server error, try later");
        }
        return "redirect:/";
    }
}
