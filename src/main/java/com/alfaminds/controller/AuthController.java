package com.alfaminds.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.alfaminds.Jwt.Jwtutils;
import com.alfaminds.Repository.UserRepository;
import com.alfaminds.models.UserModel;
import com.alfaminds.payload.requests.SignupRequest;
import com.alfaminds.payload.response.MessageResponse;
import com.alfaminds.payload.response.AuthenticationResponse;
import com.alfaminds.payload.requests.LoginRequest;
import com.alfaminds.services.CustomServiceDetails;
import com.alfaminds.services.UserServices;

import net.bytebuddy.utility.RandomString;

@RestController
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserServices userService;
	

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Jwtutils jwtutils;

	@Autowired
	PasswordEncoder encoder;
	
	
	@Autowired
	private HttpServletRequest request;
	

	UserModel us;
	

	@GetMapping("/dashboard")
	private String testingToken() {
		return "welcome to dashboard " + SecurityContextHolder.getContext().getAuthentication().getName();
	}

	// register
	@PostMapping("/signup")
	private ResponseEntity<?> register(@RequestBody SignupRequest signupRequest ) throws UnsupportedEncodingException, MessagingException {

		// checking duplicate username & email.
		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		//HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest;
		

		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// create new user's account.
		   us = new UserModel(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getCompany(), signupRequest.getFullname(),
				signupRequest.getMobile());
		
		
		//generate verification code
		String randomCode = RandomString.make(64);
		us.setVerificationCode(randomCode);
		us.setEnabled(false);		
		
		// save user to database.
		userRepository.save(us);
		
		
		String currentSiteUrl = getSiteURL(request);
		this.sendVerificationEmail(us, currentSiteUrl);
		
		
		return ResponseEntity
				.ok(new MessageResponse("User Signup Successful for " + signupRequest.getFullname() + " ."));
	}
	
	public void sendVerificationEmail(UserModel us, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = us.getEmail();
	    String fromAddress = "sheikhnaveedshafi@gmail.com";
	    String senderName = "PowerMail";
	    String subject = "Please verify your registration";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "Your company name.";
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", us.getFullname());
	    String verifyURL = siteURL + "/verify?code=" + us.getVerificationCode();
	     
	    content = content.replace("[[URL]]", verifyURL);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	     
	}
	
	
	//user account verification
	public boolean verify(String verificationCode) {
	     us = userRepository.findByVerificationCode(verificationCode);
	     
	    if (us == null || us.isEnabled()) {
	        return false;
	    } else {
	        us.setVerificationCode(null);
	        us.setEnabled(true);
	        userRepository.save(us);
	         
	        return true;
	    }
	     
	}
	
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
	    if (verify(code)) {
	        return "verify_success";
	    } else {
	        return "verify_fail";
	    }
	}
	
	// login
	@PostMapping("/signin")
	private ResponseEntity<?> login(@RequestBody CustomServiceDetails loginRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Incorrect Username or Password!"));
		}

		UserDetails loadeduser = userService.loadUserByUsername(loginRequest.getEmail());

		String generatedToken = jwtutils.generateToken(loadeduser);

		AuthenticationResponse respObj = new AuthenticationResponse();
		respObj.setResponseMessage("User Login Successful.");
		respObj.setToken(generatedToken);
		respObj.setUsername(loginRequest.getEmail());
		return ResponseEntity.ok(respObj);

	}
	
	public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

}
