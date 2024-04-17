package com.von.api.article;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;



import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.von.api.article.model.ArticleDTO;
import com.von.api.article.service.ArticleService;
import com.von.api.article.service.ArticleServiceImpl;
import com.von.api.common.component.MessengerVO;
import com.von.api.common.component.PageRequestVO;

@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/articles")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService service;

    @PostMapping(path = "")
    public ResponseEntity<MessengerVO> save(PageRequestVO vo) throws SQLException {
        service.save(null);
        return ResponseEntity.ok(new MessengerVO());
    }
    @DeleteMapping(path = "/delete")
    public ResponseEntity<MessengerVO> deleteById(@RequestParam Long id) {
        log.info("입력받은 정보 : {}", id );
        return ResponseEntity.ok(service.deleteById(id));
    }
    
    @GetMapping(path = "/list")
    public ResponseEntity<List<ArticleDTO>> findAll(PageRequestVO vo) throws SQLException {
        log.info("입력받은 정보 : {}");
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping(path = "/detail")
    public ResponseEntity<Optional<ArticleDTO>> findById(@RequestParam Long id){
        log.info("입력받은 정보 : {}");
        return ResponseEntity.ok(service.findById(id));
    }


    @GetMapping(path = "/count")
    public ResponseEntity<Long> count(){
        return ResponseEntity.ok(service.count());
    }
    @GetMapping(path = "/exists/{id}")
    public ResponseEntity<MessengerVO> existById(@PathVariable long id){
        service.existById(0L);
        return ResponseEntity.ok(new MessengerVO());
    }
}