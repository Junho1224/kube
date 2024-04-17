package com.von.api.user.model;

import java.util.List;

import com.von.api.article.model.Article;
import com.von.api.common.model.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@ToString(exclude = { "id" })
@Setter
public class User extends BaseEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String job;


    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE) // orphanRemoval = flase가 default
    private List<Article> articles;




}