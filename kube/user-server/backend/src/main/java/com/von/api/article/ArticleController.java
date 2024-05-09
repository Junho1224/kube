package com.von.api.article;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.von.api.article.model.ArticleDTO;
import com.von.api.article.repository.ArticleRepository;
import com.von.api.article.service.ArticleServiceImpl;
import com.von.api.common.component.MessengerVO;


@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/articles")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleServiceImpl service;
    private final ArticleRepository repository;

    @PostMapping(path = "/save")
    public ResponseEntity<MessengerVO> save(@RequestBody ArticleDTO dto) {
        log.info("save-입력받은 정보 : {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }
    @PutMapping("/modify")
    public ResponseEntity<MessengerVO> modify(@RequestBody ArticleDTO dto) {
        log.info("입력받은 정보 : {}", dto );
        return ResponseEntity.ok(service.modify(dto));
    }
    @DeleteMapping(path = "/delete")
    public ResponseEntity<MessengerVO> deleteById(@RequestParam Long id) {
        log.info("입력받은 정보 : {}", id );
        return ResponseEntity.ok(service.deleteById(id));
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<ArticleDTO>> findAll(){
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

    @GetMapping("/myList")
    public ResponseEntity<List<ArticleDTO>> myList(@RequestParam("id") Long id){
        return ResponseEntity.ok(service.myList(id));
    }

    
    
}