package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.ConfirmPassword;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public String defaultPage(ModelMap model, @AuthenticationPrincipal User user) {
        model.addAttribute("userModel", user);
        return user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")) ? helloAdmin(model, user) : helloUser(model, user);
    }

//    @PostMapping("/test")
//    public String test(ModelMap model, User user) {
//        model.addAttribute("testModelForm", user);
//        return "users";
//    }

    @GetMapping("/admin")
    public String helloAdmin(ModelMap model, @AuthenticationPrincipal User user) {
//        User updatedUser = new User();
        model.addAttribute("adminInfo", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
//        model.addAttribute("updatedUser", updatedUser);
//        model.addAttribute("user", new User());
//        model.addAttribute("user", user);
//        System.out.println(model.getAttribute("adminInfo"));
        return "users";
    }

    @GetMapping("/user")
    public String helloUser(ModelMap model, @AuthenticationPrincipal User user) {
        model.addAttribute("userModel", user);
//        model.addAttribute("loginUser", )
        return "user";
    }

    @GetMapping("/admin/createUser")
    public String createUser(ModelMap model, @AuthenticationPrincipal User loggedOnUser) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("loggedOnUser", loggedOnUser);
        model.addAttribute("roles", roleService.getAllRoles());
        return "createUser";
    }

    @GetMapping("/admin/addNewUser")
    public String addNewUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "addUser";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User newUser) {
//    public String saveUser(ModelMap model, User newUser) {
//        System.out.println(newUser.getRolesAsString());
//        System.out.println(newUser.getRolesAsInt());
//        userService.add(newUser);
        userService.updateUser(newUser);
        return "redirect:/admin";
    }

//    @GetMapping("/admin/removeUser/{id}")
//    public String removeUser(@PathVariable int id) {
//        User user = userService.getUserById(id);
//        userService.removeUser(user);
//        return "redirect:/admin";
//    }

    @PostMapping("/admin/removeUser/{id}")
    public String removeUser(@ModelAttribute("iter") User user) {
        System.out.println("Удаление пользователя");
//        User deletedUser = userService.getUserById(user.getId());
        userService.removeUser(user);
        return "redirect:/admin";
    }

//    @GetMapping ("/admin/updateUser/{id}")
//    public String updateUser(@PathVariable int id, ModelMap model) {
//        System.out.println("Обновление пользователя");
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        return "updateUser";
//    }

    @PostMapping ("/admin/updateUser/{id}")
    public String updateUser(@ModelAttribute("iter") User user) {
        System.out.println("Обновление пользователя");
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
        userService.updateUser(user);
        return "redirect:/admin";
    }

//    @PostMapping("/admin/updateUser")
//    public String updateUserData(User user) {
//        userService.updateUser(user.getId(), user.getUsername(), user.getEmail(), user.getBirthday());
//        return "redirect:/admin";
//    }

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
