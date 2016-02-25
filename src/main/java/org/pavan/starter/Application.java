package org.pavan.starter;

import org.pavan.starter.dao.Product;
import org.pavan.starter.dao.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  CommandLineRunner init(final ProductRepository productRepository) {
    return arg0 -> {
      logger.info("Initializing database.");
      productRepository.deleteAll();
      productRepository.save(new Product("13860428", 15.0, "USD"));
      productRepository.save(new Product("15117729", 400.0, "USD"));
      productRepository.save(new Product("16483589", 600.0, "USD"));
      productRepository.save(new Product("16696652", 250.0, "USD"));
      productRepository.save(new Product("16752456", 30.0, "USD"));
      logger.info("Initializing database complete.");
    };

  }
}
