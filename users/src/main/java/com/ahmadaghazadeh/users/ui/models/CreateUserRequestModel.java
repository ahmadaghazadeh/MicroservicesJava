package com.ahmadaghazadeh.users.ui.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequestModel {
    @NotNull(message = "First name cannot be null")
    @Size(min = 2,message = "First name must not be less than two characters")
    private String firstName;
    @NotNull(message = "LastName name cannot be null")
    @Size(min = 2,message = "LastName name must not be less than two characters")
    private String lastName;
    @NotNull(message = "Password name cannot be null")
    @Size(min = 7, max = 16,message = "Password name must not be less than two characters")
    private String password;
    @NotNull(message = "Email name cannot be null")
    @Email
    private String email;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2,message = "First name must not be less than two characters")
    private String userName;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
