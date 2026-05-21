package com.codecool.dungeoncrawl.data;

import com.codecool.dungeoncrawl.data.actors.Player;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private int mapId;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType, int mapId) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
        this.mapId = mapId;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getCells() {
        Cell[][] copy = new Cell[cells.length][];
        for (int i = 0; i < cells.length; i++) {
            copy[i] = cells[i].clone();
        }
        return copy;
    }

    public int getMapId() { return this.mapId; }
}
