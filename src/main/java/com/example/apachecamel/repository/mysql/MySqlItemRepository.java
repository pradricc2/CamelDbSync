package com.example.apachecamel.repository.mysql;

import com.example.apachecamel.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySqlItemRepository extends JpaRepository<Item, Long> {
    // Custom query methods if needed

}
