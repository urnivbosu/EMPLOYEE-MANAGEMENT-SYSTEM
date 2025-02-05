package com.thapasya.infopark;

import com.thapasya.infopark.controllers.AuthController;
import com.thapasya.infopark.dto.LoginRequest;
import com.thapasya.infopark.dto.RegistrationRequest;
import com.thapasya.infopark.models.Employee;
import com.thapasya.infopark.models.User;
import com.thapasya.infopark.repository.EmployeeRepository;
import com.thapasya.infopark.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        authController = new AuthController();
        authController.authenticationManager = authenticationManager;
        authController.userRepository = userRepository;
        authController.employeeRepository = employeeRepository;
        authController.passwordEncoder = passwordEncoder;
        authController.jwtIssuer = "testIssuer";
        authController.jwtSecretKey = "testSecretKey";
    }

    /**
     * Test Case 1: Successful Login
     */
//    @Test
//    void testLoginSuccess() {
//        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
//        User mockUser = new User();
//        mockUser.setEmail("user@example.com");
//        mockUser.setRole("USER");
//
//        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));
//        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//
//        ResponseEntity<Object> response = authController.login(loginRequest);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertTrue(response.getBody() instanceof HashMap);
//    }

    /**
     * Test Case 2: Login - User not found
     */
    @Test
    void testLoginUserNotFound() {
        LoginRequest loginRequest = new LoginRequest("unknown@example.com", "password");

        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        ResponseEntity<Object> response = authController.login(loginRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
    }

    /**
     * Test Case 3: Login - Invalid credentials
     */
    @Test
    void testLoginInvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "wrongpassword");

        doThrow(new RuntimeException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(RuntimeException.class, () -> authController.login(loginRequest));
    }

    /**
     * Test Case 4: Registration - Successful
     */
//    @Test
//    void testRegisterUserSuccess() {
//        RegistrationRequest registrationRequest = new RegistrationRequest("newuser@example.com", "password", "USER");
//
//        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
//        when(employeeRepository.findByEmail("newuser@example.com"))
//                .thenReturn(Optional.of(new Employee()));
//        when(passwordEncoder.encode("password")).thenReturn("hashed_password");
//
//        ResponseEntity<?> response = authController.registerUser(registrationRequest);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("User registered successfully", response.getBody());
//    }

    /**
     * Test Case 5: Registration - User already exists
     */
    @Test
    void testRegisterUserAlreadyExists() {
        RegistrationRequest registrationRequest = new RegistrationRequest("existing@example.com", "password", "USER");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        ResponseEntity<?> response = authController.registerUser(registrationRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User already exists with this email", response.getBody());
    }
}
