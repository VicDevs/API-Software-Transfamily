package br.com.family.manutencao_preventiva.controller;

import br.com.family.manutencao_preventiva.domain.model.User;
import br.com.family.manutencao_preventiva.dto.request.AuthenticationDTO;
import br.com.family.manutencao_preventiva.dto.response.LoginResponseDTO;
import br.com.family.manutencao_preventiva.repository.UserRepository;
import br.com.family.manutencao_preventiva.service.TokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> me(Authentication authentication) {

        var usuario = userRepository.findByCpf(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return ResponseEntity.ok(new LoginResponseDTO(
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();

        var token = tokenService.gerarToken(user);

        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(false)    // false para HTTP (IP da rede), true para HTTPS
                .path("/")
                .maxAge(7200)
                .sameSite("Lax")  // Crucial para o navegador aceitar o cookie em domínios/portas diferentes
                .build();

        // 4. Retorna os dados do motorista SEM o token no corpo
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new LoginResponseDTO(
                        user.getNome(),
                        user.getCpf(),
                        user.getRole()
                ));
    }
}