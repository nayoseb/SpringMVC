package com.nhnacademy.nhnmart.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    private String id;

    @NotBlank
    private String pwd;
}
