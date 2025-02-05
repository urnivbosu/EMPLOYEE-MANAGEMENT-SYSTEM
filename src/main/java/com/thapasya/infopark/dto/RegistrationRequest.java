package com.thapasya.infopark.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationRequest(String email, String role, String password) {
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // You can make role optional and set a default if you want
    @NotBlank(message = "Role is required")
    private String role;
}
