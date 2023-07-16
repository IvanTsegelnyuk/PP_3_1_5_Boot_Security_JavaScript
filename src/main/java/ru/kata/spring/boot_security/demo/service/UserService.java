package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void saveUser(User user);

    void removeUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    void updateUser(User user);

    void deleteUser(Integer id);

}
