package com.example.birthay;

import com.example.birthay.reader.BirthdayReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@SpringBootApplication
@Slf4j
public class BirthayApplication implements CommandLineRunner {
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BirthayApplication.class);
        application.setAddCommandLineProperties(false);
        application.run(args);
    }

    @Override
    public void run(String... args) {
        Path birthdayFile = Paths.get(Objects.requireNonNull(environment.getProperty("birthdays")));
        Path templateFile = Paths.get(Objects.requireNonNull(environment.getProperty("template")));
        BirthdayReader.readAll(birthdayFile, templateFile);
    }

}
