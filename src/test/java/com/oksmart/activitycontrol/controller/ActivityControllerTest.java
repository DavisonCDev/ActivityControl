package com.oksmart.activitycontrol.controller;

import com.oksmart.activitycontrol.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivityControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtualizarDadosEndpoint() throws IOException {
        // Simula a execução do método do serviço
        doNothing().when(activityService).atualizarDados();

        // Chama o endpoint da API para atualizar os dados
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/activity/atualizar", null, String.class);

        // Verifica se a resposta está correta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dados atualizados com sucesso!", response.getBody());

        // Verifica se o serviço foi chamado
        verify(activityService, times(1)).atualizarDados();
    }
}
