package ru.kata.spring.boot_security.demo.models;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;

@Entity
//@DynamicUpdate
@Table(name = "people")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column
//    private String name;

    @Column
    private String email;

    @Column
    private String birthday;



    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "people_roles", joinColumns = @JoinColumn(name = "users_id"),
//                inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles;

    @Column
    private String username;

    @Column
    private String password;

//    private boolean accountExpired;
//
//    private boolean accountLocked;
//
//    private boolean credentialsExpired;
//
//    private boolean enabled;

    public User(String name, String email, String birthday) {
        this.username = name;
        this.email = email;
        this.birthday = birthday;
    }

    public User(int id, String email, String birthday, Set<Role> roles, String username, String password) {
        this.id = id;
        this.email = email;
        this.birthday = birthday;
        this.roles = roles;
        this.username = username;
        this.password = password;
    }

    public User() {

    }

//    public String getName() {
//        return name;
//    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream().map(p -> new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
