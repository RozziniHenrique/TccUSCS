package uscs.STEFER.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("STEFER API")
                        .description("API REST do sistema STEFER, contendo as funcionalidades de agendamento de consultas, gerenciamento de funcionários e especialidades.")
                        .contact(new Contact()
                                .name("Henrique Rossini")
                                .email("hrozzini@gmail.com"))
                        .version("1.0"));
    }
}
