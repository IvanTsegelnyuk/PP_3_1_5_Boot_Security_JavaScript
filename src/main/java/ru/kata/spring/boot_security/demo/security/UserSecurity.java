package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import ru.kata.spring.boot_security.demo.dao.UserDao;

//@Service("userSecurity")
//public class UserSecurity implements UserDetailsService {
//
//    private UserDao userDao;
//
//    @Autowired
//    public UserSecurity(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        return null;
//    }
//}
