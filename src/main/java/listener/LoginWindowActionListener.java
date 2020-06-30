package listener;

import lombok.Data;
import personOperation.UserOperation;
import window.EditWindow;
import window.LoginWindow;
import window.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class LoginWindowActionListener implements ActionListener {
    private LoginWindow loginWindow;
    private MainWindow mainWindow;
    private EditWindow editWindow;
    private UserOperation userOperation;

    public LoginWindowActionListener(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("用户登录".equals(loginWindow.getJFrame().getTitle())) {
            userOperation = new UserOperation(loginWindow);
            boolean isUserInfoCorrect = userOperation.isLoginInfoCorrect();
            /**
             * 登录面板点击“登录”时，如果用户名和密码正确，关闭登录面板，打开主面板
             */
            if (isUserInfoCorrect && "登录".equals(command)) {
                mainWindow = new MainWindow();
                mainWindow.showMainWindow(true);
                loginWindow.showLoginWindow(false);
                /**
                 * 登录面板点击“登录”时，如果用户名和密码不正确，弹出提示框，并且清空输入
                 */
            } else if (!isUserInfoCorrect && "登录".equals(command)) {
                JOptionPane.showMessageDialog(null, "用户名或密码错误，请重新输入");
                loginWindow.getJUsernameField().setText("");
                loginWindow.getJPasswordField().setText("");
                /**
                 * 登录面板点击“游客登录”时，关闭登录面板，打开主面板，主面板只有更新、查询功能
                 */
            } else if ("游客登录".equals(command)) {
                mainWindow = new MainWindow();
                mainWindow.showMainWindow(true);
                mainWindow.getAddItem().setEnabled(false);
                mainWindow.getDeleteItem().setEnabled(false);
                mainWindow.getModifyItem().setEnabled(false);
                mainWindow.getImportItem().setEnabled(false);
                mainWindow.getExportItem().setEnabled(false);
                mainWindow.getAddJButton().setEnabled(false);
                mainWindow.getDeleteJButton().setEnabled(false);
                mainWindow.getModifyJButton().setEnabled(false);
                loginWindow.showLoginWindow(false);
                /**
                 * 登录面板点击“重置”时，清空输入
                 */
            } else if ("重置".equals(command)) {
                loginWindow.getJPasswordField().setText("");
                loginWindow.getJUsernameField().setText("");
            }
        }
    }
}