package ru.kata.spring.boot_security.demo.models;

public class ConfirmPassword {
    private String password;
    private String confirmPassword;
    private int id;
    private String userName;


    public ConfirmPassword(String password, String confirmPassword, int id) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.id = id;
    }

    public ConfirmPassword() {

    }

    public ConfirmPassword(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ConfirmPassword(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
