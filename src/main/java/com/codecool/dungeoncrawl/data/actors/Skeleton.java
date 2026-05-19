package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell, 8, 2);
    }

    @Override
    public void move(int dx, int dy) {}

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
