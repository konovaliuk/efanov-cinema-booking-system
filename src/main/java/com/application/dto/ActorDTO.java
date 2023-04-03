package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActorDTO {
    private String first_name;
    private String last_name;
    private String role;
}
