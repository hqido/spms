package cc.hqido.spms.adapter.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultPageConfig {

    @Bean
    public DefaultPageHandlerMapping defaultPageHandlerMapping() {
        return new DefaultPageHandlerMapping();
    }

}
