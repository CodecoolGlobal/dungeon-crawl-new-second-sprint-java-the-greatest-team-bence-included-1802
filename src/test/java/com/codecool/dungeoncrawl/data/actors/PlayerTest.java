package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.items.Health;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.data.items.Sword;
import com.codecool.dungeoncrawl.service.SQLService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlayerTest {
    GameMap gameMap = new GameMap(4, 4, CellType.FLOOR, 1);
    Player player = new Player(gameMap.getCell(1, 1), new SQLService(gameMap));

    @BeforeAll
    static void initJavaFX() {
        javafx.application.Platform.startup(() -> {});
    }

    @Test
    void getPlayerTileName() {
        String result = player.getTileName();
        assertEquals("player", result);
    }

    @Test
    void cannotMoveIntoTree() {
        gameMap.getCell(2, 1).setType(CellType.TREE);
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoTrees() {
        gameMap.getCell(2, 1).setType(CellType.TREES);
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoEmptyCell() {
        gameMap.getCell(2, 1).setType(CellType.EMPTY);
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void canMoveToFloor() {
        gameMap.getCell(2, 1).setType(CellType.FLOOR);
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void canSaveTheGame() {
        SQLService mockSqlService = mock(SQLService.class);
        Player player = new Player(gameMap.getCell(1, 1), mockSqlService);
        gameMap.getCell(2, 1).setType(CellType.SAVE);

        player.move(1, 0);

        verify(mockSqlService, times(1)).save();
    }

    @Test
    void canLoadPreviousGame() {
        SQLService mockSqlService = mock(SQLService.class);
        Player player = new Player(gameMap.getCell(1, 1), mockSqlService);
        gameMap.getCell(2, 1).setType(CellType.LOAD);

        player.move(1, 0);

        verify(mockSqlService, times(1)).load();
    }

    @Test
    void pickUpHealthPotionIncreasesPlayerHealth() {
        Item healthPotion = new Health(gameMap.getCell(2,1));
        gameMap.getCell(2, 1).setItem(healthPotion);
        player.move(1, 0);

        assertEquals(15, player.getHealth());
    }

    @Test
    void pickUpSwordIncreasesAttackPower() {
        Item sword = new Sword(gameMap.getCell(2,1));
        gameMap.getCell(2, 1).setItem(sword);
        player.move(1, 0);

        assertEquals(10, player.getAttackPower());
    }

    @Test
    void pickUpSwordThenFlushInWC() {
        Item sword = new Sword(gameMap.getCell(2,1));
        gameMap.getCell(2, 1).setItem(sword);
        player.move(1, 0);

        gameMap.getCell(3,1).setType(CellType.WC);
        player.move(1, 0);

        assertEquals(5, player.getAttackPower());
    }

}
