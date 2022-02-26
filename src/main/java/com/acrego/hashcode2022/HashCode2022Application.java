package com.acrego.hashcode2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HashCode2022Application {

    public static void main(String[] args) {
        SpringApplication.run(HashCode2022Application.class, args);
        //evaluate("a_an_example.in.txt", "a_an_example.out.txt");
        //evaluate("b_better_start_small.in.txt", "b_better_start_small.out.txt");
        //evaluate("c_collaboration.in.txt", "c_collaboration.out.txt");
        //evaluate("d_dense_schedule.in.txt", "d_dense_schedule.out.txt");
        //evaluate("e_exceptional_skills.in.txt", "e_exceptional_skills.out.txt");
        //evaluate("f_find_great_mentors.in.txt", "f_find_great_mentors.out.txt");
    }

    public static void evaluate(String fileNameIn, String fileNameOut) {
        Problema pizzeria = new Problema();
        pizzeria.load(fileNameIn);
        pizzeria.solucionar();
        pizzeria.writeResult(fileNameOut);
    }

}
