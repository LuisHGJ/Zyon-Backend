package br.com.zyon.backend.controller;

import org.springframework.web.bind.annotation.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.util.*;
import br.com.zyon.backend.entity.User;
import br.com.zyon.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/pagamento")
// @CrossOrigin(origins = "http://localhost:3000")

public class PagamentoController {

    private final UserService userService;

    
    public PagamentoController(UserService userService) {
        this.userService = userService;
    }

    @Value("${frontend.url}")
    private String frontendUrl;

    @PostMapping("/checkout")
    public Map<String, String> criarSessaoCheckout(@RequestParam Long userId) throws Exception {


        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            // Passa userId na success_url para ativar o usuário depois
            .setSuccessUrl(frontendUrl + "/sucesso?userId=" + userId)
            .setCancelUrl(frontendUrl + "/erro")            
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("brl")
                            .setUnitAmount(2500L) // R$25,00 em centavos
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName("Cadastro Pago")
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build();

        Session session = Session.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("url", session.getUrl());
        return response;
    }

    // Endpoint para marcar usuário como pago
    @PostMapping("/success")
    public ResponseEntity<?> marcarUsuarioComoPago(@RequestParam Long userId) {
        userService.markUserAsPaid(userId);
        return ResponseEntity.ok(Map.of("message", "Pagamento confirmado! Usuário ativado."));
    }
}
