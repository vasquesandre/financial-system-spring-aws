package br.andre.financialsystem.service;

import br.andre.financialsystem.domain.exception.auth.UnauthorizedLoginException;
import br.andre.financialsystem.domain.model.Client;
import br.andre.financialsystem.dto.auth.LoginRequest;
import br.andre.financialsystem.infra.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ClientService clientService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(ClientService clientService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest request) {

        Client client = clientService.findByCpf(request.getCpf());

        if (!passwordEncoder.matches(request.getPassword(), client.getPassword())) {
            throw new UnauthorizedLoginException();
        }

        return jwtService.generateToken(client.getId());
    }

}
