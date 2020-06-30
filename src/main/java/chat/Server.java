package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 创建服务器端类
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
public class Server {
    public static void main(String[] args) throws IOException {
        /**
         * 获得服务器端的ServerSocket对象，开放的端口号为9527
         */
        ServerSocket serverSocket = new ServerSocket(9527);
        List<Socket> sArrayList = new ArrayList<>();

        while (true) {
            System.out.println("等待客户机连接……");
            /**
             * 当获取到连接到服务器端的客户机时，获得客户机的Socket对象
             */
            Socket socket = serverSocket.accept();
            /**
             * 获得客户机IP地址
             */
            String ip = socket.getInetAddress().getHostAddress();
            /**
             * 获得客户机端口号
             */
            int port = socket.getPort();
            System.out.println("IP地址为：" + ip + "，端口号为：" + port + "的客户机已连接到服务机");
            sArrayList.add(socket);
            ServerThread serverThread = new ServerThread(sArrayList, socket);
            serverThread.start();
        }
    }
}