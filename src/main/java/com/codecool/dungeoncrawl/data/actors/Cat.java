package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;

public class Cat extends Actor {
    public Cat(Cell cell) {
        super(cell, 9, 666);
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        getCell().setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    @Override
    public String getTileName() {
        return "cat";
    }
}
