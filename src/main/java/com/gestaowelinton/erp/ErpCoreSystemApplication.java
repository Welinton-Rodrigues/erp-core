package com.gestaowelinton.erp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gestaowelinton.erp.model.Empresa;
import com.gestaowelinton.erp.repository.EmpresaRepository;
import com.gestaowelinton.erp.security.dto.RegisterRequestDto;
import com.gestaowelinton.erp.security.repository.UsuarioRepository;
import com.gestaowelinton.erp.security.service.AuthenticationService;

@SpringBootApplication

public class ErpCoreSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpCoreSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService authService,
            EmpresaRepository empresaRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder // Adicionamos para o log
    ) {
        return args -> {
            if (empresaRepository.count() == 0) {
                System.out.println("Nenhuma empresa encontrada. Criando empresa padrão...");
                Empresa empresaPadrao = new Empresa();
                empresaPadrao.setRazaoSocial("Empresa Padrão SA");
                empresaPadrao.setNomeFantasia("Meu ERP");
                empresaPadrao.setCnpj("00.000.000/0001-00");
                empresaPadrao.setStatus("ATIVA");
                empresaRepository.save(empresaPadrao);
                System.out.println("Empresa Padrão criada com ID 1.");
            }

            String adminEmail = "admin@erp.com";
            if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {
                System.out.println("Criando usuário ADMIN padrão...");
                var admin = new RegisterRequestDto(
                        "Admin ERP",
                        adminEmail,
                        "admin123",
                        "ROLE_ADMIN",
                        1L // Agora temos certeza que a empresa de ID 1 existe
                );
                authService.register(admin);
                System.out.println("Usuário ADMIN criado com sucesso.");
            }
        };
    }

}
