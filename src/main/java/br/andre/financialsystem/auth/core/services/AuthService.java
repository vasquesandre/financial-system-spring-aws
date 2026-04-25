package br.andre.financialsystem.auth.core.services;

import br.andre.financialsystem.client.core.services.ClientService;
import br.andre.financialsystem.auth.core.domain.exceptions.UnauthorizedLoginException;
import br.andre.financialsystem.client.core.domain.Client;
import br.andre.financialsystem.auth.adapters.in.web.request.LoginRequest;
import br.andre.financialsystem.auth.adapters.out.security.JwtService;
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

        return jwtService.generateToken(client.getId(), client.getRole());
    }

}
