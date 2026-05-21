package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.items.*;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.logic.Game;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.service.SQLService;
import com.codecool.dungeoncrawl.ui.Tiles;
import com.codecool.dungeoncrawl.ui.windows.ShopWindow;
import javafx.application.Platform;

import java.util.List;
import java.util.random.RandomGenerator;

public class Player extends Actor {

    private final SQLService sqlService;
    private int moveMultiplier = 1;
    private int gold = 0;

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
                } else if (item.getTileName().equals("gun")) {
                    attackPower -= 10;
                    Tiles.changeTileMap("player", 25, 0);
                }
                removeInventoryItem(item);
            }
        }
    }

    private void pickUpItem(Cell nextCell) {
        if (nextCell.getItem() instanceof Health) {
            this.addHealth(5);
        } else if (nextCell.getItem() instanceof Gold) {
            gold++;

        } else if (nextCell.getItem().getTileName().equals("magicWand")) {
            Cell targetCell = getTargetCell();
            cell.setActor(null);
            cell = targetCell;
            targetCell.setActor(this);

        } else {
            if (nextCell.getItem() instanceof Sword) {
                attackPower += 5;
                Tiles.changeTileMap("player", 27, 0);
            } else if (nextCell.getItem().getTileName().equals("gun")) {
                attackPower += 10;
                Tiles.changeTileMap("player", 26, 0);
            }
            addItem(nextCell.getItem());
        }
        nextCell.setItem(null);

    }

    private Cell getTargetCell() {
        RandomGenerator gen = RandomGenerator.getDefault();
        int randomX = gen.nextInt(0, cell.getGameMap().getWidth() - 1);
        int randomY = gen.nextInt(0, cell.getGameMap().getHeight() - 1);
        Cell[][] cells = cell.getGameMap().getCells();
        Cell targetCell = cells[randomX][randomY];

        while (!targetCell.getType().equals(CellType.FLOOR)) {
            randomX = gen.nextInt(0, cell.getGameMap().getWidth() - 1);
            randomY = gen.nextInt(0, cell.getGameMap().getHeight() - 1);
            targetCell = cells[randomX][randomY];
        }
        return targetCell;
    }

    private void step(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public int getPlayerGold() {
        return gold;
    }

    public String getInventoryString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : getInventory()) {
            sb.append(item.getDisplayName()).append("\n");
        }
        return sb.toString();
    }

    public void interact() {
        Cell[] neighbors = {
                getCell().getNeighbor(0, -1),
                getCell().getNeighbor(0, 1),
                getCell().getNeighbor(-1, 0),
                getCell().getNeighbor(1, 0)
        };

        for (Cell cell : neighbors) {
            Actor actor = cell.getActor();

            if (actor instanceof Merchant merchant) {
                new ShopWindow(this, merchant).show();
            }
        }
    }

    public void purchase(ShopItem shopItem) {
        if (gold >= shopItem.getPrice()) {
            addItem(shopItem.getItem());
            gold -= shopItem.getPrice();
            System.out.println("Purchased");
        } else {
            System.out.println("No money in the bank.");
        }
    }
}
