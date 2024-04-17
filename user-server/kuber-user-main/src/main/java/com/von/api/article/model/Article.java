package com.von.api.article.model;

import com.von.api.board.model.Board;
import com.von.api.common.model.BaseEntity;
import com.von.api.user.model.User;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"id"})
@Entity(name = "articles")
@Builder
public class Article extends BaseEntity{

    @Id
    @Column(name ="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer", nullable = true)
    private User writer;

    @ManyToOne
    @JoinColumn(name = "board", nullable = true)
    private Board board;



    public static Article of(Long id, String title, String content){
        Article article = new Article();
        article.id = id;
        article.title = title;
        article.content = content;

        return article;
        
    }

   

}
