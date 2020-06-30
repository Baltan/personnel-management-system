package chat;

import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class ChatWindow {
    private Socket socket;
    private String name;
    private JTextField jTextField;
    private JTextArea jTextArea;
    private ClientThread clientThread;

    public ChatWindow(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        /**
         * 创建对话框
         */
        JFrame jFrame = new JFrame(name);
        jFrame.setLayout(new BorderLayout());
        Container container = jFrame.getContentPane();
        /**
         * 创建对话窗口信息显示框
         */
        jTextArea = new JTextArea(15, 15);
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        container.add(jScrollPane, BorderLayout.CENTER);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        /**
         * 创建对话窗口信息输入框
         */
        jTextField = new JTextField("", 8);
        JButton jButton = new JButton("发送");
        jPanel.add(jTextField);
        jPanel.add(jButton);
        container.add(jPanel, BorderLayout.SOUTH);

        ChatActionListener myActionListener = new ChatActionListener(this);
        /**
         * 为对话窗口发送键注册事件监听
         */
        jButton.addActionListener(myActionListener);

        jFrame.setVisible(true);
        jFrame.setSize(200, 250);
        jFrame.setLocation(300, 300);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setResizable(false);

        clientThread = new ClientThread(socket, this);
        clientThread.setName(name);
        clientThread.start();
    }
}