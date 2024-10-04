package com.example.apachecamel.route;

import com.example.apachecamel.model.Item;
import com.example.apachecamel.repository.mysql.MySqlItemRepository;
import com.example.apachecamel.repository.postgres.PostgresItemRepository;
import com.example.apachecamel.service.LoggingService;
import com.example.apachecamel.service.SyncService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSyncRoute extends RouteBuilder {


    private final LoggingService loggingService;
    private final SyncService syncService;

    public DatabaseSyncRoute(LoggingService loggingService, SyncService syncService) {
        this.loggingService = loggingService;
        this.syncService = syncService;
    }

    @Override
    public void configure() throws Exception {
        // Sincronizza Insert da MySQL a PostgreSQL
        from("direct:mysqlInsert")
                .log("Inserting item from MySQL to PostgreSQL")
                .process(exchange -> {
                    Item item = exchange.getIn().getBody(Item.class);
                    loggingService.logInfo("Item: " + item);
                    syncService.syncInsertToPostgres(item);
                });

        // Sincronizza Delete da MySQL a PostgreSQL
        from("direct:mysqlDelete")
                .log("Deleting item from MySQL to PostgreSQL")
                .process(exchange -> {
                    Long itemId = exchange.getIn().getBody(Long.class);
                    loggingService.logInfo("Item ID: " + itemId);
                    syncService.syncDeleteToPostgres(itemId);
                });

        // Sincronizza Insert da PostgreSQL a Mysql
        from("direct:postgresInsert")
                .log("Inserting item from PostgreSQL to MySQL")
                .process(exchange -> {
                    Item item = exchange.getIn().getBody(Item.class);
                    loggingService.logInfo("Item: " + item);
                    syncService.syncInsertToMySql(item);
                });

        // Sincronizza Delete da PostgreSQL a Mysql
        from("direct:postgresDelete")
                .log("Deleting item from PostgreSQL to MySQL")
                .process(exchange -> {
                    Long itemId = exchange.getIn().getBody(Long.class);
                    loggingService.logInfo("Item ID: " + itemId);
                    syncService.syncDeleteToMySql(itemId);
                });

    }
}

