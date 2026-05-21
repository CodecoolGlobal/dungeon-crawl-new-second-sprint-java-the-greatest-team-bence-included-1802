package com.codecool.dungeoncrawl.service;

import com.codecool.dungeoncrawl.dao.SQLDao;
import com.codecool.dungeoncrawl.dao.sqlData.ActorData;
import com.codecool.dungeoncrawl.dao.sqlData.InventoryData;
import com.codecool.dungeoncrawl.dao.sqlData.ItemData;
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
import java.util.List;

public class SQLService {
    private static final SQLDao sqlDao = new SQLDao();
    private final GameMap gameMap;

    public SQLService(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void save() {
        Cell[][] cells = gameMap.getCells();
        int mapId = gameMap.getMapId();
        sqlDao.resetTables(mapId);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Actor actor = cells[i][j].getActor();
                Item item = cells[i][j].getItem();

                if (actor != null) {
                    sqlDao.saveActor(mapId, actor.getTileName(), actor.getX(), actor.getY(), actor.getHealth(), actor.getAttackPower(), actor.getGold());
                }
                if (item != null) {
                    sqlDao.saveItem(mapId, item.getTileName(), item.getCell().getX(), item.getCell().getY());
                }
            }
        }

        List<Item> inventory = gameMap.getPlayer().getInventory();
        for (Item inventoryItem : inventory) {
            sqlDao.saveInventoryItem(mapId, inventoryItem.getDisplayName());
        }
    }

    public void load() {
        // Reset GameMap
        Cell[][] cells = gameMap.getCells();
        int mapId = gameMap.getMapId();

        Tiles.changeTileMap("player", 25, 0);

        for (Cell[] cell : cells) {
            for (Cell value : cell) {

                if (!value.getTileName().equals("load") && !value.getTileName().equals("merchant")) {
                    value.setActor(null);
                    value.setItem(null);
                }
            }
        }
        gameMap.setPlayer(null);

        List<ActorData> actors = sqlDao.loadActors(mapId);
        for (ActorData actorData : actors) {
            loadActor(actorData.name(), actorData.x(), actorData.y(), actorData.health(), actorData.attackPower(), actorData.gold());
        }

        List<ItemData> items = sqlDao.loadItems(mapId);
        for (ItemData itemData : items) {
            loadItem(itemData.name(), itemData.x(), itemData.y());
        }

        List<InventoryData> inventoryItems = sqlDao.loadInventoryItems(mapId);
        for (InventoryData inventoryItem : inventoryItems) {
            loadInventoryItem(inventoryItem.name());
        }
    }

    private void loadActor(String name, int x, int y, int health, int attackPower, int gold) {
        Actor actor = null;
        Cell[][] cells = gameMap.getCells();

        actor = switch (name) {
            case "player" -> {
                gameMap.setPlayer(new Player(cells[x][y], new SQLService(gameMap)));
                yield gameMap.getPlayer();
            }
            case "skeleton" -> new Skeleton(cells[x][y]);
            case "cat" -> new Cat(cells[x][y]);
            case "dragon" -> new Dragon(cells[x][y]);
            default -> actor;
        };

        if (actor != null) {
            actor.setHealth(health);
            actor.setAttackPower(attackPower);
            actor.increaseGold(gold);
            cells[x][y].setActor(actor);
        }
    }

    private void loadItem(String name, int x, int y) {
        Item item = null;
        Cell[][] cells = gameMap.getCells();
        item = switch (name) {
            case "health" -> new Health(cells[x][y]);
            case "key" -> new Key(cells[x][y]);
            case "sword" -> new Sword(cells[x][y]);
            default -> item;
        };
        if (item != null) {
            cells[x][y].setItem(item);
        }
    }

    private void loadInventoryItem(String name) {
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
