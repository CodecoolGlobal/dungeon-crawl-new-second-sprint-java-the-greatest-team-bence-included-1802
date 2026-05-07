package com.codecool.dungeoncrawl.dao;

import javax.sql.DataSource;
import java.sql.*;

public class SaveDAO {
    private final DataSource dataSource;

    public SaveDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void resetTables() {
        try (Connection conn = dataSource.getConnection()) {
            String[] sqls = {
                    "DELETE FROM actors",
                    "DELETE FROM items",
                    "DELETE FROM inventory_items"
            };

            for (String sql : sqls) {
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }

    public void saveActor(String name, int x, int y, int health, int attackPower) {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO actors (name, x, y, health, attackpower) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setInt(2, x);
            st.setInt(3, y);
            st.setInt(4, health);
            st.setInt(5, attackPower);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Actor cannot be added to database.", e);
        }
    }

    public void saveItem(String name, int x, int y) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO items (name, x, y) VALUES (?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setInt(2, x);
            st.setInt(3, y);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }

    public void saveInventoryItem(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO inventory_items (name) VALUES (?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }
}
