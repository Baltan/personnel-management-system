package operation;

import lombok.Data;
import util.DatabaseUtil;
import window.LoginWindow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class UserOperation {
    private LoginWindow loginWindow;
    private String username;
    private String password;

    public UserOperation(LoginWindow loginWindow) {
        super();
        this.loginWindow = loginWindow;
    }

    /**
     * 判定输入的用户名和密码是否正确
     *
     * @return
     */
    public boolean isLoginInfoCorrect() {
        username = loginWindow.getJUsernameField().getText();
        password = String.valueOf(loginWindow.getJPasswordField().getPassword());
        Connection connection = DatabaseUtil.getConnection();
        String sql = "select * from user where username='" + username + "' and password='" + password + "'";
        Statement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                flag = true;
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.DatabaseClose(resultSet, statement, connection);
        }
        return flag;
    }
}