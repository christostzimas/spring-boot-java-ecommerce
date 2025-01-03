package com.ct_ecommerce.eshop.Product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used to generate new objects inside the db
 */
@Configuration
public class ProductConfig {
    @Bean
    CommandLineRunner CommandLineRunner(ProductRepository repository){
        return args -> {
            //repository.deleteAll();
            /** Product test1 = new Product(
                    "test 1",
                    "test test test",
                    //10.5,
                    0.0,
                    15,
                    25,
                    "brand 1",
                    "category 1"
            );
            Product test2 = new Product(
                    "test 2",
                    "test test test",
                    0.0,
                    105,
                    60,
                    "brand 2",
                    "category 2"
            );

            test1.setSales(10);
            test2.setSales(1);

            repository.saveAll(List.of(test1, test2)); */
        };
    }
}
