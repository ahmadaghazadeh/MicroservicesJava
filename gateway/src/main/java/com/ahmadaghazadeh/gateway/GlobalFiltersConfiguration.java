package com.ahmadaghazadeh.gateway;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {
    final Logger logger=LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    @Order(1)
    @Bean
    @NotNull
    public GlobalFilter secondPreFilter() {
        return (new GlobalFilter() {
            public final Mono filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                logger.info("My second Global Pre-Filter is executed");
                return chain.filter(exchange).then(Mono.fromRunnable((Runnable)(new Runnable() {
                    public final void run() {
                        logger.info("My third Global Post-Filter is executed");
                    }
                })));
            }
        });
    }

    @Order(2)
    @Bean
    @NotNull
    public GlobalFilter thirdPreFilter() {
        return (new GlobalFilter() {
            public final Mono filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                logger.info("My third Global Pre-Filter is executed");
                return chain.filter(exchange).then(Mono.fromRunnable((Runnable)(new Runnable() {
                    public final void run() {
                        logger.info("My Second Global Post-Filter is executed");
                    }
                })));
            }
        });
    }

    @Order(3)
    @Bean
    @NotNull
    public GlobalFilter forthPreFilter() {
        return (new GlobalFilter() {
            public final Mono filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                logger.info("My forth Global Pre-Filter is executed");
                return chain.filter(exchange).then(Mono.fromRunnable((Runnable)(new Runnable() {
                    public final void run() {
                        logger.info("My First Global Post-Filter is executed");
                    }
                })));
            }
        });
    }


}
