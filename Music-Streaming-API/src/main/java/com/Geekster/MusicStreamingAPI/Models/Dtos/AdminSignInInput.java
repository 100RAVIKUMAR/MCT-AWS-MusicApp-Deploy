package com.Geekster.MusicStreamingAPI.Models.Dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSignInInput {
    @Email
    private String email;
    private String password;
}
