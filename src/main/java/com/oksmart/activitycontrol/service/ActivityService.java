package com.oksmart.activitycontrol.service;

import com.oksmart.activitycontrol.model.Activity;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void atualizarDados() throws IOException {
        String filePath = "C:\\temp\\dataActivity.xlsx";
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getPhysicalNumberOfRows() == 0) {
                System.out.println("O arquivo Excel está vazio.");
                return;
            }

            List<Activity> activities = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                try {
                    String cliente = row.getCell(0).getStringCellValue();
                    Integer central = (int) row.getCell(1).getNumericCellValue();
                    Integer linha = (int) row.getCell(2).getNumericCellValue();
                    Integer endereco = (int) row.getCell(3).getNumericCellValue();
                    String tipo = row.getCell(4).getStringCellValue();
                    String descritivo = row.getCell(5).getStringCellValue();

                    Activity activity = new Activity(cliente, central, linha, endereco, tipo, descritivo);
                    activities.add(activity);

                    criarTabelaSeNecessario(cliente);
                    salvarDadosNaTabela(cliente, activity);
                } catch (Exception e) {
                    System.out.println("Erro ao processar a linha " + row.getRowNum() + ": " + e.getMessage());
                }
            }

            System.out.println("Total de atividades lidas: " + activities.size());

            if (activities.isEmpty()) {
                System.out.println("Nenhuma atividade foi lida do arquivo.");
            }
        } catch (IOException e) {
            throw new IOException("Erro ao processar o arquivo Excel: " + e.getMessage(), e);
        }
    }

    private void criarTabelaSeNecessario(String cliente) {
        String clienteTabela = cliente.replaceAll("\\s+", "_"); // Substitui espaços por underline
        String sql = "SHOW TABLES LIKE ?";
        List<String> tables = jdbcTemplate.queryForList(sql, String.class, clienteTabela);

        if (tables.isEmpty()) {
            String createTableSql = "CREATE TABLE `" + clienteTabela + "` ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "cliente VARCHAR(255), "
                    + "central INT, "
                    + "linha INT, "
                    + "endereco INT, "
                    + "tipo VARCHAR(255), "
                    + "descritivo VARCHAR(255) "
                    + ")";
            jdbcTemplate.execute(createTableSql);
            System.out.println("Tabela criada para o cliente: " + clienteTabela);
        } else {
            System.out.println("Tabela já existe para o cliente: " + clienteTabela);
        }
    }

    private void salvarDadosNaTabela(String cliente, Activity activity) {
        String clienteTabela = cliente.replaceAll("\\s+", "_"); // Substitui espaços por underline
        String insertSql = "INSERT INTO `" + clienteTabela + "` (cliente, central, linha, endereco, tipo, descritivo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql,
                activity.getCliente(), activity.getCentral(),
                activity.getLinha(), activity.getEndereco(),
                activity.getTipo(), activity.getDescritivo());
        System.out.println("Atividade salva na tabela " + clienteTabela);
    }
}
