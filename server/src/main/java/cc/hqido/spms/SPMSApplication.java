package cc.hqido.spms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties
public class SPMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(SPMSApplication.class, args);
    }

}
