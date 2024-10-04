// src/main/java/com/example/apachecamel/service/SyncService.java
package com.example.apachecamel.service;

import com.example.apachecamel.model.Item;
import com.example.apachecamel.repository.mysql.MySqlItemRepository;
import com.example.apachecamel.repository.postgres.PostgresItemRepository;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    private final PostgresItemRepository postgresItemRepository;
    private final MySqlItemRepository mySqlItemRepository;

    public SyncService(PostgresItemRepository postgresItemRepository, MySqlItemRepository mySqlItemRepository) {
        this.postgresItemRepository = postgresItemRepository;
        this.mySqlItemRepository = mySqlItemRepository;
    }

    public void syncInsertToPostgres(Item item) {
        postgresItemRepository.save(item);
    }

    public void syncDeleteToPostgres(Long itemId) {
        postgresItemRepository.deleteById(itemId);
    }

    public void syncInsertToMySql(Item item) {
        mySqlItemRepository.save(item);
    }

    public void syncDeleteToMySql(Long itemId) {
        mySqlItemRepository.deleteById(itemId);
    }
}
