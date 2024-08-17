//package com.example.gstbilling;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class GstApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(GstApplication.class, args);
//	}
//
//}

package com.example.gstbilling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories("com.example.gstbilling.repository")
@EntityScan("com.example.gstbilling.model")
public class GstApplication {
    public static void main(String[] args) {
        SpringApplication.run(GstApplication.class, args);
    }
}

