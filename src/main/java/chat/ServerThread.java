package chat;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Description: 创建服务器端多线程类
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
class ServerThread extends Thread {
    private List<Socket> sArrayList;
    private Socket socket;

    public ServerThread(List<Socket> sArrayList, Socket socket) {
        super();
        this.sArrayList = sArrayList;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /**
             * 当服务器端收到某个客户端发来的信息时，把他转发到所有的客户端
             */
            while (true) {
                String msg = bufferedReader.readLine();
                for (Socket socket1 : sArrayList) {
                    PrintWriter printWriter = new PrintWriter(socket1.getOutputStream());
                    printWriter.println(msg);
                    printWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}