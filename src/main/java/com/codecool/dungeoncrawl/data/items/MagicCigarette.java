package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;

public class MagicCigarette extends Item{
    public MagicCigarette(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "magicCigarette";
    }

    public String getDisplayName() {
        return "Magic cigarette";
    }
}
