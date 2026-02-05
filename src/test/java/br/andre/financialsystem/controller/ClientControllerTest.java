package br.andre.financialsystem.controller;

import br.andre.financialsystem.domain.enums.ClientStatus;
import br.andre.financialsystem.domain.enums.Role;
import br.andre.financialsystem.domain.model.Client;
import br.andre.financialsystem.dto.client.CreateClientRequest;
import br.andre.financialsystem.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateClient() throws Exception {
        CreateClientRequest request = new CreateClientRequest(
                "Andre",
                "12345678900",
                "123456",
                "andre@email.com",
                Role.CLIENT
        );

        Client client = new Client(
                "1",
                request.getName(),
                request.getCpf(),
                request.getPassword(),
                request.getEmail(),
                BigDecimal.ZERO,
                ClientStatus.ACTIVE,
                Instant.now(),
                request.getRole()
        );

        when(clientService.create(any())).thenReturn(client);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Andre"))
                .andExpect(jsonPath("$.balance").value(0))
                .andExpect(jsonPath("$.email").value("andre@email.com"))
                .andExpect(jsonPath("$.status").value(ClientStatus.ACTIVE.toString()));

    }

    @Test
    void shouldFindClientById() throws Exception {
        Client client = new Client(
                "1",
                "Andre",
                "12345678900",
                "123456",
                "andre@email.com",
                BigDecimal.ZERO,
                ClientStatus.ACTIVE,
                Instant.now(),
                Role.CLIENT
        );

        when(clientService.findById("1")).thenReturn(client);

        mockMvc.perform(get("/clients/1")
                        .with(user("client-123").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Andre"))
                .andExpect(jsonPath("$.balance").value(0))
                .andExpect(jsonPath("$.email").value("andre@email.com"))
                .andExpect(jsonPath("$.status").value(ClientStatus.ACTIVE.toString()));
    }

    @Test
    void shouldFindClientByCpf() throws Exception {
        Client client = new Client(
                "1",
                "Andre",
                "12345678900",
                "123456",
                "andre@email.com",
                BigDecimal.ZERO,
                ClientStatus.ACTIVE,
                Instant.now(),
                Role.CLIENT
        );

        when(clientService.findByCpf("12345678900")).thenReturn(client);

        mockMvc.perform(get("/clients/cpf/12345678900")
                        .with(user("client-123").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Andre"))
                .andExpect(jsonPath("$.balance").value(0))
                .andExpect(jsonPath("$.email").value("andre@email.com"))
                .andExpect(jsonPath("$.status").value(ClientStatus.ACTIVE.toString()));
    }
}