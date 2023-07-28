package com.Geekster.MusicStreamingAPI.Models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpOutput {
    private Boolean signupOutputStatus;
    private String signupOutputMessage;

}
