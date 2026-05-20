package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;

    public Item(Cell cell) {
        this.cell = cell;

        if (cell != null) {
            cell.setItem(this);
        }
    }

    public Cell getCell() {
        return cell;
    }

    public abstract String getDisplayName();
}