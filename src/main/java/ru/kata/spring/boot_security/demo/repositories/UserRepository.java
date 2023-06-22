package ru.kata.spring.boot_security.demo.repositories;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

@Repository
@DynamicUpdate
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query(value = "from User where username=:param")
//    @Transactional
//    @Transactional
    @Query(value = "select u from User u left join fetch u.roles where u.username = :param")
    User findUserByUsername(@Param(value = "param") String username);

    @Query(value = "update User set username=:username, email=:email, birthday=:birthday where id=:id")
    @Modifying
    void updateUserById(@Param("id") int id, @Param("username") String username, @Param("email") String email, @Param("birthday") String birthday);

}
