package com.von.api.common.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.von.api.article.model.ArticleDTO;

public interface QueryService<T> {

    List<T> findAll() throws SQLException;
    Optional<T> findById(Long id);
    long count();
    Boolean existById(Long id);
    // List<ArticleDTO> findArticlesByTitle(String title);
    // List<ArticleDTO> findArticlesByContent(String content);

    
}
