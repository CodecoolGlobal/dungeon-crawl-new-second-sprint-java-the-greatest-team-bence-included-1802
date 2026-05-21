package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.sqlData.ActorData;
import com.codecool.dungeoncrawl.dao.sqlData.InventoryData;
import com.codecool.dungeoncrawl.dao.sqlData.ItemData;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDao {
    private final DataSource dataSource;

    public SQLDao() {
        try {
            dataSource = connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //SAVE

    public void saveActor(int mapId, String name, int x, int y, int health, int attackPower) {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO actors (mapId, name, x, y, health, attackpower) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, mapId);
            st.setString(2, name);
            st.setInt(3, x);
            st.setInt(4, y);
            st.setInt(5, health);
            st.setInt(6, attackPower);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Actor cannot be added to database.", e);
        }
    }

    public void saveItem(int mapId, String name, int x, int y) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO items (mapId, name, x, y) VALUES (?, ?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, mapId);
            st.setString(2, name);
            st.setInt(3, x);
            st.setInt(4, y);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }

    public void saveInventoryItem(int mapId, String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO inventory_items (mapId, name) VALUES (?, ?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, mapId);
            st.setString(2, name);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }

    //LOAD

    public List<ActorData> loadActors(int mapId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM actors WHERE mapId = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, mapId);
            ResultSet rs = st.executeQuery();

            List<ActorData> actors = new ArrayList<>();
            while (rs.next()) {
                actors.add(new ActorData(rs.getString("name"),
                        rs.getInt("x"),
                        rs.getInt("y"),
                        rs.getInt("health"),
                        rs.getInt("attackPower")));
            }
            return actors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ItemData> loadItems(int mapId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM items WHERE mapId = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, mapId);
            ResultSet rs = st.executeQuery();

            List<ItemData> items = new ArrayList<>();

            while (rs.next()) {
                items.add(new ItemData(rs.getString("name"),
                        rs.getInt("x"),
                        rs.getInt("y")));
            }

            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InventoryData> loadInventoryItems(int mapId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM inventory_items WHERE mapId = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, mapId);
            ResultSet rs = st.executeQuery();

            List<InventoryData> inventoryItems = new ArrayList<>();

            while (rs.next()) {
                inventoryItems.add(new InventoryData( rs.getString("name")));
            }

            return inventoryItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName("dungeon_crawl");
        dataSource.setUser(System.getenv("DB_USER"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }

    public void resetTables(int mapId) {
        try (Connection conn = dataSource.getConnection()) {
            String[] sqls = {
                    "DELETE FROM actors WHERE mapId = ?",
                    "DELETE FROM items WHERE mapId = ?",
                    "DELETE FROM inventory_items WHERE mapId = ?"
            };

            for (String sql : sqls) {
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.setInt(1, mapId);
                    st.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }
}
