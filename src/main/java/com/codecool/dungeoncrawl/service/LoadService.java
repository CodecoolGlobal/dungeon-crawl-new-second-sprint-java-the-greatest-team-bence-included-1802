package com.codecool.dungeoncrawl.service;

import com.codecool.dungeoncrawl.dao.LoadDAO;
import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class LoadService {

    public static void load(GameMap gameMap) {
        DataSource dataSource;
        try {
            dataSource = connect();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to connect to the SQL", exception);
        }
        LoadDAO loadDAO = new LoadDAO(dataSource);

        // Reset GameMap
        Cell[][] cells = gameMap.getCells();

        for (Cell[] cell : cells) {
            for (Cell value : cell) {

                if (value.getTileName().equals("load")) {
                    value.setType(CellType.LOAD);
                }
                value.setActor(null);
                value.setItem(null);
            }
        }
        gameMap.setPlayer(null);
        loadDAO.loadActors(gameMap);
        loadDAO.loadItems(gameMap);

        //gameMap.setPlayer(null);
        //gameMap.setPlayer(new Player(cells[1][1]));

        //loadDAO.loadActors();

        /*ResultSet actorsResult;
        ResultSet itemsResult;
        ResultSet inventoryItemsResult;
        try {
            actorsResult = loadDAO.loadActors();
            itemsResult = loadDAO.loadItems();
            inventoryItemsResult = loadDAO.loadInventoryItems();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ResultSet conversion and handle the map change

        Cell[][] cells = gameMap.getCells();*/
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
