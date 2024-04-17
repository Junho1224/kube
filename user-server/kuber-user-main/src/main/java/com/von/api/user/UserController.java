package com.von.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.von.api.common.component.MessengerVO;
import com.von.api.user.model.User;
import com.von.api.user.model.UserDTO;
import com.von.api.user.repository.UserRepository;
import com.von.api.user.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.*;



@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServiceImpl service;



    @PostMapping(path = "/join")
    public ResponseEntity<MessengerVO> save(@RequestBody UserDTO dto) {
        log.info("join-입력받은 정보 : {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }


    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> findAll(){
        log.info("입력받은 정보 : {}");
        return ResponseEntity.ok(service.findAll());
        
    }

    @GetMapping(path = "/detail")
    public ResponseEntity<Optional<UserDTO>> findById(@RequestParam Long id) {
        log.info("입력받은 정보 : {}", id );
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/modify")
    public ResponseEntity<MessengerVO> modify(@RequestBody UserDTO param) {
        log.info("입력받은 정보 : {}", param );
        return ResponseEntity.ok(service.modify(param));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MessengerVO> deleteById(@RequestParam Long id) {
        log.info("입력받은 정보 : {}", id );
        return ResponseEntity.ok(service.deleteById(id));
    }
    

    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        return ResponseEntity.ok(service.count());
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserDTO>> findUsersByName(@RequestBody UserDTO param) {
        //log.info("입력받은 정보 : {}", name );
        return ResponseEntity.ok(service.findUsersByName(param.getName()));
    }
    
    @PostMapping(path = "/login")
    public ResponseEntity<MessengerVO> login(@RequestBody UserDTO param) {
       log.info("입력받은 정보 : {}", param );
        return ResponseEntity.ok(service.login(param));
    }

    


    
    
}

