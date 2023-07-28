package com.Geekster.MusicStreamingAPI.Models;

import com.Geekster.MusicStreamingAPI.Models.Enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    @Email
    @Column(unique = true)
    private String userEmail;

    @NotBlank
    private String userPassword;

    @Enumerated(EnumType.STRING)
    private Gender userGender;
}
