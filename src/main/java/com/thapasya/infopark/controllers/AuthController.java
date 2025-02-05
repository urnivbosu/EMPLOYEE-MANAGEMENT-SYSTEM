package com.thapasya.infopark.controllers;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.thapasya.infopark.dto.LoginRequest;
import com.thapasya.infopark.dto.RegistrationRequest;
import com.thapasya.infopark.models.User;
import com.thapasya.infopark.repository.EmployeeRepository;
import com.thapasya.infopark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Value("${security.jwt.secret-key}")
    public String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    public String jwtIssuer;
    /**
     * OAuth2-based login endpoint (authentication is handled by Spring Security)
     */
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Since OAuth2 handles token generation, return a success message
//        return ResponseEntity.ok("User authenticated successfully via OAuth2");
//    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        var response = new HashMap<String,Object>();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String jwtToken = createJwtToken(user);
        response.put("token", jwtToken);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }



//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//        // Authenticate the user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Extract the OAuth2 authentication token and retrieve the JWT token
//        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//        String jwtToken = oauthToken.getCredentials().toString();  // Get JWT token
//
//        // Return the JWT token in the response
//        return ResponseEntity.ok(new AuthResponse(jwtToken)); // You can customize this response DTO
//    }
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Manually extract JWT token from the authentication object
//        String jwtToken = ((OAuth2AuthenticationToken) authentication)
//                .getAuthorizedClientRegistrationId();  // Or extract access token as needed
//
//        return ResponseEntity.ok(new AuthResponse(jwtToken));
//    }




    /**
     * User registration endpoint
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        // Check if user already exists
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists with this email");
        }
        if (!employeeRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("No employee exists with this email");
        }


        // Create new user entity and encode the password
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(registrationRequest.getRole());

        // Save the new user
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    private String createJwtToken(Optional<User> user){
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24*3600))
                .subject(user.get().getEmail())
                .claim("role",user.get().getRole().toUpperCase())
                .build();
        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),claims);
        return encoder.encode(params).getTokenValue();


    }
}
