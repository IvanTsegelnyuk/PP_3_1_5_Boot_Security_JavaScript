package ru.kata.spring.boot_security.demo.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name = "people")
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

    @Transient
    private String rolesAsInt;

    public String getRolesAsInt() {
        return rolesAsInt;
    }

    public void setRolesAsInt(String rolesAsInt) {
        this.rolesAsInt = rolesAsInt;
    }

//    @ManyToMany(fetch = FetchType.LAZY)
//    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "people_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Role> roles;

    @Column
    private String username;

    @Column
    private String password;

    private String confirmPassword;

    public User(String name, String email, String LastName) {
        this.username = name;
        this.email = email;
        this.LastName = LastName;
    }

    public User(int id, String email, String LastName, Set<Role> roles, String username, String password, String confirmPassword, int age) {
        this.id = id;
        this.email = email;
        this.LastName = LastName;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public void setLastName(String birthday) {
        this.LastName = birthday;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
