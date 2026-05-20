package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;

public class GameLogic {
    private GameMap map;

    public GameLogic() {
        this.map = MapLoader.loadMap(1);
    }

    public double getMapWidth() {
        return map.getWidth();
    }

    public double getMapHeight() {
        return map.getHeight();
    }

    public void setMap(GameMap gameMap) {
        this.map = gameMap;
    }

    public void setup() {
    }

    public Cell getCell(int x, int y) {
        return map.getCell(x, y);
    }

    public String getPlayerHealth() {
        return Integer.toString(map.getPlayer().getHealth());
    }

    public String getPlayerInventory() {
        return map.getPlayer().getInventoryString();
    }

    public String getPlayerAttackPower() {
        return Integer.toString(map.getPlayer().getAttackPower());
    }


    public GameMap getMap() {
        return map;
    }
}
