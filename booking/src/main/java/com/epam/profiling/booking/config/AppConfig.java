package com.epam.profiling.booking.config;

import io.jaegertracing.internal.JaegerTracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static io.jaegertracing.Configuration.ReporterConfiguration;
import static io.jaegertracing.Configuration.SamplerConfiguration;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JaegerTracer getTracer() {
        return new io.jaegertracing.Configuration("booking")
                .withSampler(SamplerConfiguration
                        .fromEnv()
                        .withType("const")
                        .withParam(1))
                .withReporter(ReporterConfiguration
                        .fromEnv()
                        .withLogSpans(true))
                .getTracer();
    }
}
