package com.von.api.board.model;

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
public class BoardDTO {
    private Long id;
    private String boardName;
    private String boardType;
    private List<Article> articles;
}