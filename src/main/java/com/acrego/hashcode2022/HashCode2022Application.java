package com.acrego.hashcode2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HashCode2022Application {

    public static void main(String[] args) {
        SpringApplication.run(HashCode2022Application.class, args);
        evaluate("a_an_example.in.txt", "a_an_example.out.txt");
    }

    public static void evaluate(String fileNameIn, String fileNameOut) {
        Problema pizzeria = new Problema();
        pizzeria.load(fileNameIn);
        pizzeria.solucionar();
        pizzeria.writeResult(fileNameOut);
    }

}
