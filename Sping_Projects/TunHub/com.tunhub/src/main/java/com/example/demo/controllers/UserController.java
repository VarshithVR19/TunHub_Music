

package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entites.Users;
import com.example.demo.services.SongService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController{
	
	@Autowired
	UsersService service;
	

	@Autowired
	SongService songService;
	
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user)
	{
		boolean userStatus = service.emailExists(user.getEmail());
		if(userStatus==false)
		{			
			service.addUser(user);
			System.out.println("User Added");
		}else {
			System.out.println("Users already exits");
		}
		return "Home";			
	}
	
	
	
	@PostMapping("/validate")
	public String validate(@RequestParam("email")String email,
			@RequestParam("password") String password,
			HttpSession session)
	{
		if(service.validateUser(email,password)==true)
		{
			String role = service.getRole(email);
			
			session.setAttribute("email", email);
			
			if(role.equals("admin"))
			{
				return "adminHome";
			}else{
				return "customerHome";
				
//				Users user = service.getUser(email);
//				boolean userStatus = user.isPremium();
//				List<Song> songsList = songService.fetchAllSongs();
//				model.addAttribute("songs", songsList);
//				
//				
//				model.addAttribute("isPremium", userStatus);
//				
//				return "customerHome";
				
				
			}
		}
		else{
			return "login";
		}
	}
	
	
//	@GetMapping("/pay")
//	public String pay(@RequestParam String email) {
//		boolean paymentStatus=false;//payment api
//		
//		if(paymentStatus == true) {			
//			Users user = service.getUser(email);
//			user.setPremium(true);
//			service.updateUser(user);
//		}
//		
//		return "login";	
//	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
		
	}
	
}

