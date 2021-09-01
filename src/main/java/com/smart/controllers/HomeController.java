package com.smart.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "This is title");
		return "home";
	}
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "This is about");
		return "about";
	}
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "This is Signup");
		model.addAttribute("user", new User());
		return "signup";
	}
	
//	handler for segistering user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(
			@Valid @ModelAttribute("user") User user,
			BindingResult bindingResult,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			Model model,
			HttpSession session ) {
	
		try {
			if(!agreement) {
				System.out.println("You have not agreed the terms and conditions!!!");
				throw new Exception("You have not agreed the terms and conditions!!!");
			}
			//validation
			if(bindingResult.hasErrors()) {
				System.out.println("ERROR :" + bindingResult.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(encoder.encode(user.getPassword()));
			
			System.out.println("Agreement: " + agreement);
			System.out.println("User: " + user);
			
			User result = userRepo.save(user);
			model.addAttribute("user", new User());
			
			//message
			session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			//message
			session.setAttribute("message", new Message("Something went wrong!!" +e.getMessage(), "alert-danger"));
			return  "signup";
		}
	
	}
	
}
