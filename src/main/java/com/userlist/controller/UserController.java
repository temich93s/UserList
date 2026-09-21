package com.userlist.controller;

import com.userlist.dto.UserDto;
import com.userlist.exception.UserNotFoundException;
import com.userlist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
            List<UserDto> userDtoList = userService.getUsers();
            model.addAttribute("users", userDtoList);
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
        model.addAttribute("userDto", new UserDto());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "addUser";
        }
        try {
            userService.addUser(userDto);
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
            UserDto userDto = userService.getUserById(id);
            model.addAttribute("userDto", userDto);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            model.addAttribute("message", "User not found");
        }
        return "updateUser";
    }

    @PostMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable long id, @Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/updateUser";
        }
        try {
            userDto.setId(id);
            userService.updateUser(userDto);
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
