package com.smart.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.repository.ContactRepository;
import com.smart.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ContactRepository contactRepo;
	//method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println("USERNAME: " + username );
		
		User user = userRepo.getUserByUserName(username);
		
		System.out.println("Name: " + user);
		model.addAttribute("user", user);
		
	}
	//dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "Home");
		return "normal/user_dashboard";
	}
	
	//open add form handler
	@GetMapping("/add-contact")
	public String openAddContectForm(Model model) {
		
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		
		return "normal/add_contact_form";
	}
	//process add contact form
	@PostMapping("/process-contact")
	public String processContact(
			@ModelAttribute Contact contact,
			@RequestParam("profileImage") MultipartFile file,
			Principal principal,
			HttpSession session) {
		try {
		String name = principal.getName();
		User user = this.userRepo.getUserByUserName(name);
		contact.setUser(user);
		user.getContacts().add(contact);
		
		//upload image
		if(file.isEmpty()) {
			System.out.println("File is empty");
			//set default img
			contact.setImage("contact.png");
		} else {
			//file to folder and update name
			contact.setImage(file.getOriginalFilename());
			File saveFile = new ClassPathResource("static/img").getFile();
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Upload success");
		}
		
		this.userRepo.save(user);
		
		
	
		System.out.println("contact:" + contact);
		System.out.println("Added to data"); 
		//message success
		session.setAttribute("message", new Message("your contact is added!! , Add more!!!", "success"));	
		
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			//message error
			session.setAttribute("message", new Message("Some went wrong!! , try again!!!", "danger"));	
		}
 		
		return "normal/add_contact_form";
	}
	
	//show all contact
	@GetMapping("/show-contacts/{page}")
	public String showContacts(
			@PathVariable("page") Integer page,
			Model model, 
			Principal principal) {
		model.addAttribute("title", "Show User Contact");
		
		
		String userName =  principal.getName();
		User user = this.userRepo.getUserByUserName(userName);
		
		//page
		Pageable pageable = PageRequest.of(page, 5);

		Page<Contact> contacts = this.contactRepo.findContactByUser(user.getId(), pageable);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());
		return "normal/show_contact";
		}
	//show partilar contact details
	@GetMapping("/{id}/contact")
	public String showContactDetail(@PathVariable("id") Integer id, Model model, Principal principal) {
		System.out.println("ID: " + id);
		
		Optional<Contact> contactOptinal = this.contactRepo.findById(id);
		Contact contact = contactOptinal.get();
		
		model.addAttribute("title", "Contact Detail");
		
		// xu ly loi khac Id nhung van truy cap dc
		String userName = principal.getName();
		User user = this.userRepo.getUserByUserName(userName);
		if(user.getId() == contact.getUser().getId()) {
			model.addAttribute("contact", contact);
		}	
		return "normal/contact_detal";
	}
	@GetMapping("/delete/{id}")
	public String deleteContact(@PathVariable("id") Integer id, Model model, HttpSession session, Principal principal) {
		Optional<Contact> contactOptinal = this.contactRepo.findById(id);
		Contact contact = contactOptinal.get();
		
		//check assigimetn
		User user = this.userRepo.getUserByUserName(principal.getName());
		user.getContacts().remove(contact);
		
		session.setAttribute("message", new Message("Contact delete successfully!!!", "success"));
		return "redirect:/user/show-contacts/0";
	}
	
	//open update handler
	@PostMapping("/update-contact/{id}")
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		
		Contact contact = this.contactRepo.findById(id).get();
		model.addAttribute("contact", contact);
		
		
		model.addAttribute("title", "Update Contact");
		return "/normal/update_form";
	}
	//process update
	@RequestMapping(value="/process-update", method = RequestMethod.POST)
	public String updateContact(
			@ModelAttribute("contact") Contact contact,
			@RequestParam("profileImage") MultipartFile file,
			Model model,
			HttpSession session,
			Principal principal) {
		
		try {
			//old contact
			Contact oldContact = this.contactRepo.findById(contact.getId()).get();
			if(!file.isEmpty()) {
				//file word	
				
				//delete image
				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, oldContact.getImage());
				file1.delete();
				//add new image
				contact.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());
			} else {
				//get old image
				contact.setImage(oldContact.getImage());
			}
			
			User user = this.userRepo.getUserByUserName(principal.getName());
			contact.setUser(user);
			this.contactRepo.save(contact);
			
			session.setAttribute("message", new Message("Update Success!!!", "success"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/user/" + contact.getId() + "/contact";
	}
}
	