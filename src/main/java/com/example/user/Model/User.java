package com.example.user.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseModel{
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Enumerated(value = EnumType.ORDINAL)
    private Role role;
}


