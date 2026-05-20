package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.service.SQLService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlayerTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(1, 1));

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

        gameMap.getCell(2, 1).setType(CellType.SAVE);
        player.move(1, 0);

        verify(mockSqlService, times(1)).save();

    }

}
