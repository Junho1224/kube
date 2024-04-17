package com.von.api.article.model;

import org.springframework.stereotype.Component;

import com.von.api.board.model.Board;
import com.von.api.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private User writer;
    private Board board;
    
}
