package com.von.api.board.model;


import java.util.List;

import com.von.api.article.model.Article;
import com.von.api.common.model.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = {"id"})
@Entity(name="boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Board extends BaseEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    private String title;
    private String description;

    @OneToMany(mappedBy ="board", cascade = CascadeType.REMOVE) // fetch = FetchType.LAZY LAZY가 default값  orphanRemoval = flase
    private List<Article> articles;

}