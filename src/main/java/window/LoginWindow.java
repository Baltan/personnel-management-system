package window;

import listener.LoginWindowActionListener;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class LoginWindow {
    private LoginWindowActionListener loginWindowActionListener;
    private JFrame jFrame;
    private JTextField jUsernameField;
    private JPasswordField jPasswordField;

    public LoginWindow() {
        loginWindowActionListener = new LoginWindowActionListener(this);
        jFrame = new JFrame("用户登录");
        Container container = jFrame.getContentPane();

        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JLabel usernameLabel = new JLabel("用户名");
        jUsernameField = new JTextField("", 10);
        JLabel passwordLabel = new JLabel("密   码");
        jPasswordField = new JPasswordField("", 10);
        jPasswordField.setEchoChar('•');
        jPanel1.add(usernameLabel);
        jPanel1.add(jUsernameField);
        jPanel2.add(passwordLabel);
        jPanel2.add(jPasswordField);
        container.add(jPanel1);
        container.add(jPanel2);

        JPanel jPanel3 = new JPanel();
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(loginWindowActionListener);
        JButton guestLoginButton = new JButton("游客登录");
        guestLoginButton.addActionListener(loginWindowActionListener);
        JButton resetButton = new JButton("重置");
        resetButton.addActionListener(loginWindowActionListener);
        jPanel3.add(loginButton);
        jPanel3.add(guestLoginButton);
        jPanel3.add(resetButton);
        container.add(jPanel3);

        jFrame.setVisible(true);
        jFrame.setSize(270, 150);
        jFrame.setLocation(500, 300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        container.setLayout(new FlowLayout());
    }

    public void showLoginWindow(boolean loginWindowFlag) {
        jFrame.setVisible(loginWindowFlag);
    }
}