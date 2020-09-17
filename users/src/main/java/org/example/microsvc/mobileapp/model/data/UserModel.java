package org.example.microsvc.mobileapp.model.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class UserModel {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    @Column(name="firstname",nullable = false)
    private String firstName;
    @Column(name="lastname",nullable = false)
    private String lastName;
    @Column(name="email",nullable = false)
    private String email;
    @Column(name="userid",nullable = false, unique = true)
    private String userId;
    @Column(name="encryptedpassword",nullable = false)
    private String encryptedPassword;
}
