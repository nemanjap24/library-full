package com.polo.librarybackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;
    @Column(name= "username", nullable = false, unique = true)
    private String username;
    @Column(name= "password", nullable = false, unique = true)
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @Column(name= "email", nullable = false,unique = true)
    private String email;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role.getRoleName();
    }
    // todo: please update
    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role.getRoleName() +
                ", email='" + email + '\'' +
                '}';
    }
}
