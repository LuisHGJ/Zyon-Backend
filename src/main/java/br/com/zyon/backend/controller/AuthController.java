package br.com.zyon.backend.controller;

import br.com.zyon.backend.entity.CustomUserDetails;
import br.com.zyon.backend.security.JwtUtil;
import br.com.zyon.backend.service.CustomUserDetailsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
// @CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String senha = loginData.get("senha");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, senha)
            );

            // Cast para CustomUserDetails
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

            // Verifica se o usuário pagou
            if (!user.getUser().isPaid()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Pagamento pendente. Complete o checkout para ativar sua conta."));
            }

            String token = jwtUtil.generateToken(user.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", user.getId());
            response.put("email", user.getUsername());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Adicione um log da exceção (use um logger real no código, como SLF4J/Logger)
            System.err.println("Falha na autenticação para o email: " + email);
            e.printStackTrace(); // Imprime o rastreamento completo da pilha

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email ou senha inválidos"));
        }
    }
}