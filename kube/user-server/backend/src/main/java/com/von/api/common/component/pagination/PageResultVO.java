package com.von.api.common.component.pagination;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResultVO<DTO,EN> {

    private List<DTO> dtoList;
    private int totalPage, page, size, start, end;
    private boolean prev,next;
    private List<Integer> pageList;

    public PageResultVO(Page<EN>result, Function<EN,DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makepageList(result.getPageable());
        
        
    }

    private void makepageList(Pageable pageable) {
        this.page = pageable.getPageNumber()+1;
        this.size = pageable.getPageSize();
        int tempEnd = (int) ( Math.ceil(page/10))*10;
        start = tempEnd-9;
        prev = start > 1;
        end=totalPage > tempEnd ? tempEnd :totalPage;
        next = totalPage > tempEnd;
        pageList= IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }
    
}
