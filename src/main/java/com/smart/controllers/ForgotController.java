package com.smart.controllers;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotController {
	@GetMapping("/forgot")
	String forgotForm(Model model) {
		
		model.addAttribute("title", "Forgot Password");
		return "forgot_email_form";
	}
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email) {
		System.out.println("email: " + email);
		
		// phát ra ngẫu nhiên mã OTP 4 số
		Random random = new Random(1000);
		int otp = random.nextInt(9999);
		System.err.println("otp:" + otp );
		return "verify_otp";
	}
}
