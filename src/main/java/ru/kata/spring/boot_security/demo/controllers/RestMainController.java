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

import java.util.List;

@RestController
@RequestMapping("/")
public class RestMainController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @GetMapping("api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.getAllUsers();
        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("api/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        System.out.println("Удаление пользователя");
        userService.deleteUser(id);
    }

    @PutMapping("api/edit")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @PostMapping("api/users")
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        userService.saveUser(user);
        ResponseEntity<User> responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        return responseEntity;
    }


    @GetMapping("api/loginUser")
    public ResponseEntity<User> getAuthenticatedUser(@AuthenticationPrincipal User user) {
        ResponseEntity<User> responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("api/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        ResponseEntity<List<Role>> roles = new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
        return roles;
    }

}
