package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.ConfirmPassword;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String defaultPage(ModelMap model, @AuthenticationPrincipal User user) {
        model.addAttribute("userModel", user);
        return user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")) ? helloAdmin(model) : helloUser(model, user);
    }

    @GetMapping("/admin")
    public String helloAdmin(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/user")
    public String helloUser(ModelMap model, @AuthenticationPrincipal User user) {
        model.addAttribute("userModel", user);
        return "user";
    }

    @GetMapping("/admin/createUser")
    public String createUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "createUser";
    }

    @GetMapping("/admin/addNewUser")
    public String addNewUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "addUser";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(ModelMap model, User newUser) {
        userService.add(newUser);
        return "redirect:/admin";
    }

    @GetMapping("/admin/removeUser/{id}")
    public String removeUser(@PathVariable int id) {
        User user = userService.getUserById(id);
        userService.removeUser(user);
        return "redirect:/admin";
    }

    @GetMapping ("/admin/updateUser/{id}")
    public String updateUser(@PathVariable int id, ModelMap model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/admin/updateUser")
    public String updateUserData(User user) {
        userService.updateUser(user.getId(), user.getUsername(), user.getEmail(), user.getBirthday());
        return "redirect:/admin";
    }

    @GetMapping("/admin/changePassword/{id}")
    public String changePassword(@PathVariable int id, ModelMap model) {
        model.addAttribute("confirmPassword", new ConfirmPassword(id, userService.getUserById(id).getUsername()));
        return "changePassword";
    }

    @GetMapping("/admin/changePassword/error")
    public String changePassword(@RequestParam("id") int id, @ModelAttribute("confirmPassword") ConfirmPassword confirmPassword) {
        confirmPassword.setId(id);
        confirmPassword.setUserName(userService.getUserById(id).getUsername());
        return "passwordsDoNotMatch";
    }

    @PostMapping("/admin/changePassword")
    public String changePassword(@ModelAttribute("confirmPassword") ConfirmPassword confirmPassword) {
        if (confirmPassword.getPassword().equals(confirmPassword.getConfirmPassword())) {
            userService.updatePasswordById(confirmPassword.getId(), confirmPassword.getPassword());
            return "redirect:/admin";
        }
        else return "redirect:/admin/changePassword/error?id=" + confirmPassword.getId();
    }
}
