package com.stepanov.documentclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Size(min = 3, max = 20, message = "длина должна быть от 3 до 15 символов")
    private String login;
    @Size(min = 5, message = "длина должна быть не меньше 5 символов")
    private String password;
    private String verifyPassword;
}

