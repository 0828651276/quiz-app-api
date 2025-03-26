package com.quiszerver.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user") // Đặt tên bảng trong database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    private Long id;

    private String username;
    private String password;
    private String email;
    private Integer phone;
    private Boolean is_teacher;

    // Constructor không tham số (cần thiết cho JPA)
    public User() {
    }

    // Constructor đầy đủ tham số
    public User(String username, String password, String email, Integer phone, Boolean is_teacher) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.is_teacher = is_teacher;
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {  // Sửa từ Number thành Integer
        return phone;
    }

    public void setPhone(Integer phone) { // Sửa từ int thành Integer
        this.phone = phone;
    }

    public Boolean getIs_teacher() {
        return is_teacher;
    }

    public void setIs_teacher(Boolean is_teacher) {
        this.is_teacher = is_teacher;
    }
}
