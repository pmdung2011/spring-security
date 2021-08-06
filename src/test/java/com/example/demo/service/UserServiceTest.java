package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;

    @BeforeEach
    public void setUp() {
        userServiceUnderTest = new UserService(mockUserRepository,
                                               mockRoleRepository,
                                               mockBCryptPasswordEncoder);
        User user = User.builder()
                .id(1)
                .name("Gustavo")
                .lastName("Ponce")
                .email("test@test.com")
                .build();

        /**
         * we use the static method Mockito.lenient() to enable the lenient stubbing on the add method of our mock list.
         * Lenient stubs bypass “strict stubbing” validation rules. For example, when stubbing is declared as lenient,
         * it won't be checked for potential stubbing problems such as the unnecessary stubbing described earlier.
         */
        Mockito.lenient().when(mockUserRepository.save(any()))
                .thenReturn(user);
        Mockito.lenient().when(mockUserRepository.findByEmail(anyString()))
                .thenReturn(user);
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        final User result = userServiceUnderTest.findUserByEmail(email);

        // Verify the results
        Assertions.assertEquals(email, result.getEmail());
    }

    @Test
    public void testSaveUser() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        User result = userServiceUnderTest.saveUser(User.builder().build());

        // Verify the results
        Assertions.assertEquals(email, result.getEmail());
    }
}
