/**
* @Description 服务端控制器
* @Author 古佳乐
* @Date 2024/6/15-下午1:13
*/

package com.gayloc.flappyBirdPlusServer;

import java.sql.*;
import java.util.ArrayList;

/**
 * {@code @Description} 服务端控制器
 * {@code @Author} 古佳乐
 * {@code @Date} 2024/6/15-下午1:13
 */
public class Controller implements Server {

    //数据库连接
    Connection conn = null;

    /**
    * {@code @Description} 控制器构造函数，启动数据库连接并检查表
    * {@code @Author} 古佳乐
    * {@code @Param} void
    * {@code @Date} 2024/6/15-下午1:13
     **/
    public Controller() {
        start();
        String createTableSql = "CREATE TABLE IF NOT EXISTS USER ("
                + "NAME TEXT PRIMARY KEY, "
                + "SCORE INTEGER"
                + ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
    * {@code @Description} 启动数据库连接
    * {@code @Author} 古佳乐
    * {@code @Param} void
    * {@code @return} void
    * {@code @Date} 2024/6/15-下午1:13
    */
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

    /**
    * {@code @Description} 终止数据库连接
    * {@code @Author} 古佳乐
    * {@code @Param} void
    * {@code @return} void
    * {@code @Date} 2024/6/15-下午1:13
    */
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

    /**
    * {@code @Description} 向数据库中添加分数信息 
    * {@code @Param} User user
    * {@code @return} boolean 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午10:14
    */
    @Override
    public boolean addScore(User user) {
        // 储存返回结果
        boolean result = false;

        // 三种不同操作对应的 sql 语句
        String selectSql = "SELECT COUNT(*) FROM USER WHERE NAME = ?";
        String updateSql = "UPDATE USER SET SCORE = ? WHERE NAME = ?";
        String insertSql = "INSERT INTO USER(NAME, SCORE) VALUES(?, ?)";

        // 使用 PreparedStatement
        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
            selectStmt.setString(1, user.getName());
            ResultSet rs = selectStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            // 如果数据库已经存在该用户的分数记录
            if (count > 0) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, user.getScore());
                    updateStmt.setString(2, user.getName());
                    updateStmt.executeUpdate();
                    result = true;
                }
                // 如果不存在
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, user.getName());
                    insertStmt.setInt(2, user.getScore());
                    insertStmt.executeUpdate();
                    result = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
    * {@code @Description} 获取排行榜前十名的信息
    * {@code @return}  User[]
    * {@code @Param} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    @Override
    public User[] getTopUsers() {
        String sql = "SELECT NAME, SCORE FROM USER ORDER BY SCORE DESC LIMIT 10";
        ArrayList<User> topUsers = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // 遍历数据库返回的结果
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

    /**
    * {@code @Description} 获取一个用户的分数 
    * {@code @Param} String userName
    * {@code @return} int
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
    @Override
    public int getUserScore(String userName) {
        String sql = "SELECT SCORE FROM USER WHERE NAME = ?";
        int result = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                //理论上只有一个查询结果，为了健壮性使用遍历，有重复名字的以最后一个为准
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

    /**
    * {@code @Description} 删除一个用户的分数信息
    * {@code @Param} String userName
    * {@code @return} boolean
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午1:13
    */
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
