package br.com.zyon.backend.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripeTestController {

    @GetMapping("/api/pagamento/teste")
    public String testarStripe() {
        try {
            // Aqui usamos diretamente a chave configurada no StripeConfig (já definida globalmente)
            Balance balance = Balance.retrieve(); // faz uma requisição simples à API do Stripe
            return "✅ Stripe conectado com sucesso! Saldo disponível: " + 
                   balance.getAvailable().get(0).getAmount() + " " + 
                   balance.getAvailable().get(0).getCurrency();
        } catch (StripeException e) {
            return "❌ Erro ao conectar ao Stripe: " + e.getMessage();
        } catch (Exception e) {
            return "❌ Erro inesperado: " + e.getMessage();
        }
    }
}
