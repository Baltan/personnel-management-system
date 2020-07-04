package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseUtil {
    /**
     * 数据库连接驱动
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接数据库
     *
     * @return
     */
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/psm";
        String username = "root";
        String password = "123456";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭数据库操作部件
     *
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void DatabaseClose(ResultSet resultSet, Statement statement,
                                     Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}