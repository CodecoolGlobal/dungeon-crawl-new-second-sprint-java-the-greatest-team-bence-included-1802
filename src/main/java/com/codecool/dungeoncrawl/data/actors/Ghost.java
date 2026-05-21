package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.geometry.Direction;

public class Ghost extends Actor {
    private int directionIndex;

    public Ghost(Cell cell) {
        super(cell, 12, 5);
        this.directionIndex = 0;
    }

    @Override
    public void move(int dx, int dy) {}

    public void move() {
        Direction[] directions = Direction.values();
        Direction direction = directions[directionIndex];

        int nx = cell.getX() + direction.getDx();
        int ny = cell.getY() + direction.getDy();

        Cell nextCell = cell.getGameMap().getCell(nx, ny);

        if (nextCell.getActor() == null && nextCell.getTileName().equals("floor")) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        directionIndex = (directionIndex + 1) % directions.length;
    }

    @Override
    public String getTileName() {
        return "ghost";
    }
}