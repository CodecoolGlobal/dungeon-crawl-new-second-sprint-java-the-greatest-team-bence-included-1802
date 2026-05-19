package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;

public class Dragon extends Actor {
    public Dragon(Cell cell) {
        super(cell, 12, 5);
    }

    @Override
    public void move(int dx, int dy) {}

    @Override
    public String getTileName() {
        return "dragon";
    }
}