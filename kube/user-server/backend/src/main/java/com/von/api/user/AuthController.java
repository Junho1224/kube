package com.von.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.von.api.common.component.MessengerVO;
import com.von.api.user.model.UserDTO;
import com.von.api.user.service.UserServiceImpl;

import java.util.*;



@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserServiceImpl service;

    @PostMapping("/login")
    public ResponseEntity<MessengerVO> login(@RequestBody UserDTO param) {
        log.info("::: login controller parameter ",param);
        MessengerVO messenger = service.login(param);
        return ResponseEntity.ok(messenger);
    }

    @GetMapping("/exists-username")
    public ResponseEntity<Boolean> existsUsername(@RequestParam("username") String username) {
        log.info("existsUsernam 파라미터 정보 : "+username);
        Boolean flag = service.existsUsername(username);
        log.info("existsUsernam 결과 : "+flag);
        return ResponseEntity.ok(flag);
    }

    


}

