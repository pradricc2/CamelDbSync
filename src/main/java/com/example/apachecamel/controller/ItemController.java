package com.example.apachecamel.controller;


import com.example.apachecamel.dto.ItemDTO;
import com.example.apachecamel.model.Item;
import com.example.apachecamel.repository.mysql.MySqlItemRepository;
import com.example.apachecamel.repository.postgres.PostgresItemRepository;
import com.example.apachecamel.service.LoggingService;
import jakarta.validation.Valid;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final MySqlItemRepository mysqlItemRepository;
    private final PostgresItemRepository postgresItemRepository;
    private final ProducerTemplate producerTemplate;
    private final LoggingService loggingService;

    public ItemController(MySqlItemRepository mysqlItemRepository, PostgresItemRepository postgresItemRepository, ProducerTemplate producerTemplate, LoggingService loggingService) {
        this.mysqlItemRepository = mysqlItemRepository;
        this.postgresItemRepository = postgresItemRepository;
        this.producerTemplate = producerTemplate;
        this.loggingService = loggingService;
    }

    @PostMapping("/mysql")
    public ResponseEntity<ItemDTO> createItemMysql(@Valid @RequestBody ItemDTO itemDTO) {
        try {
            // Inserimento in MySQL
            Item item = new Item();
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(new BigDecimal(itemDTO.getPrice()));
            item.setCreatedAt(LocalDateTime.parse(itemDTO.getCreatedAt()));
            item.setUpdatedAt(LocalDateTime.parse(itemDTO.getUpdatedAt()));
            item.setProcessed(itemDTO.isProcessed());
            Item newItem = mysqlItemRepository.save(item);

            // Sincronizzazione su PostgreSQL tramite Camel
            producerTemplate.sendBody("direct:mysqlInsert", newItem);

            itemDTO.setId(newItem.getId());

            return ResponseEntity.ok(itemDTO);
        } catch (Exception e) {
            loggingService.logError("Error while creating item in MySQL", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/postgres")
    public ResponseEntity<ItemDTO> createItemPostgres(@Valid @RequestBody ItemDTO itemDTO) {
        try {
            Item item = new Item();
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(new BigDecimal(itemDTO.getPrice()));
            item.setCreatedAt(LocalDateTime.parse(itemDTO.getCreatedAt()));
            item.setUpdatedAt(LocalDateTime.parse(itemDTO.getUpdatedAt()));
            item.setProcessed(itemDTO.isProcessed());
            // Inserimento in Postgres
            Item newItem = postgresItemRepository.save(item);

            // Sincronizzazione su MySql tramite Camel
            producerTemplate.sendBody("direct:postgresInsert", newItem);
            itemDTO.setId(newItem.getId());
            return ResponseEntity.ok(itemDTO);
        } catch (Exception e) {
            loggingService.logError("Error while creating item in Postgres", e);
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/mysql/{id}")
    public ResponseEntity<Void> deleteItemMysql(@PathVariable Long id) {
        try {
            // Eliminazione da MySQL
            mysqlItemRepository.deleteById(id);

            // Sincronizzazione su PostgreSQL tramite Camel
            producerTemplate.sendBody("direct:mysqlDelete", id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            loggingService.logError("Error while deleting item in MySQL", e);
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/postgres/{id}")
    public ResponseEntity<Void> deleteItemPostgres(@PathVariable Long id){
        try {
            // Eliminazione da Postgres
            postgresItemRepository.deleteById(id);

            // Sincronizzazione su PostgreSQL tramite Camel
            producerTemplate.sendBody("direct:postgresDelete", id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            loggingService.logError("Error while deleting item in Postgres", e);
            return ResponseEntity.status(500).build();
        }

    }
}

