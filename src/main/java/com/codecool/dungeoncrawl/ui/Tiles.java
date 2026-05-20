package com.codecool.dungeoncrawl.ui;

import com.codecool.dungeoncrawl.data.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    private static class Tile {
        public final int x, y, w, h;
        public Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(25, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("cat", new Tile(29, 7));
        tileMap.put("dragon", new Tile(28, 8));
        tileMap.put("ghost", new Tile(27,6));
        tileMap.put("sword", new Tile(4,28));
        tileMap.put("key", new Tile(17,23));
        tileMap.put("health", new Tile(17,25));
        tileMap.put("tree", new Tile(0,1));
        tileMap.put("trees", new Tile(3,1));
        tileMap.put("wc", new Tile(12,10));
        tileMap.put("save", new Tile(26,28));
        tileMap.put("load", new Tile(22,20));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }

    public static void changeTileMap(String key, int x, int y) {
        tileMap.replace(key, new Tile(x, y));
    }
}
