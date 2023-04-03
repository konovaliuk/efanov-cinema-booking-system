package com.application.dto;

import com.application.model.Screening;
import com.application.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationDTO {
    private User user;
    private Screening screening;
    private boolean reserved = false;
    private boolean paid = false;
    private boolean active = false;
}
