package com.von.api.article.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long boardId;
    private String regDate;
    private String modDate;
    
}
