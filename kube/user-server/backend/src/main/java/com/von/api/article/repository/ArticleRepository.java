package com.von.api.article.repository;
import java.util.*;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.von.api.article.model.Article;
import com.von.api.article.model.ArticleDTO;



@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

    //JPQL Default
    @Query("select a from articles a where a.board.id = :boardId order by a.id desc")
    public List<Article> getArticlesByBoardId(@Param("boardId") Long boardId);

    // //Native
    // @Query(value ="select a from articles a where a.board.id = :1", nativeQuery = true)
    // public List<Map<String, Object>> getQnaArticles(@Param("boardId") Long boardId);


    // @Query(value="select a from articles a where a.board.id = :2", nativeQuery = true)
    // public List<Map<String, Object>> getReviewArticles(@Param("boardId") Long boardId);

    // String articleDtoMapping = "new com.von.api.article.model.ArticleDTO("+
    // "a.id, a.title, a.content, a.writer.id a.board.id" +
    // ", a.regDate, a.modDate)";
    // @Query("SELECT"+articleDtoMapping+"FROM articles a WHERE a.board.id = :boardId")
    // public List<ArticleDTO> getArticleDTOsByBoardId(@Param("boardId")Long boardId);

    List<Article> findAllByOrderByIdDesc();

    
} 