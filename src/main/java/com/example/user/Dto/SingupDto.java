package com.example.user.Dto;

import com.example.user.Model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingupDto {
    private String email;
    private String name;
    private String password;
    private String role;
}
