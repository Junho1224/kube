package com.von.api.article.service;

import com.von.api.article.model.Article;
import com.von.api.article.model.ArticleDTO;
import com.von.api.article.repository.ArticleRepository;
import com.von.api.common.AbstractService;
import com.von.api.common.component.MessengerVO;


import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repository;


    @Override
    public MessengerVO save(ArticleDTO t){
        entityToDto(repository.save(dtoToEntity(t)));
        return new MessengerVO();
    }

    @Override
    public MessengerVO deleteById(Long id) {
        repository.deleteById(id);
        return MessengerVO.builder()
                .message(repository.existsById(id) ? "FAILURE" : "SUCCESS")
                .build();
    }


    @Override
    public List<ArticleDTO> findAll(){
        return repository.findAll().stream().map(i->entityToDto(i)).toList();
        
    }

    @Override
    public long count() {
        return repository.count();
    }


    @Override
    public Boolean existById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public MessengerVO modify(ArticleDTO t) {
        repository.save(dtoToEntity(t));
        return MessengerVO.builder().message("True").build();

    }

    @Override
    public Optional<ArticleDTO> findById(Long id) {
        return repository.findById(id).map(this::entityToDto);
    }

    @Override
    public List<ArticleDTO> myList(Long id) {
        return repository.getArticlesByBoardId(id).stream().map(i->entityToDto(i)).toList();
        
    }



   
    
    
}
