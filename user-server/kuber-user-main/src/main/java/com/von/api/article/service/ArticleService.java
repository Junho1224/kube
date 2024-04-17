package com.von.api.article.service;


import java.util.*;

import com.von.api.article.model.Article;
import com.von.api.article.model.ArticleDTO;
import com.von.api.common.service.CommandService;
import com.von.api.common.service.QueryService;


public interface ArticleService extends CommandService<ArticleDTO>, QueryService<ArticleDTO>{

    default Article dtoToEntity(ArticleDTO dto){    //dto 를 entity로 바꾸는 것
        return Article.builder()
        .id(dto.getId())
        .title(dto.getTitle())
        .content(dto.getContent())
        .build();
    }

    default ArticleDTO entityToDto(Article ent){ //entity 를 dto로 바꾸는 것
        return ArticleDTO.builder()
        .id(ent.getId())
        .title(ent.getTitle())
        .content(ent.getContent())
        .build();
    }
}


