package ru.kata.spring.boot_security.demo.repositories;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

@Repository
@DynamicUpdate
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select u from User u left join fetch u.roles where u.email = :param")
    User findUserByUsername(@Param(value = "param") String email);
}
