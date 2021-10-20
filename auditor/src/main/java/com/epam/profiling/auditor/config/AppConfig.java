package com.epam.profiling.auditor.config;

import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JaegerTracer getTracer() {
        return new io.jaegertracing.Configuration("auditor")
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
