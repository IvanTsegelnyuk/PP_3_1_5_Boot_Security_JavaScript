package ru.kata.spring.boot_security.demo.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String email;

    @Column
    private String LastName;

    @Column
    private int age;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    @Column
    private String username;

    @Column
    private String password;

    public User(String name, String email, String LastName) {
        this.username = name;
        this.email = email;
        this.LastName = LastName;
    }

    public User(int id, String email, String LastName, Set<Role> roles, String username, String password, int age) {
        this.id = id;
        this.email = email;
        this.LastName = LastName;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public User() {

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
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

    public String printRoles() {
        StringBuilder sb = new StringBuilder();
        for (Role role: getRoles().stream().sorted(Comparator.comparing(Role::getName)).toList()) {
            sb.append(role.getName().substring(5)).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return getUsername() + " "
                + getLastName() + " "
                + getAge() + " "
                + getEmail() + " "
                + getPassword() + " "
                + getRoles();
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
