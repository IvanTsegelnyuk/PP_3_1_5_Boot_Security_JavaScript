package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

//    @Autowired
//    private UserDao userDao;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void removeUser(User user) {
//        userDao.removeUser(user);
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void add(User user) {
//        userDao.createUser(user);
//        user.setRoles(Set.of(new Role(2), new Role(1)));
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, String username, String email, String birthday) {
        userRepository.updateUserById(id, username, email, birthday);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
//        return userDao.getAllUsers();
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        //return userDao.getUserById(id);
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

//    @Override
//    @Transactional
//    @Query(value = "from User where username=:param")
//    public User findUserByUsername(@Param("param") String username) {
//
//    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
        return user;
    }
}
