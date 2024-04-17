package com.von.api.user.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.von.api.article.model.Article;

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
@Log4j2
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String job;
    private List<Article> articles;
    
}
