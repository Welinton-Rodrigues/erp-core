package com.gestaowelinton.erp.security.service;

import com.gestaowelinton.erp.model.Empresa;
import com.gestaowelinton.erp.security.dto.AuthRequestDto;
import com.gestaowelinton.erp.security.dto.AuthResponseDto;
import com.gestaowelinton.erp.security.dto.RegisterRequestDto;
import com.gestaowelinton.erp.security.model.Usuario;
import com.gestaowelinton.erp.security.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import com.gestaowelinton.erp.model.Empresa;
import com.gestaowelinton.erp.repository.EmpresaRepository;
import com.gestaowelinton.erp.security.dto.RegisterRequestDto;
import com.gestaowelinton.erp.security.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto login(AuthRequestDto request) {
        // 1. O AuthenticationManager usa o nosso 'AuthenticationProvider' para validar
      
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha()));

        // 2. Se a autenticação passar, buscamos o usuário no banco.
        var user = usuarioRepository.findByEmail(request.email())
                .orElseThrow(); // Sabemos que o usuário existe se a linha de cima não deu erro.

        // 3. Geramos o token JWT para o usuário encontrado.
        var jwtToken = jwtService.generateToken(user);

        // 4. Retornamos o token dentro do nosso DTO de resposta.
        return new AuthResponseDto(jwtToken);
    }

    public AuthResponseDto register(RegisterRequestDto request) {
        // 1. Busca a empresa à qual o usuário será associado
        Empresa empresa = empresaRepository.findById(request.idEmpresa())
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada"));

        // 2. Cria a nova entidade Usuario
        var user = new Usuario();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setEmpresa(empresa);
        user.setCargo(request.cargo());

        // 3. CRIPTOGRAFA A SENHA ANTES DE SALVAR!
        user.setSenha(passwordEncoder.encode(request.senha()));

        // 4. Salva o novo usuário no banco
        usuarioRepository.save(user);

        // 5. Gera um token JWT para o usuário recém-criado e já o retorna
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponseDto(jwtToken);
    }
}