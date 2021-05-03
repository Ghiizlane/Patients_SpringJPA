package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.dao.PatientRepository;
import com.example.demo.entities.Patient;

@SpringBootApplication
public class Demo4Application implements CommandLineRunner{
	@Autowired //Injection des dépendances
	PatientRepository patientRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Demo4Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	//insérer des données à notre base de données:
	/*
	 * patientRepository.save(new Patient(null,"Ali", new Date(), 2, false));
	 * patientRepository.save(new Patient(null,"Salim", new Date(), 3, false));
	 * patientRepository.save(new Patient(null,"Sara", new Date(), 2, false));
	 * patientRepository.save(new Patient(null,"Samia", new Date(), 6, false));
	 */
		
		patientRepository.findAll().forEach(p->{
			System.out.println(p.getNom());
		});
	}

}
