package com.von.api.article.repository;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.von.api.article.model.Article;
import com.von.api.user.model.User;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

    
} 