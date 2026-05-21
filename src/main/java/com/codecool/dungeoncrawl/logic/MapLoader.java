package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.*;
import com.codecool.dungeoncrawl.data.items.Gold;
import com.codecool.dungeoncrawl.data.items.Health;
import com.codecool.dungeoncrawl.data.items.Key;
import com.codecool.dungeoncrawl.data.items.Sword;
import com.codecool.dungeoncrawl.data.actors.Cat;
import com.codecool.dungeoncrawl.data.actors.Dragon;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.actors.Skeleton;
import com.codecool.dungeoncrawl.data.items.*;
import com.codecool.dungeoncrawl.data.actors.*;
import com.codecool.dungeoncrawl.service.SQLService;
import com.codecool.dungeoncrawl.ui.UI;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(int mapId) {
        InputStream is = MapLoader.class.getResourceAsStream("/map" + mapId + ".txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY, mapId);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case '2':
                            cell.setType(CellType.TREES);
                            break;
                        case 'v':
                            cell.setType(CellType.SAVE);
                            break;
                        case 'l':
                            cell.setType(CellType.LOAD);
                            break;
                        case 'w':
                            cell.setType(CellType.WC);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case '§':
                            cell.setType(CellType.FLOOR);
                            map.setGhost(new Ghost(cell));
                            break;
                        case 'n':
                            cell.setType(CellType.DOOR);
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Cat(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.FLOOR);
                            new Dragon(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell, new SQLService(cell.getGameMap())));
                            break;
                        case '|':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new Health(cell);
                            break;
                        case 'ŀ':
                            cell.setType(CellType.FLOOR);
                            new MagicWand(cell);
                            break;
                        case 'ͳ':
                            cell.setType(CellType.FLOOR);
                            new Gun(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Gold(cell);
                            break;
                        case 'm':
                            cell.setType(CellType.MERCHANT);
                            new Merchant(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static GameMap loadMap(int mapId, Player player) {
        InputStream is = MapLoader.class.getResourceAsStream("/map" + mapId + ".txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY, mapId);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case '2':
                            cell.setType(CellType.TREES);
                            break;
                        case 'v':
                            cell.setType(CellType.SAVE);
                            break;
                        case 'l':
                            cell.setType(CellType.LOAD);
                            break;
                        case 'w':
                            cell.setType(CellType.WC);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case '§':
                            cell.setType(CellType.FLOOR);
                            map.setGhost(new Ghost(cell));
                            break;
                        case 'n':
                            cell.setType(CellType.DOOR);
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Cat(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.FLOOR);
                            new Dragon(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(player);
                            player.setCell(cell);
                            cell.setActor(player);
                            break;
                        case '|':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new Health(cell);
                            break;
                        case 'ŀ':
                            cell.setType(CellType.FLOOR);
                            new MagicWand(cell);
                            break;
                        case 'ͳ':
                            cell.setType(CellType.FLOOR);
                            new Gun(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Gold(cell);
                            break;
                        case 'm':
                            cell.setType(CellType.MERCHANT);
                            new Merchant(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
}
