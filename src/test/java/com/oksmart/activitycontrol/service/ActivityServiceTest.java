package com.oksmart.activitycontrol.service;

import com.oksmart.activitycontrol.model.Activity;
import com.oksmart.activitycontrol.repository.ActivityRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtualizarDados() throws IOException {
        // Prepara o mock do Excel
        String filePath = "src/test/resources/dataActivityTest.xlsx"; // Altere conforme necessário
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Cliente");
        row.createCell(1).setCellValue(123);
        row.createCell(2).setCellValue(456);
        row.createCell(3).setCellValue(789);
        row.createCell(4).setCellValue("Tipo1");

        // Chama o método que está sendo testado
        activityService.atualizarDados();

        // Verifica se o método saveAll foi chamado uma vez
        verify(activityRepository, times(1)).saveAll(anyList());
    }
}
