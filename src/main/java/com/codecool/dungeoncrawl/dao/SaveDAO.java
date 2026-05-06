package com.codecool.dungeoncrawl.dao;

import javax.sql.DataSource;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

public class SaveDAO {
    private final DataSource dataSource;

    public SaveDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void resetTables() {
        // Reset items, actors, inventory_items table
        // DELETE * FROM items;
        // DELETE * FROM actors;
        // DELETE * FROM inventory_items;
    }

    public void saveActor(String name, int x, int y, int health, int attackPower) {
        System.out.println(name + x + y + health + attackPower);
        // INSERT INTO actors (name, x, y, health, attackPower) VALUES (name, x, y, health, attackPower)
    }

    public void saveItem(String name, int x, int y) {
        System.out.println(name + x + y);
        // INSERT INTO item (name, x, y) VALUES (name, x, y)
    }

    public void saveInventoryItem(String name) {
        System.out.println(name);
        // INSERT INTO item (name) VALUES (name)
    }
}
