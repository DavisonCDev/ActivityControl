package com.oksmart.activitycontrol.controller;

import com.oksmart.activitycontrol.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/atualizar")
    public ResponseEntity<String> atualizarDados() {
        try {
            activityService.atualizarDados();
            return ResponseEntity.ok("Dados atualizados com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao atualizar os dados: " + e.getMessage());
        }
    }
}
