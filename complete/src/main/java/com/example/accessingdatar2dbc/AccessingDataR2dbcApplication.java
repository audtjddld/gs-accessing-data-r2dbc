package com.example.accessingdatar2dbc;

import io.r2dbc.spi.ConnectionFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;

@SpringBootApplication
public class AccessingDataR2dbcApplication {

  private static final Logger log = LoggerFactory.getLogger(AccessingDataR2dbcApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(AccessingDataR2dbcApplication.class, args);
  }

  @Bean
  ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

    return initializer;
  }

  @Bean
  public CommandLineRunner demo(CustomerRepository repository) {

    return (args) -> {

      // save a few customers
      List<Account> accounts = new ArrayList<>();
      accounts.add(new Account("신한은행"));


      repository.saveAll(Arrays.asList(new Customer("Jack", "Bauer", accounts),
          new Customer("Chloe", "O'Brian", accounts),
          new Customer("Kim", "Bauer", accounts),
          new Customer("David", "Palmer", accounts),
          new Customer("Michelle", "Dessler", accounts)))
          .blockLast(Duration.ofSeconds(10));

      // fetch all customers
      log.info("Customers found with findAll():");
      log.info("-------------------------------");
      repository.findAll().doOnNext(customer -> {
        log.info(customer.toString());
      }).blockLast(Duration.ofSeconds(10));

      log.info("");

      // fetch an individual customer by ID
      repository.findById(1L).doOnNext(customer -> {
        log.info("Customer found with findById(1L):");
        log.info("--------------------------------");
        log.info(customer.toString());
        log.info("");
      }).block(Duration.ofSeconds(10));

      repository.findById(1L).flatMap(customer -> {
        customer.setFirstName("홍");
        return repository.save(customer);
      }).flatMap(customer -> {
        return repository.findById(customer.getId());
      }).doOnNext(customer -> {
        log.info("-----------------> update customer? ");
        log.info(customer.toString());
      }).block();


      // fetch customers by last name
      log.info("Customer found with findByLastName('Bauer'):");
      log.info("--------------------------------------------");
      repository.findByLastName("Bauer").doOnNext(bauer -> {
        log.info(bauer.toString());
      }).blockLast(Duration.ofSeconds(10));
      log.info("");
    };
  }

}
