package br.com.kabum.courrier.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SpringDocConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Courrier Simulation API")
                        .description("Simulation for consulting prices for different courriers due to package sizes."));
    }
}
