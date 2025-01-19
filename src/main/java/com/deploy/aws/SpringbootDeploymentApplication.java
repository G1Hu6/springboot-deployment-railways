package com.deploy.aws;

import com.deploy.aws.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootDeploymentApplication implements CommandLineRunner {

	@Autowired
	DataService dataService;

	@Value("${app.environment}")
	private String environment;

	public static void main(String[] args) {

		SpringApplication.run(SpringbootDeploymentApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(dataService.getData());
		System.out.println("App Profile = " + environment);
	}

}
