package com.codecool.dungeoncrawl.service;

import com.codecool.dungeoncrawl.dao.LoadDAO;
import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.*;
import com.codecool.dungeoncrawl.data.items.Health;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.data.items.Key;
import com.codecool.dungeoncrawl.data.items.Sword;
import com.codecool.dungeoncrawl.ui.Tiles;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class LoadService {
    public GameMap gameMap;

    public LoadService(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void load() {
        DataSource dataSource;
        try {
            dataSource = SQLService.connect();
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
        loadDAO.loadActors(this);
        loadDAO.loadItems(this);
        loadDAO.loadInventoryItems(this);
    }

    public void loadActor(String name, int x, int y, int health, int attackPower) {
        Actor actor = null;
        Cell[][] cells = gameMap.getCells();
        switch (name) {
            case "player":
                gameMap.setPlayer(new Player(cells[x][y]));
                actor = gameMap.getPlayer();
                break;
            case "skeleton":
                actor = new Skeleton(cells[x][y]);
                break;
            case "cat":
                actor = new Cat(cells[x][y]);
                break;
            case "dragon":
                actor = new Dragon(cells[x][y]);
                break;

        }
        if (actor != null) {
            actor.setHealth(health);
            actor.setAttackPower(attackPower);
            cells[x][y].setActor(actor);
        }
    }

    public void loadItem(String name, int x, int y) {
        Item item = null;
        Cell[][] cells = gameMap.getCells();
        switch (name) {
            case "health":
                item = new Health(cells[x][y]);
                break;
            case "key":
                item = new Key(cells[x][y]);
                break;
            case "sword":
                item = new Sword(cells[x][y]);
                break;
        }
        if (item != null) {
            cells[x][y].setItem(item);
        }
    }

    public void loadInventoryItem(String name) {
        Cell cell = gameMap.getCell(0, 0);
        Actor player = gameMap.getPlayer();
        switch (name) {
            case "Key":
                player.addItem(new Key(cell));
                break;
            case "Sword":
                player.addItem(new Sword(cell));
                Tiles.changeTileMap("player", 27, 0);
                break;
        }
        cell.setItem(null);
    }
}
