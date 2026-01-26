package br.andre.financialsystem.controller;

import br.andre.financialsystem.config.security.JwtAuthenticationFilter;
import br.andre.financialsystem.domain.enums.Role;
import br.andre.financialsystem.domain.enums.TransactionStatus;
import br.andre.financialsystem.domain.enums.TransactionType;
import br.andre.financialsystem.domain.model.Transaction;
import br.andre.financialsystem.dto.transaction.CreateTransactionRequest;
import br.andre.financialsystem.infra.security.JwtService;
import br.andre.financialsystem.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void shouldCreateTransaction() throws Exception {

        CreateTransactionRequest request = new CreateTransactionRequest(
                BigDecimal.valueOf(100),
                TransactionType.CREDIT
        );

        Transaction transaction = new Transaction(
                "tx-1",
                "client-123",
                request.getValue(),
                request.getType(),
                BigDecimal.ZERO,
                TransactionStatus.PENDING,
                Instant.now()
        );

        when(transactionService.create(any(), eq("client-123")))
                .thenReturn(transaction);

        mockMvc.perform(post("/transactions")
                        .with(user("client-123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("tx-1"))
                .andExpect(jsonPath("$.clientId").value("client-123"))
                .andExpect(jsonPath("$.value").value(100))
                .andExpect(jsonPath("$.status").value(TransactionStatus.PENDING.toString()))
                .andExpect(jsonPath("$.type").value("CREDIT"))
                .andExpect(jsonPath("$.balanceAtCreation").value(BigDecimal.ZERO));
    }

    @Test
    void shouldFindTransactionById() throws Exception {

        Transaction transaction = new Transaction(
                "tx-1",
                "client-123",
                BigDecimal.valueOf(50),
                TransactionType.DEBIT,
                BigDecimal.ZERO,
                TransactionStatus.PENDING,
                Instant.now()
        );

        when(transactionService.findById("tx-1", "1", Role.ADMIN))
                .thenReturn(transaction);

        mockMvc.perform(get("/transactions/tx-1")
                        .with(user("client-123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("tx-1"))
                .andExpect(jsonPath("$.clientId").value("client-123"))
                .andExpect(jsonPath("$.value").value(50))
                .andExpect(jsonPath("$.status").value(TransactionStatus.PENDING.toString()))
                .andExpect(jsonPath("$.type").value("DEBIT"))
                .andExpect(jsonPath("$.balanceAtCreation").value(0));;
    }

    @Test
    void shouldFindTransactionsByClient() throws Exception {

        Transaction t1 = new Transaction(
                "tx-1",
                "client-123",
                BigDecimal.valueOf(100),
                TransactionType.CREDIT,
                BigDecimal.ZERO,
                TransactionStatus.PENDING,
                Instant.now()
        );

        Transaction t2 = new Transaction(
                "tx-2",
                "client-123",
                BigDecimal.valueOf(50),
                TransactionType.DEBIT,
                BigDecimal.ZERO,
                TransactionStatus.PENDING,
                Instant.now()
        );

        when(transactionService.findByClientId(eq("client-123"), eq("client-123")))
                .thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/transactions/client/client-123")
                        .with(user("client-123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("tx-1"))
                .andExpect(jsonPath("$[0].clientId").value("client-123"))
                .andExpect(jsonPath("$[0].value").value(100))
                .andExpect(jsonPath("$[0].status").value(TransactionStatus.PENDING.toString()))
                .andExpect(jsonPath("$[0].type").value("CREDIT"))
                .andExpect(jsonPath("$[0].balanceAtCreation").value(0))
                .andExpect(jsonPath("$[1].id").value("tx-2"))
                .andExpect(jsonPath("$[1].clientId").value("client-123"))
                .andExpect(jsonPath("$[1].value").value(50))
                .andExpect(jsonPath("$[1].status").value(TransactionStatus.PENDING.toString()))
                .andExpect(jsonPath("$[1].type").value("DEBIT"))
                .andExpect(jsonPath("$[1].balanceAtCreation").value(0));
    }
}