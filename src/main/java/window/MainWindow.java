package window;

import listener.MainWindowActionListener;
import listener.MyMouseListener;
import lombok.Data;
import person.Person;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class MainWindow {
    private MainWindowActionListener mainWindowActionListener;
    private MyMouseListener myMouseListener;
    private EditWindow editWindow;
    private JFrame jFrame;
    private DefaultTableModel model;
    private JTable jTable;
    private JButton addJButton;
    private JButton deleteJButton;
    private JButton modifyJButton;
    private JMenuItem addItem;
    private JMenuItem deleteItem;
    private JMenuItem modifyItem;
    private JMenuItem importItem;
    private JMenuItem exportItem;

    public MainWindow() {
        editWindow = new EditWindow(this);
        mainWindowActionListener = new MainWindowActionListener(this);
        myMouseListener = new MyMouseListener(this);

        jFrame = new JFrame("人事管理系统");
        Container container = jFrame.getContentPane();

        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileJMenu = new JMenu("File");
        JMenu helpJMenu = new JMenu("Help");
        addItem = new JMenuItem("增加");
        addItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(addItem);
        deleteItem = new JMenuItem("删除");
        deleteItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(deleteItem);
        modifyItem = new JMenuItem("修改");
        modifyItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(modifyItem);
        JMenuItem renewItem = new JMenuItem("更新");
        renewItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(renewItem);
        JMenuItem queryItem = new JMenuItem("模糊查询");
        queryItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(queryItem);
        JMenuItem exactQueryItem = new JMenuItem("精确查询");
        exactQueryItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(exactQueryItem);
        fileJMenu.addSeparator();
        JMenuItem quitItem = new JMenuItem("退出");
        quitItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(quitItem);
        importItem = new JMenuItem("导入");
        importItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(importItem);
        exportItem = new JMenuItem("导出");
        exportItem.addActionListener(mainWindowActionListener);
        fileJMenu.add(exportItem);
        JMenuItem discussionItem = new JMenuItem("问题讨论");
        discussionItem.addActionListener(mainWindowActionListener);
        helpJMenu.add(discussionItem);
        jMenuBar.add(fileJMenu);
        jMenuBar.add(helpJMenu);

        JLabel jLabel = new JLabel("人事管理系统");
        /**
         * 修改表名字体
         */
        jLabel.setFont(new Font("宋体", Font.BOLD, 15));

        Object[] caption = {"编号", "名字", "性别", "部门", "薪资"};
        model = new DefaultTableModel(caption, 0);
        jTable = new JTable(model);
        jTable.getTableHeader().addMouseListener(myMouseListener);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        /**
         * 设置表格尺寸
         */
        jScrollPane.setPreferredSize(new Dimension(660, 300));
        /**
         * 设置表头数据居中显示
         */
        DefaultTableCellHeaderRenderer tchr = new DefaultTableCellHeaderRenderer();
        tchr.setHorizontalAlignment(SwingConstants.CENTER);
        jTable.getTableHeader().setDefaultRenderer(tchr);
        /**
         * 设置表中数据居中显示
         */
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        jTable.setDefaultRenderer(Object.class, tcr);

        JPanel buttonPanel = new JPanel();
        addJButton = new JButton("增加");
        addJButton.addActionListener(mainWindowActionListener);
        deleteJButton = new JButton("删除");
        deleteJButton.addActionListener(mainWindowActionListener);
        modifyJButton = new JButton("修改");
        modifyJButton.addActionListener(mainWindowActionListener);
        JButton renewJButton = new JButton("更新");
        renewJButton.addActionListener(mainWindowActionListener);
        JButton queryJButton = new JButton("模糊查询");
        queryJButton.addActionListener(mainWindowActionListener);
        JButton exactQueryJButton = new JButton("精确查询");
        exactQueryJButton.addActionListener(mainWindowActionListener);
        /**
         * 设置按键字字体
         */
        addJButton.setFont(new Font("黑体", Font.BOLD, 12));
        deleteJButton.setFont(new Font("黑体", Font.BOLD, 12));
        modifyJButton.setFont(new Font("黑体", Font.BOLD, 12));
        renewJButton.setFont(new Font("黑体", Font.BOLD, 12));
        queryJButton.setFont(new Font("黑体", Font.BOLD, 12));
        exactQueryJButton.setFont(new Font("黑体", Font.BOLD, 12));
        buttonPanel.add(addJButton);
        buttonPanel.add(deleteJButton);
        buttonPanel.add(modifyJButton);
        buttonPanel.add(renewJButton);
        buttonPanel.add(queryJButton);
        buttonPanel.add(exactQueryJButton);

        this.showMainWindow(false);
        jFrame.setSize(680, 420);
        jFrame.setLocation(300, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        container.setLayout(new FlowLayout());
        /**
         * 设置顶层框架的菜单栏
         */
        jFrame.setJMenuBar(jMenuBar);
        container.add(jLabel);
        container.add(jScrollPane);
        container.add(buttonPanel);
    }

    /**
     * 显示主面板
     *
     * @param mainWindowFlag
     */
    public void showMainWindow(boolean mainWindowFlag) {
        jFrame.setVisible(mainWindowFlag);
    }

    /**
     * 获取主面板中的所有人员信息
     *
     * @return
     */
    public List<Person> getPersonInfoAll() {
        List<Person> personArrayList = new ArrayList<>();

        int rowsAll = this.getModel().getRowCount();

        for (int i = 0; i < rowsAll; i++) {
            int id = Integer.valueOf(String.valueOf(jTable.getValueAt(i, 0)).trim());
            String name = String.valueOf(jTable.getValueAt(i, 1)).trim();
            String sex = String.valueOf(jTable.getValueAt(i, 2)).trim();
            String department = String.valueOf(jTable.getValueAt(i, 3)).trim();
            int salary = Integer.valueOf(String.valueOf(jTable.getValueAt(i, 4)).trim());
            Person person = new Person(id, name, sex, department, salary);
            personArrayList.add(person);
        }
        return personArrayList;
    }

    /**
     * 将数组中所有的人员信息显示到主面板中
     *
     * @param personArrayList
     */
    public void showPersonInfoAll(List<Person> personArrayList) {
        this.getModel().setRowCount(0);

        for (int i = 0; i < personArrayList.size(); i++) {
            Person person = personArrayList.get(i);

            Object[] personInfo = {person.getId(), person.getName(), person.getSex(), person.getDepartment(),
                    person.getSalary()};
            this.getModel().addRow(personInfo);
        }
    }
}