package br.com.family.manutencao_preventiva.domain.model;

import br.com.family.manutencao_preventiva.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    @Setter
    private String cpf;

    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    @Setter
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_MOTORISTA")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_MOTORISTA"));
    }

    @Override
    public String getUsername() {
        return this.cpf;
    }
}
