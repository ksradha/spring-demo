package com.example.springdemo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info_t")
public class User {

    @Id
    @GeneratedValue
    @Column(name="user_id")
    private int userId;
    @Column(name="user_name")
    @NotNull(message = "Name cannot be null")
	private String userName;
    @Column(name="password")
	private String password;


}
