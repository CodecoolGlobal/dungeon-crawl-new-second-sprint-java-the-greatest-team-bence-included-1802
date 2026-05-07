package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.data.actors.*;
import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.items.Health;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.data.items.Key;
import com.codecool.dungeoncrawl.data.items.Sword;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadDAO {
    private final DataSource dataSource;

    public LoadDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void loadActors(GameMap gameMap) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM actors";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            Cell[][] cells = gameMap.getCells();
            while (rs.next()) {
                Actor actor = null;
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                switch (rs.getString("name")) {
                    case "player":
                        gameMap.setPlayer(new Player(cells[x][y]));
                        actor = gameMap.getPlayer();
                        break;
                    case "skeleton":
                        actor = new Skeleton(cells[x][y]);
                        break;
                    case "cat":
                        actor = new Cat(cells[x][y]);
                        break;
                    case "dragon":
                        actor = new Dragon(cells[x][y]);
                        break;

                }
                if (actor != null) {
                    cells[x][y].setActor(actor);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadItems(GameMap gameMap) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM items";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            Cell[][] cells = gameMap.getCells();
            while (rs.next()) {
                Item item = null;
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                switch (rs.getString("name")) {
                    case "health":
                        item = new Health(cells[x][y]);
                        break;
                    case "key":
                        item = new Key(cells[x][y]);
                        break;
                    case "sword":
                        item = new Sword(cells[x][y]);
                        break;
                }
                if (item != null) {
                    cells[x][y].setItem(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet loadInventoryItems() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM inventory_items";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
