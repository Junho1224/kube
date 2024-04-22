package com.von.api.common.component.pagination;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Component
@Getter
@AllArgsConstructor
@Builder
public class PageRequestVO {
    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestVO() {
        this.page =1;
        this.size =10;

        
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size,sort);
    }


}
