package com.von.api.user.model;
import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String job;
    private String regDate;
    private String modDate;
    private String token;
    
}
