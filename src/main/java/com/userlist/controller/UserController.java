package com.userlist.controller;

import com.userlist.dto.UserDto;
import com.userlist.exception.UserNotFoundException;
import com.userlist.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
            logger.error("Failed to userList", e);
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
    public String addUser(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "addUser";
        }
        try {
            userService.addUser(userDto);
            redirectAttributes.addFlashAttribute("message", "User added successfully");
        } catch (Exception e) {
            logger.error("Failed to addUser", e);
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
            logger.error("Failed to removeUser id {}", id, e);
            redirectAttributes.addFlashAttribute("message", "User not found");
        } catch (Exception e) {
            logger.error("Failed to removeUser id {}", id, e);
            redirectAttributes.addFlashAttribute("message", "Server error, try later");
        }
        return "redirect:/";
    }

    @GetMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable long id, ModelMap model, RedirectAttributes redirectAttributes) {
        try {
            UserDto userDto = userService.getUserById(id);
            model.addAttribute("userDto", userDto);
            return "updateUser";
        } catch (UserNotFoundException e) {
            logger.error("Failed to updateUser id {}", id, e);
            redirectAttributes.addFlashAttribute("message", "User not found");
            return "redirect:/";
        }
    }

    @PostMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable long id, @Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "updateUser";
        }
        try {
            userDto.setId(id);
            userService.updateUser(userDto);
            redirectAttributes.addFlashAttribute("message", "User updated successfully");
        } catch (UserNotFoundException e) {
            logger.error("Failed to updateUser id {}", id, e);
            redirectAttributes.addFlashAttribute("message", "User not found");
        } catch (Exception e) {
            logger.error("Failed to updateUser id {}", id, e);
            redirectAttributes.addFlashAttribute("message", "Server error, try later");
        }
        return "redirect:/";
    }
}
