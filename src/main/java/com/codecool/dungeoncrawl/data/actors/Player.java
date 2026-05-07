package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.service.LoadService;
import com.codecool.dungeoncrawl.service.SaveService;
import com.codecool.dungeoncrawl.ui.Tiles;
import javafx.application.Platform;

import java.util.List;

public class Player extends Actor {

    public Player(Cell cell) {
        super(cell, 10, 5);
    }

    public String getTileName() {
        return "player";
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (!nextCell.getTileName().equals("wall") &&
                !nextCell.getTileName().equals("tree") &&
                !nextCell.getTileName().equals("trees") &&
                !nextCell.getTileName().equals("empty")) {

            if (nextCell.getTileName().equals("wc")) {
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

            if (nextCell.getTileName().equals("save")) {
                GameMap gameMap = nextCell.getGameMap();
                SaveService.save(gameMap);
            }

            if (nextCell.getTileName().equals("load")) {
                GameMap gameMap = nextCell.getGameMap();
                //load previous save
                LoadService loadService = new LoadService(gameMap);
                loadService.load();
                return;
            }

            if (nextCell.getActor() != null) {
                Actor enemy = nextCell.getActor();
                enemy.addHealth(-this.attackPower);
                if (enemy.getHealth() > 0) {
                    this.addHealth(-enemy.attackPower);
                    if (this.health <= 0) {
                        System.out.println("You dead");
                        Platform.exit();
                    }

                } else {
                    cell.setActor(null);
                    nextCell.setActor(this);
                    cell = nextCell;
                }

            } else {
                if (nextCell.getItem() != null) {
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
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public String getInventoryString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : getInventory()) {
            sb.append(item.getDisplayName()).append("\n");
        }
        return sb.toString();
    }
}
