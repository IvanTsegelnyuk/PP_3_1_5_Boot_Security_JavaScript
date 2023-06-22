package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;

//import ru.ivan.pp_3_1_2_spring_boot.models.User;
//import ru.ivan.pp_3_1_2_spring_boot.service.UserService;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String defaultPage(ModelMap model, @AuthenticationPrincipal User user) {
        System.out.println(user.getEmail());
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
        System.out.println(user.getEmail());
//        model.addAttribute("user", userService.getAllUsers());
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

//    @PostMapping("/admin/saveUser")
//    public String saveUser(@ModelAttribute("user") User user) {
//        user.setRoles(Set.of(new Role(2)));
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userService.add(user);
//
////        userService.updateUser(user.getId(), user.getUsername(), user.getEmail(), user.getBirthday());
//        return "redirect:/admin";
//    }

    @PostMapping("/admin/saveUser")
    public String saveUser(ModelMap model, User newUser) {
        System.out.println("Выполнение метода добавления пользователя");
        newUser = (User) model.getAttribute("user");
        newUser.setRoles(Set.of(new Role(2)));
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userService.add(newUser);
        System.out.println("Конец метода добавления пользователя");
        return "redirect:/admin";
    }

    @GetMapping("/admin/removeUser/{id}")
    public String removeUser(@PathVariable int id) {
        User user = userService.getUserById(id);
        userService.removeUser(user);
        return "redirect:/admin";
    }

    @GetMapping ("/admin/updateUser/{id}")
    public String updateUser(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "addUser";
    }
}
