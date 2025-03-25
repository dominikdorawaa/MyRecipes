package pl.edu.pjatk.MPR_Projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"pl.edu.pjatk.MPR_Projekt"})
public class MprProjektApi {
	public static void main(String[] args) {
		SpringApplication.run(MprProjektApi.class, args);
	}
}
