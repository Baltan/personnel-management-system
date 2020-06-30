package listener;

import chat.Client;
import lombok.Data;
import person.Person;
import window.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class MainWindowActionListener implements ActionListener {
    private MainWindow mainWindow;

    public MainWindowActionListener(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("人事管理系统".equals(mainWindow.getJFrame().getTitle())) {
            /**
             * 主面板点击“增加”时，显示增加编辑面板
             */
            if ("增加".equals(command)) {
                mainWindow.getEditWindow().showEditWindow(true);
                mainWindow.getEditWindow().setWindowTitle("增加");
                mainWindow.getEditWindow().clearEditWindow();
                mainWindow.getEditWindow().showQueryPanel(false);
            } else if ("修改".equals(command)) {
                /**
                 * 主面板点击“修改”时，如果已选择一条记录，显示增加修改面板
                 */
                if (mainWindow.getJTable().getSelectedRow() >= 0) {
                    mainWindow.getEditWindow().showEditWindow(true);
                    mainWindow.getEditWindow().setWindowTitle("修改");
                    mainWindow.getEditWindow().showPersonInfo();
                    mainWindow.getEditWindow().showQueryPanel(false);
                } else {
                    /**
                     * 主面板点击“修改”时，如果没有选择的记录，弹出提示框
                     */
                    JOptionPane.showMessageDialog(null, "请选择一条数据");
                }
                /**
                 * 主面板点击“模糊查询”时，显示模糊查询编辑面板
                 */
            } else if ("模糊查询".equals(command)) {
                mainWindow.getEditWindow().showEditWindow(true);
                mainWindow.getEditWindow().setWindowTitle("模糊查询");
                mainWindow.getEditWindow().clearEditWindow();
                mainWindow.getEditWindow().showQueryPanel(true);
                /**
                 * 主面板点击“精确查询”时，显示精确查询编辑面板
                 */
            } else if ("精确查询".equals(command)) {
                mainWindow.getEditWindow().showEditWindow(true);
                mainWindow.getEditWindow().setWindowTitle("精确查询");
                mainWindow.getEditWindow().clearEditWindow();
                mainWindow.getEditWindow().showQueryPanel(true);
                /**
                 * 主面板点击“更新”时，将数据库中所有的人员记录显示在主面板上
                 */
            } else if ("更新".equals(command)) {
                mainWindow.getEditWindow().jTableRenewPerson();
            } else if ("删除".equals(command)) {
                /**
                 * 主面板点击“删除”时，如果已选择一条记录，弹出确认框
                 */
                if (mainWindow.getJTable().getSelectedRow() >= 0) {
                    int deleteConfirm = JOptionPane.showConfirmDialog(null, "确认删除记录？", "确认框",
                            JOptionPane.YES_NO_OPTION);
                    /**
                     * 删除面板确认框点击“是”时，在主面板上删除选中的人员记录，并在数据库中删除对应的人员记录
                     */
                    if (deleteConfirm == 0) {
                        mainWindow.getEditWindow().jTableDeletePerson();
                    }
                    /**
                     * 主面板点击“删除”时，如果没有选择的记录，弹出提示框
                     */
                } else {
                    JOptionPane.showMessageDialog(null, "请选择一条数据");
                }
                /**
                 * 主面板点击“导入”时，弹出文件选择对话框
                 */
            } else if ("导入".equals(command)) {
                JFileChooser jFileChooser = new JFileChooser("");
                int selection = jFileChooser.showOpenDialog(null);
                /**
                 * 文件选择对话框点击“打开”时，将XML文件中人员记录添加到主面板中，并且弹出是否导入数据库确认框
                 */
                if (selection == 0) {
                    /**
                     * 获取选中文件
                     */
                    File file = jFileChooser.getSelectedFile();
                    List<Person> personArrayList = mainWindow.getEditWindow().importFile(file);
                    int importConfirm = JOptionPane.showConfirmDialog(null, "是否将数据添加到数据库中？", "确认框",
                            JOptionPane.YES_NO_OPTION);
                    /**
                     * 是否导入数据库确认框点击“是”时，将主面板上所有人员记录添加到数据库中
                     */
                    if (importConfirm == 0) {
                        mainWindow.getEditWindow().getPersonOperation().addPersonAll(personArrayList);
                    }
                }
                /**
                 * 主面板点击“导出”时，弹出文件选择对话框
                 */
            } else if ("导出".equals(command)) {
                JFileChooser jFileChooser = new JFileChooser("");
                int selection = jFileChooser.showSaveDialog(null);
                /**
                 * 文件选择对话框点击“打开”时，将主面板中人员记录导出到XML文件
                 */
                if (selection == 0) {
                    /**
                     * 获取选中文件
                     */
                    File file = jFileChooser.getSelectedFile();
                    mainWindow.getEditWindow().exportFile(file);
                }
                /**
                 * 主面板点击“问题讨论”时，弹出聊天登录窗口
                 */
            } else if ("问题讨论".equals(command)) {
                new Client();
                /**
                 * 主面板点击“退出”时，关闭人事管理系统
                 */
            } else if ("退出".equals(command)) {
                System.exit(0);
            }
        }
    }
}