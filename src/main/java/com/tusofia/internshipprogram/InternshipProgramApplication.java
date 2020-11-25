package com.tusofia.internshipprogram;

import com.tusofia.internshipprogram.config.ApplicationConfig;
import com.tusofia.internshipprogram.exception.DirectoryNotInitializedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.tusofia.internshipprogram.util.GlobalConstants.COULD_NOT_INITIALIZE_DIRECTORIES_MESSAGE;

@SpringBootApplication
@Slf4j
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
			Files.createDirectories(Paths.get(applicationConfig.getFinalReportsDirectory()));
		} catch (IOException e) {
			LOGGER.error(COULD_NOT_INITIALIZE_DIRECTORIES_MESSAGE, e);
			throw new DirectoryNotInitializedException(COULD_NOT_INITIALIZE_DIRECTORIES_MESSAGE, e);
		}
	}
}
