//package ru.kata.spring.boot_security.demo.controllers;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//@Controller
//@RequestMapping("/")
//public class MainController {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    RoleService roleService;
//
//    @GetMapping("/")
//    public String defaultPage(ModelMap model, @AuthenticationPrincipal User user) {
//        model.addAttribute("userModel", user);
//        return user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")) ? helloAdmin(model, user) : helloUser(model, user);
//    }
//
//    @GetMapping("/admin")
//    public String helloAdmin(ModelMap model, @AuthenticationPrincipal User user) {
//        model.addAttribute("adminInfo", user);
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "users";
//    }
//
//    @GetMapping("/user")
//    public String helloUser(ModelMap model, @AuthenticationPrincipal User user) {
//        model.addAttribute("userModel", user);
//        return "user";
//    }
//
//    @GetMapping("/admin/createUser")
//    public String createUser(ModelMap model, @AuthenticationPrincipal User loggedOnUser) {
//        User user = new User();
//        model.addAttribute("user", user);
//        model.addAttribute("loggedOnUser", loggedOnUser);
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "createUser";
//    }
//
//    @PostMapping("/admin/saveUser")
//    public String saveUser(@ModelAttribute("user") User newUser) {
//        userService.updateUser(newUser);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/admin/removeUser/{id}")
//    public String removeUser(@ModelAttribute("iter") User user) {
//        userService.removeUser(user);
//        return "redirect:/admin";
//    }
//
//    @PostMapping ("/admin/updateUser/{id}")
//    public String updateUser(@ModelAttribute("iter") User user) {;
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }
//}
