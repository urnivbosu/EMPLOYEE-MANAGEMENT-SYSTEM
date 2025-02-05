package com.thapasya.infopark;

import com.thapasya.infopark.models.User;
import com.thapasya.infopark.repository.UserRepository;
import com.thapasya.infopark.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password123");
        mockUser.setRole("EMPLOYEE");
    }

    @Test
    void loadUserByUsername_UserFound_ShouldReturnUserDetails() {
        // Arrange: Mock the repository to return the test user
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        // Act: Call the service method
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");

        // Assert: Verify the result
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE")));

        // Verify interaction with the repository
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void loadUserByUsername_UserNotFound_ShouldThrowException() {
        // Arrange: Mock repository to return empty
        when(userRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        // Act & Assert: Expect UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("invalid@example.com"));

        // Verify interaction with the repository
        verify(userRepository, times(1)).findByEmail("invalid@example.com");
    }
}
