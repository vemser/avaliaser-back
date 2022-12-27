package br.com.dbc.vemser.avaliaser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EnableFeignClients
@SpringBootApplication
public class AvaliaserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvaliaserApplication.class, args);
    }

}
