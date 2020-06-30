package chat;

import lombok.Data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class ChatActionListener implements ActionListener {
    private ChatWindow chatWindow;

    public ChatActionListener(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        /**
         * 当单击“发送”键时，将输入的内容加入到打印流中输出，并且清空对话窗口输入框
         */
        if ("发送".equals(command)) {
            try {
                PrintWriter printWriter = new PrintWriter(chatWindow.getSocket().getOutputStream());
                String msg =
                        chatWindow.getClientThread().getName() + ":" + chatWindow.getJTextField().getText();
                printWriter.println(msg);
                printWriter.flush();
                chatWindow.getJTextField().setText("");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}