package com.kparlar.catalogsearcher;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@EnableHystrixDashboard
@EnableCircuitBreaker
@SpringBootApplication
@EnableMetrics
@EnableAspectJAutoProxy
public class CatalogSearcherApplication extends MetricsConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(CatalogSearcherApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public HystrixCommandAspect hystrixAspect() {
		return new HystrixCommandAspect();
	}


	@Override
	public void configureReporters(MetricRegistry metricRegistry) {
		// registerReporter allows the MetricsConfigurerAdapter to
		// shut down the reporter when the Spring context is closed
		registerReporter(ConsoleReporter
				.forRegistry(metricRegistry)
				.build())
				.start(1, TimeUnit.MINUTES);
	}
}
