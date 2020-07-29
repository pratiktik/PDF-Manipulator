package com.codeplanet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeplanet.model.User;

@RestController
public class ApiController {
	@GetMapping("/apiget")
	
	public User api1(User u1) {
		System.out.println(u1.getEmailId());
		User u=new User();
		u.setEmailId("parthcode@co.in");
		u.setMobile("8942927943");
		return u;
	}
	
}
