package listener;

import lombok.Data;
import personOperation.PersonOperation;
import window.EditWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class EditWindowActionListener implements ActionListener {
    private EditWindow editWindow;
    private PersonOperation personOperation;

    public EditWindowActionListener(EditWindow editWindow) {
        this.editWindow = editWindow;
        personOperation = new PersonOperation(this.editWindow);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("增加".equals(editWindow.getJFrame().getTitle())) {
            /**
             * 增加编辑面板点击“确定”时，向主面板和数据库增加人员记录
             */
            if ("确认".equals(command)) {
                editWindow.jTableAddPerson();
                /**
                 * 增加编辑面板点击“取消”时，关闭编辑面板
                 */
            } else if ("取消".equals(command)) {
                editWindow.showEditWindow(false);
            }
        } else if ("修改".equals(editWindow.getJFrame().getTitle())) {
            /**
             * 修改编辑面板点击“确定”时，将主面板选中的人员信息修改，同时修改数据库对应人员记录
             */
            if ("确认".equals(command)) {
                editWindow.jTableModifyPerson();
                /**
                 * 修改编辑面板点击“取消”时，关闭编辑面板
                 */
            } else if ("取消".equals(command)) {
                editWindow.showEditWindow(false);
            }
        } else if ("模糊查询".equals(editWindow.getJFrame().getTitle())) {
            /**
             * 模糊查询编辑面板点击“确定”时，将数据库中查询到的匹配记录显示在主面板中
             */
            if ("确认".equals(command)) {
                editWindow.jTableQueryPerson();
                /**
                 * 模糊查询编辑面板点击“取消”时，关闭编辑面板
                 */
            } else if ("取消".equals(command)) {
                editWindow.showEditWindow(false);
            }
        } else if ("精确查询".equals(editWindow.getJFrame().getTitle())) {
            /**
             * 精确查询编辑面板点击“确定”时，将数据库中查询到的匹配记录显示在主面板中
             */
            if ("确认".equals(command)) {
                editWindow.jTableExactQueryPerson();
                /**
                 * 精确查询编辑面板点击“取消”时，关闭编辑面板
                 */
            } else if ("取消".equals(command)) {
                editWindow.showEditWindow(false);
            }
        }
    }
}