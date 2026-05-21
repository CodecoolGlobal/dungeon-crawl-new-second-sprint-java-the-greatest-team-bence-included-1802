package com.codecool.dungeoncrawl.data;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    TREE("tree"),
    TREES("trees"),
    WC("wc"),
    SAVE("save"),
    LOAD("load"),
    DOOR("door"),
    MERCHANT("merchant"),
    OPENDOOR("opendoor");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
