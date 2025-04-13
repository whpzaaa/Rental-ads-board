package org.example.informationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class InformationSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(InformationSystemApplication.class, args);
    }

}
