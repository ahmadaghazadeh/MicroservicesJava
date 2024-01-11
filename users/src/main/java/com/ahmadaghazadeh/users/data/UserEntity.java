package com.ahmadaghazadeh.users.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,length =50)
    private String firstName;
    @Column(nullable = false,length =50)
    private String lastName;

    private String password;
    @Column(nullable = false,length =120,unique = true)
    private String email;

    @Column(nullable = false,length =120,unique = true)
    private String userName;

    @Column(nullable = false,unique = true)
    private String userId;
    @Column(nullable = false)
    private String encryptedPassword;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
