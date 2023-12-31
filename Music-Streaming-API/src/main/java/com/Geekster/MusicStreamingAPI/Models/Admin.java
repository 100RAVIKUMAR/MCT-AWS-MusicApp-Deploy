package com.Geekster.MusicStreamingAPI.Models;

import com.Geekster.MusicStreamingAPI.Models.Enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String adminName;
    @Pattern(regexp = "^.+@musicadmin\\.com$")
    @Column(unique = true)
    private String adminEmail;
    @NotBlank
    private String adminPassword;
    private Gender adminGender;
}
