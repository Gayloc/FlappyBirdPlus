package com.gayloc.flappyBirdPlusServer;

import java.sql.*;
import java.util.ArrayList;

public class Controller implements Server {

    Connection conn = null;

    public Controller() {
        start();
    }

    @Override
    public void start() {
        try {
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:identifier.sqlite";
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            if (conn != null) {
                System.out.println("Closing connection.");
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addScore(User user) {
        String sql = "INSERT OR REPLACE INTO USER(NAME, SCORE) VALUES(?,?)";
        boolean result = false;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getScore());
            stmt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public User[] getTopUsers() {
        String sql = "SELECT NAME, SCORE FROM USER ORDER BY SCORE DESC LIMIT 10";
        ArrayList<User> topUsers = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("NAME");
                int score = rs.getInt("SCORE");
                topUsers.add(new User(name, score));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        /*
        * 类型推断：传递一个类型化的数组（例如 new User[0]）允许 Java 知道所需的数组类型。在这种情况下，Java 将知道你希望返回一个 User 类型的数组。
        * 性能优化：如果传递的数组足够大以容纳列表中的所有元素，toArray 方法会使用传入的数组。如果传递的数组太小（例如 new User[0]），toArray 方法将创建一个新数组并返回它。这种方式避免了类型转换问题，并提高了性能，因为不需要在运行时检查和转换类型。
        */
        return topUsers.toArray(new User[0]);
    }

    @Override
    public int getUserScore(String userName) {
        String sql = "SELECT SCORE FROM USER WHERE NAME = ?";
        int result = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt("SCORE");
                    System.out.println("User " + userName + " has score " + result);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public boolean removeScore(String userName) {
        String sql = "DELETE FROM USER WHERE NAME = ?";
        boolean result = false;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User " + userName + " deleted successfully.");
                result = true;
            } else {
                System.out.println("User " + userName + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}
