# Descrizione del Progetto
Applicazione studio del framework Apache Camel applicato alla sincronizzazione tra 2 tabelle identiche su DB mysql e postgres mediante api rest
## Linguaggi e Frameworks Utilizzati:  
- Java
- Spring Boot
- Maven
## Componenti Principali:  
- Controller (ItemController):  
Gestisce le richieste HTTP per la creazione e l'eliminazione di oggetti Item nei database MySQL e PostgreSQL.
Utilizza ProducerTemplate di Apache Camel per sincronizzare i dati tra i due database.
Esegue la validazione degli input utilizzando annotazioni di validazione (@Valid).
Utilizza LoggingService per il logging centralizzato.
- Service (SyncService):  
Contiene la logica di sincronizzazione per inserire ed eliminare oggetti Item tra MySQL e PostgreSQL.
Fornisce metodi per sincronizzare le operazioni di inserimento e cancellazione tra i due database.
- Service (LoggingService):  
Fornisce metodi centralizzati per il logging a diversi livelli (info, error, debug, warn).
Utilizzato sia nel ItemController che nel DatabaseSyncRoute per registrare messaggi di log.
- Route (DatabaseSyncRoute):  
Configura le rotte di Apache Camel per sincronizzare le operazioni di inserimento e cancellazione tra MySQL e PostgreSQL.
Utilizza SyncService per eseguire la logica di sincronizzazione.
Utilizza LoggingService per registrare messaggi di log durante le operazioni di sincronizzazione.
- DTO (ItemDTO):  
Oggetto di trasferimento dati utilizzato per trasferire i dati tra il client e il server.
Contiene annotazioni di validazione per garantire che i dati siano corretti prima di essere elaborati.
- Configurazione (PostgresDataSourceConfig):  
Configura il datasource e il transaction manager per il database PostgreSQL.
Definisce il bean postgresTransactionManager per gestire le transazioni JPA.
## Struttura del Progetto
- src/main/java/com/example/apachecamel/controller/ItemController.java
- src/main/java/com/example/apachecamel/service/SyncService.java
- src/main/java/com/example/apachecamel/service/LoggingService.java
- src/main/java/com/example/apachecamel/route/DatabaseSyncRoute.java
- src/main/java/com/example/apachecamel/dto/ItemDTO.java
- src/main/java/com/example/apachecamel/config/PostgresDataSourceConfig.java
## Funzionalità Principali
- Creazione di Item:  
Endpoint per creare un Item in MySQL e sincronizzarlo con PostgreSQL.
Endpoint per creare un Item in PostgreSQL e sincronizzarlo con MySQL.
- Eliminazione di Item:  
Endpoint per eliminare un Item da MySQL e sincronizzarlo con PostgreSQL.
Endpoint per eliminare un Item da PostgreSQL e sincronizzarlo con MySQL.
- Sincronizzazione dei Dati:  
Utilizzo di Apache Camel per sincronizzare le operazioni di inserimento e cancellazione tra i due database.
- Logging Centralizzato:  
Utilizzo di LoggingService per registrare messaggi di log in modo centralizzato.
- Validazione degli Input:
Utilizzo di annotazioni di validazione nei DTO per garantire che i dati siano corretti prima di essere elaborati.
