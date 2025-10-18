package com.devees.authaudit.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class RegisterRequestDto {
    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;
}
