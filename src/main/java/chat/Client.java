package chat;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Description: 客户端类
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
public class Client {
    public Client() {
        Socket socket = null;

        try {
            /**
             * 获得客户端Socket对象，客户端连接到端口号为9527的本机服务端
             */
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9527);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 创建登录窗口
         */
        String name = JOptionPane.showInputDialog("请输入用户名");
        new ChatWindow(socket, name);
    }
}