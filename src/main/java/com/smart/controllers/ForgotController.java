package com.smart.controllers;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.repository.UserRepository;
import com.smart.service.EmailService;

@Controller
public class ForgotController {

	@Autowired
	private EmailService emailServer;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("/forgot")
	String forgotForm(Model model) {

		model.addAttribute("title", "Forgot Password");
		return "forgot_email_form";
	}
	
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email, HttpSession session) {
		System.out.println("email: " + email);

		// phát ra ngẫu nhiên mã OTP 4 số
		Random random = new Random(1000);
		int otp = random.nextInt(9999);
		System.err.println("otp:" + otp);

		// sent otp to mail
		String subject = "OTP From SCM";
		String message = ""
				+ "<div style='border:1px solid #e2e2e2; padding: 20px' >"
				+ "<h1>"
				+ "OTP is "
				+ "<b>" + otp
				+ "</n>"
				+ "</h1>"
				+ "</div>";
		
		String to = email;
		boolean flag = this.emailServer.sendEmail(subject, message, to);

		if (flag) {
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			return "verify_otp";
			
		} else {
			session.setAttribute("message", "Check your email id !");
			return "forgot_email_form";
		}		
	}
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") int otp, HttpSession session) {
		int myOtp = (int) session.getAttribute("myotp");
		String email = (String) session.getAttribute("email");	
		if(myOtp == otp) {
			//password change form
			
			User user = this.userRepo.getUserByUserName(email);
			if(user == null) {
				session.setAttribute("message", "User does not exist with this email!!");
			} else {
				
			}
			
			return "password_change_form";
		} else {
			session.setAttribute("message", "you have entered wrong otp!!");
			return "verify_otp";
		}
	}
	//change password
	@PostMapping("/change-password")
	public String changePasword(@RequestParam("newPassword") String newPassword, HttpSession session) {
		String email = (String) session.getAttribute("email");
		User user = this.userRepo.getUserByUserName(email);
		user.setPassword(encoder.encode(newPassword));
		this.userRepo.save(user);
		return "redirect:/signin?change=password changed successfully...";
	}
}
