package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Actor implements Serializable {
    private final long ID;
    private String first_name;
    private String last_name;
    private String role;
}
