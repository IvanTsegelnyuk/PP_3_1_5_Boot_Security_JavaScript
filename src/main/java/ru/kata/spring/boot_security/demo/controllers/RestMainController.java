package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RestMainController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(ModelMap model, @AuthenticationPrincipal User user) {
        List<User> list = userService.getAllUsers();
        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping("/edit")
    public void updateUser(@RequestBody User user) {
        System.out.println(user);
        userService.updateUser(user);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        System.out.println(user);
        System.out.println(user.getRoles());
//        System.out.println(user.getRoles());
        userService.saveUser(user);
        ResponseEntity<User> responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        System.out.println(responseEntity);
        return responseEntity;
    }


    @GetMapping("/loginUser")
    public ResponseEntity<User> getAuthenticatedUser(@AuthenticationPrincipal User user) {
        ResponseEntity<User> responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        System.out.println("Авторизованный юзер: " + user);
        System.out.println(responseEntity);
        return responseEntity;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        ResponseEntity<List<Role>> roles = new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
        return roles;
    }

}
