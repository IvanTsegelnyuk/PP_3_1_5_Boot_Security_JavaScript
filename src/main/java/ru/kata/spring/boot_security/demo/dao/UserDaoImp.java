//package ru.kata.spring.boot_security.demo.dao;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import ru.kata.spring.boot_security.demo.models.User;
//
//import java.util.List;
//
//
//@Repository
//public class UserDaoImp implements UserDao {
//
//    @PersistenceContext
//    EntityManager entityManager;
//
//    @Override
//    public void createUser(User user) {
//        entityManager.merge(user);
//    }
//
//    @Override
//    public void removeUser(User user) {
//        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        List users = entityManager.createQuery("from User", User.class).getResultList();
//        return users;
//    }
//
//    @Override
//    public User getUserById(int id) {
//        User user = entityManager.find(User.class, id);
//        return user != null ? user : new User(null, null, null);
//    }
//}
