package com.von.api.common.component.pagination;

import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.von.api.article.model.ArticleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Component
@Getter
@Builder
@Data
@AllArgsConstructor

public class PageRequestFileVO {

    private int page;
    private int size;
    private String type;
    private String keyword;
    private List<ArticleDTO> pageFileDto;

    public PageRequestFileVO(){
        this.page=1;
        this.size = 10;
    }
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size,sort);
    }
    

}
