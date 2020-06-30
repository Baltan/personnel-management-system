package chat;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Description: 创建客户端多线程类
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
class ClientThread extends Thread {
    private Socket socket;
    private ChatWindow chatWindow;

    public ClientThread(Socket socket, ChatWindow chatWindow) {
        super();
        this.socket = socket;
        this.chatWindow = chatWindow;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            /**
             * 当缓冲流读取到新信息时，在对话窗口显示信息
             */
            while (true) {
                String msg = bufferedReader.readLine();
                if (msg != null) {
                    chatWindow.getJTextArea().setText(chatWindow.getJTextArea().getText() + "\n" + msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}