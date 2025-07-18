package com.gestaowelinton.erp.security.model;

import com.gestaowelinton.erp.model.Empresa; // Lembre de importar a classe Empresa
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email; // Usaremos o email como username

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(nullable = false, length = 50)
    private String cargo; // Ex: "ROLE_ADMIN", "ROLE_VENDEDOR"

    // --- Métodos da interface UserDetails que o Spring Security precisa ---
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna as permissões/cargos do usuário
        return List.of(new SimpleGrantedAuthority(this.cargo));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        // Usaremos o email como nome de usuário para o login
        return this.email;
    }

    // Para simplificar, vamos deixar os métodos abaixo como 'true'
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}