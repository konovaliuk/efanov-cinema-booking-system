package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private final long ID;
    private String email;
    private String login;
    private String password;
    private boolean isAdmin = false;
}
