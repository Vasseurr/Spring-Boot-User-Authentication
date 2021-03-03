package com.vasseurr.registrationLogin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vasseurr.registrationLogin.model.User;
import com.vasseurr.registrationLogin.security.CustomUserDetails;
import com.vasseurr.registrationLogin.service.UserService;

@Controller
public class UserController {

	//Injection
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String viewFirstPage() {
		return "index";
	}
	
	@GetMapping("/home")
	public String viewHomePage() {
		return "home";
	}
	
	@GetMapping("/login") 
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
			return "login";
		
		return "redirect:/";
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		user.setDecrypted(user.getPassword());	//this is decrypted password
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userService.save(user);
		return "register_success";
	}
	
	@GetMapping("/list_users")
	public String viewUserList(Model model) {
		List<User> listUsers = userService.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(Principal principal, Model model) {
		String email = principal.getName();
		User user = (User) userService.findByEmail(email);
		model.addAttribute("user", user);
		return "/profile";
	}
	
	@GetMapping("/about")
	public String viewAbout(Model model) {
		return "about";
	}
	
	@GetMapping("/contact")
	public String viewContact(Model model) {
		return "contact";
	}
}
