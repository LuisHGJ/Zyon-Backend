package br.com.zyon.backend.config;

import com.stripe.Stripe;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @PostConstruct
    public void init() {
        try {
            // Carrega variáveis do arquivo .env
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            String stripeKey = dotenv.get("STRIPE_SECRET_KEY");

            if (stripeKey == null || stripeKey.isEmpty()) {
                throw new IllegalStateException("STRIPE_SECRET_KEY não encontrada no arquivo .env!");
            }

            // Define a chave da API Stripe
            Stripe.apiKey = stripeKey;
            System.out.println("✅ Stripe configurado com sucesso.");
        } catch (Exception e) {
            System.err.println("❌ Erro ao configurar Stripe: " + e.getMessage());
        }
    }
}
