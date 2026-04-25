package br.andre.financialsystem.service;

import br.andre.financialsystem.auth.core.services.AuthService;
import br.andre.financialsystem.client.core.services.ClientService;
import br.andre.financialsystem.client.core.domain.enums.ClientStatus;
import br.andre.financialsystem.auth.core.domain.enums.Role;
import br.andre.financialsystem.auth.core.domain.exceptions.UnauthorizedLoginException;
import br.andre.financialsystem.client.core.domain.Client;
import br.andre.financialsystem.auth.adapters.in.web.request.LoginRequest;
import br.andre.financialsystem.auth.adapters.out.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;

    private ClientService clientService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    private Client client;

    @BeforeEach
    void setUp() {
        clientService = mock(ClientService.class);
        jwtService = mock(JwtService.class);
        passwordEncoder = mock(PasswordEncoder.class);

        authService = new AuthService(clientService, jwtService, passwordEncoder);

        client = new Client(
                "id",
                "Andre",
                "cpf",
                "encoded-pass",
                "andre@email.com",
                new BigDecimal("500.00"),
                ClientStatus.ACTIVE,
                Instant.now(),
                Role.ADMIN
        );
    }

    @Test
    void shouldLoginSuccessfully() {
        LoginRequest request = new LoginRequest("cpf", "123456");

        when(clientService.findByCpf("cpf")).thenReturn(client);
        when(passwordEncoder.matches("123456", "encoded-pass")).thenReturn(true);
        when(jwtService.generateToken("id", Role.ADMIN)).thenReturn("jwt-token");

        String token = authService.login(request);

        assertNotNull(token);
        assertEquals("jwt-token", token);

        verify(jwtService).generateToken("id", Role.ADMIN);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        LoginRequest request = new LoginRequest("cpf", "wrong");

        when(clientService.findByCpf("cpf")).thenReturn(client);
        when(passwordEncoder.matches("wrong", "encoded-pass")).thenReturn(false);

        assertThrows(
                UnauthorizedLoginException.class,
                () -> authService.login(request)
        );

        verify(jwtService, never()).generateToken(any(), any());
    }
}