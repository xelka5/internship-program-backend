package com.tusofia.internshipprogram;

import com.tusofia.internshipprogram.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class InternshipProgramApplication implements CommandLineRunner {

	@Autowired
	public ApplicationConfig applicationConfig;

	public static void main(String[] args) {
		SpringApplication.run(InternshipProgramApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			Files.createDirectories(Paths.get(applicationConfig.getProfileImagesDirectory()));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}
}
