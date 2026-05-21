package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.logic.Game;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.service.SQLService;
import com.codecool.dungeoncrawl.ui.Tiles;
import javafx.application.Platform;

import java.util.List;

public class Player extends Actor {

    private final SQLService sqlService;
    private int moveMultiplier = 1;

    public Player(Cell cell) {
        super(cell, 10, 5);
        sqlService = new SQLService(cell.getGameMap());
    }

    public void setMoveMultiplier(int moveMultiplier){
        this.moveMultiplier = moveMultiplier;
    }
    @Override
    public String getTileName() {
        return "player";
    }

    @Override
    public void move(int dx, int dy) {
        dx *= moveMultiplier;
        dy *= moveMultiplier;
        moveMultiplier = 1;

        // Out of bounds check
        if (cell.getX() + dx < 0 || cell.getX() + dx >= cell.getGameMap().getWidth()) {
            return;
        }
        if (cell.getY() + dy < 0 || cell.getY() + dy >= cell.getGameMap().getHeight()) {
            return;
        }


        Cell nextCell = cell.getNeighbor(dx, dy);

        if (nextCell.getActor() != null) {
            fight(nextCell);

        } else if (nextCell.getItem() != null) {
            pickUpItem(nextCell);
            step(nextCell);

        } else {
            switch (nextCell.getTileName()) {
                case "floor":
                    step(nextCell);
                    break;
                case "wc":
                    flush();
                    step(nextCell);
                    break;
                case "save":
                    save(nextCell);
                    break;
                case "door":
                    openDoor(nextCell);
                    break;
                case "opendoor":
                    goToNextArea(nextCell);
                    break;
                case "load":
                    load();
                    break;
            }
        }
    }

    private void save(Cell nextCell) {
        sqlService.save();
        step(nextCell);
    }

    private void openDoor(Cell nextCell) {
        for (Item item : getInventory()) {
            if (item.getTileName().equals("key")) {
                removeInventoryItem(item);
                nextCell.setType(CellType.OPENDOOR);
                break;
            }
        }
    }

    private void goToNextArea(Cell nextCell) {
        GameMap gameMap = nextCell.getGameMap();
        Game.logic.setMap(MapLoader.loadMap(gameMap.getMapId() + 1, this));
    }

    private void load() {
        sqlService.load();
    }

    private void fight(Cell nextCell) {
        Actor enemy = nextCell.getActor();
        enemy.addHealth(-this.attackPower);
        if (enemy.getHealth() > 0) {
            this.addHealth(-enemy.attackPower);
            if (this.health <= 0) {
                System.out.println("You dead");
                Platform.exit();
            }
        } else {
            step(nextCell);
        }
    }

    private void flush() {
        List<Item> inventory = getInventory();
        for (Item item : inventory) {
            if (!item.getTileName().equals("key")) {
                if (item.getTileName().equals("sword")) {
                    attackPower -= 5;
                    Tiles.changeTileMap("player", 25, 0);
                }
                removeInventoryItem(item);
            }
        }
    }

    private void pickUpItem(Cell nextCell) {
        if (nextCell.getItem().getTileName().equals("health")) {
            this.addHealth(5);
        } else {
            if (nextCell.getItem().getTileName().equals("sword")) {
                attackPower += 5;
                Tiles.changeTileMap("player", 27, 0);
            }
            addItem(nextCell.getItem());
        }
        nextCell.setItem(null);
    }

    private void step(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public String getInventoryString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : getInventory()) {
            sb.append(item.getDisplayName()).append("\n");
        }
        return sb.toString();
    }
}
