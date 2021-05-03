package com.example.demo.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.dao.PatientRepository;
import com.example.demo.entities.Patient;

@Controller //Controlleur où on peut mettre des méthodes
public class PatientController {
	@Autowired //injection des dépendances
	private PatientRepository patientRepository;
	@GetMapping(path= "/index")
	public String index() {
		return "index";	
}
	
	@GetMapping(path= "/patientsList")
	public String list(Model model, 
			@RequestParam(name="page", defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue= "5")int size,
			@RequestParam(name="key",defaultValue= "")String mc) {
		//Page<Patient> pagePatients=patientRepository.findAll(PageRequest.of(page, size)); //cette methode findAll() par defaut
		Page<Patient> pagePatients=patientRepository.findByNomContains(mc, PageRequest.of(page, size)); //cette methode findByNom..() définit dans l'interface PatientRepository
		model.addAttribute("patients",pagePatients); //stocker les patients dans le modèle
		model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
		model.addAttribute("key", mc);
		model.addAttribute("size", size);
		model.addAttribute("page", page);
		return "patientsList";
	}
	
	@GetMapping(path= "/deletePatient")
	public String delete(Long id, String key, int page, int size) {
		patientRepository.deleteById(id); //methode deleteById() déja préparé par spring 
		return "redirect:/patientsList?page="+page+"&size="+size+"&key="+key;	
}
	
	@GetMapping(path= "/formPatient")
	public String formPatient(Model model){
		model.addAttribute("patient", new Patient());
		model.addAttribute("mode","new");
		return "formPatient";
	}
	
	@PostMapping(path= "/savePatient")
	public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult){//BindingResult est une collection qui contient la liste des erreurs qui ont étaient générés au mement de la validation
		if(bindingResult.hasErrors()) {return "formPatient";}
		patientRepository.save(patient);
		model.addAttribute("patient", patient);
		return "saveConfirmation";
	}
	
	@GetMapping(path= "/editPatient")
	public String editPatient(Model model, Long id){
		Patient pa=patientRepository.findById(id).get();
		model.addAttribute("patient", pa);
		model.addAttribute("mode","edit");
		return "formPatient";
	}
	
	/*
	 * @PostMapping(path= "/updatePatient") public String updatePatient(Model
	 * model,@Valid Patient patient, BindingResult bindingResult){//BindingResult
	 * est une collection qui contient la liste des erreurs qui ont étaient générés
	 * au mement de la validation if(bindingResult.hasErrors()) {return
	 * "formEditPatient";} patientRepository.save(patient); return
	 * "formEditPatient"; }
	 */
}
