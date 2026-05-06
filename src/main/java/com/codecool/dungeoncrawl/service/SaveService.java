package com.codecool.dungeoncrawl.service;

import com.codecool.dungeoncrawl.dao.SaveDAO;
import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.items.Item;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class SaveService {

    public static void save(GameMap gameMap) {
        DataSource dataSource;
        try {
            dataSource = connect();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to connect to the SQL", exception);
        }
        SaveDAO saveDAO = new SaveDAO(dataSource);

        Cell[][] cells = gameMap.getCells();

        saveDAO.resetTables();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Actor actor = cells[i][j].getActor();
                Item item = cells[i][j].getItem();

                if (actor != null) {
                    saveDAO.saveActor(actor.getTileName(), actor.getX(), actor.getY(), actor.getHealth(), actor.getAttackPower());
                }
                if (item != null) {
                    saveDAO.saveItem(item.getTileName(), item.getCell().getX(), item.getCell().getY());
                }
            }
        }

        List<Item> inventory = gameMap.getPlayer().getInventory();
        for (Item inventoryItem : inventory) {
            saveDAO.saveInventoryItem(inventoryItem.getDisplayName());
        }
    }

    private static DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName("dungeon_crawl");
        dataSource.setUser("postgres");
        dataSource.setPassword("Q4w3e2r1!");

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }
}
