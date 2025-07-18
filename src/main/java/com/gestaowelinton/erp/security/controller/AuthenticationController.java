package com.gestaowelinton.erp.security.controller;

import com.gestaowelinton.erp.security.dto.AuthRequestDto;
import com.gestaowelinton.erp.security.dto.AuthResponseDto;
import com.gestaowelinton.erp.security.dto.RegisterRequestDto;
import com.gestaowelinton.erp.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        // O try-catch pode ser adicionado aqui para tratar erros (ex: email já existe)
        return ResponseEntity.ok(authenticationService.register(request));
    }
}