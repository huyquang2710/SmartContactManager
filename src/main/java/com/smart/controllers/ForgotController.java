package com.smart.controllers;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.service.EmailService;

@Controller
public class ForgotController {

	@Autowired
	private EmailService emailServer;

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
		String message = " OTP = " + otp ;
		String to = email;
		boolean flag = this.emailServer.sendEmail(subject, message, to);

		if (flag) {
			session.setAttribute("otp", "OTP");
			return "verify_otp";
			
		} else {
			session.setAttribute("message", "Check your email id !");
			return "forgot_email_form";
		}		
	}
}
