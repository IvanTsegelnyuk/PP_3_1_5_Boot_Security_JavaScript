package ru.kata.spring.boot_security.demo.repositories;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

@Repository
@DynamicUpdate
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select u from User u left join fetch u.roles where u.username = :param")
    User findUserByUsername(@Param(value = "param") String username);

    @Query(value = "update User set username=:username, email=:email, LastName=:birthday where id=:id")
    @Modifying
    void updateUserById(@Param("id") int id, @Param("username") String username, @Param("email") String email, @Param("birthday") String birthday);

    @Modifying
    @Query(value = "update User set password=:password where id=:id")
    void updateUserPassword(@Param("id") int id, @Param("password") String password);

}
