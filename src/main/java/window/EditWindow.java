package window;

import listener.EditWindowActionListener;
import lombok.Data;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import person.Person;
import operation.PersonOperation;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class EditWindow {
    private MainWindow mainWindow;
    private EditWindowActionListener editWindowActionListener;
    private PersonOperation personOperation;
    private JFrame jFrame;
    private JPanel queryPanel;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JComboBox<String> sexCombobox;
    private JComboBox<String> departmentComboBox;
    private JComboBox<String> queryComboBox;
    private JTextField salaryTextField;

    public EditWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        editWindowActionListener = new EditWindowActionListener(this);
        personOperation = editWindowActionListener.getPersonOperation();
        jFrame = new JFrame();
        Container container = jFrame.getContentPane();
        container.setLayout(new GridLayout(7, 1));

        JPanel idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel sexPanel = new JPanel();
        sexPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel departmentPanel = new JPanel();
        departmentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel salaryPanel = new JPanel();
        salaryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        queryPanel = new JPanel();
        queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        queryPanel.setVisible(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        container.add(idPanel, 0);
        container.add(namePanel, 1);
        container.add(sexPanel, 2);
        container.add(departmentPanel, 3);
        container.add(salaryPanel, 4);
        container.add(queryPanel, 5);
        container.add(buttonPanel, 6);

        JLabel idLabel = new JLabel("    编号：");
        idTextField = new JTextField("", 15);
        idPanel.add(idLabel);
        idPanel.add(idTextField);

        JLabel nameLabel = new JLabel("    姓名：");
        nameTextField = new JTextField("", 15);
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);

        JLabel sexLabel = new JLabel("    性别：");
        String[] sexArray = {"female", "male"};
        sexCombobox = new JComboBox<>(sexArray);
        sexCombobox.setSize(new Dimension(800, 80));
        sexPanel.add(sexLabel);
        sexPanel.add(sexCombobox);

        JLabel departmentLabel = new JLabel("    部门：");
        String[] departmentArray = {"Sales", "Market", "Human Resource", "Accounting"};
        departmentComboBox = new JComboBox<>(departmentArray);
        departmentComboBox.setSize(new Dimension(800, 80));
        departmentPanel.add(departmentLabel);
        departmentPanel.add(departmentComboBox);

        JLabel salaryLabel = new JLabel("    薪资：");
        salaryTextField = new JTextField("", 15);
        salaryPanel.add(salaryLabel);
        salaryPanel.add(salaryTextField);

        JLabel queryLabel = new JLabel("    请选择查询条件：");
        String[] queryArray = {"and", "or"};
        queryComboBox = new JComboBox<>(queryArray);
        queryPanel.add(queryLabel);
        queryPanel.add(queryComboBox);

        JButton confirmButton = new JButton("确认");
        confirmButton.addActionListener(editWindowActionListener);
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(editWindowActionListener);
        buttonPanel.add(confirmButton, 0);
        buttonPanel.add(cancelButton, 1);

        jFrame.setSize(280, 330);
        jFrame.setLocation(300, 200);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setResizable(false);
        this.showEditWindow(false);
    }

    /**
     * 显示编辑面板
     *
     * @param editWindowFlag
     */
    public void showEditWindow(boolean editWindowFlag) {
        jFrame.setVisible(editWindowFlag);
    }

    /**
     * 清空编辑面板数据
     */
    public void clearEditWindow() {
        this.getIdTextField().setText("");
        this.getNameTextField().setText("");
        this.getSexCombobox().setSelectedIndex(-1);
        this.getDepartmentComboBox().setSelectedIndex(-1);
        this.getSalaryTextField().setText("");
    }

    /**
     * 在编辑面板上显示查询模块
     *
     * @param queryPanelFlag
     */
    public void showQueryPanel(boolean queryPanelFlag) {
        queryPanel.setVisible(queryPanelFlag);
    }

    /**
     * 设置主面板的窗口名
     *
     * @param windowTitle
     */
    public void setWindowTitle(String windowTitle) {
        jFrame.setTitle(windowTitle);
    }

    /**
     * 向主面板中添加编辑面板中的单条人员信息，同时添加到数据库中
     */
    public void jTableAddPerson() {
        String id = this.getIdTextField().getText().trim();
        String name = this.getNameTextField().getText().trim();
        String sex = String.valueOf(this.getSexCombobox().getSelectedItem()).trim();
        String department = String.valueOf(this.getDepartmentComboBox().getSelectedItem()).trim();
        String salary = this.getSalaryTextField().getText().trim();

        if (!id.matches("[1-9][0-9]{3,5}")) {
            JOptionPane.showMessageDialog(null, "输入编号不合法，编号应为1000-999999之间的数字");
            this.getIdTextField().setText("");
        } else if (!name.matches("[a-zA-Z]{3,}") && !name.matches("[\u4e00-\u9fa5]{2,}")) {
            JOptionPane.showMessageDialog(null, "输入姓名不合法，姓名应为至少3个英文字符或至少2个汉字");
            this.getNameTextField().setText("");
        } else if (!salary.matches("[1-9][0-9]*")) {
            JOptionPane.showMessageDialog(null, "输入薪资不合法，薪资应为正整数");
            this.getSalaryTextField().setText("");
        } else {
            Object[] personInfo = {id, name, sex, department, salary};
            Person person = new Person(Integer.valueOf(id), name, sex, department, Integer.valueOf(salary));
            mainWindow.getModel().addRow(personInfo);
            /**
             * 从编辑面板中向数据库添加单条人员信息
             */
            personOperation.addPerson(person);
            this.clearEditWindow();
            this.showEditWindow(false);
        }
    }

    /**
     * 将主面板中选中的人员信息删除，同时删除数据库中的对应信息
     */
    public void jTableDeletePerson() {
        int[] selectedRows = mainWindow.getJTable().getSelectedRows();
        if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int id = Integer.valueOf(
                        String.valueOf(mainWindow.getJTable().getValueAt(selectedRows[i], 0)).trim());
                mainWindow.getModel().removeRow(selectedRows[i]);
                /**
                 * 从数据库中删除单条人员信息
                 */
                personOperation.deletePerson(id);
            }
        }
    }

    /**
     * 将主面板中选中的一条人员信息显示到编辑面板中
     */
    public void showPersonInfo() {
        int selectedRow = mainWindow.getJTable().getSelectedRow();
        int id = Integer.valueOf(String.valueOf(mainWindow.getJTable().getValueAt(selectedRow, 0)));
        this.getIdTextField().setText(String.valueOf(id).trim());
        this.getNameTextField()
                .setText(String.valueOf(mainWindow.getJTable().getValueAt(selectedRow, 1)).trim());
        this.getSexCombobox()
                .setSelectedItem(String.valueOf(mainWindow.getJTable().getValueAt(selectedRow, 2)).trim());
        this.getDepartmentComboBox()
                .setSelectedItem(String.valueOf(mainWindow.getJTable().getValueAt(selectedRow, 3)).trim());
        this.getSalaryTextField()
                .setText(String.valueOf(mainWindow.getJTable().getValueAt(selectedRow, 4)).trim());
    }

    /**
     * 将编辑面板中更改的单条人员信息添加到主面板中，同时更改数据库中的对应人员信息
     */
    public void jTableModifyPerson() {
        int selectedRow = mainWindow.getJTable().getSelectedRow();
        String oldId = String.valueOf(mainWindow.getJTable().getValueAt(selectedRow, 0)).trim();
        String id = this.getIdTextField().getText().trim();
        String name = this.getNameTextField().getText().trim();
        String sex = String.valueOf(this.getSexCombobox().getSelectedItem()).trim();
        String department = String.valueOf(this.getDepartmentComboBox().getSelectedItem()).trim();
        String salary = this.getSalaryTextField().getText().trim();

        if (!id.matches("[1-9][0-9]{3,5}")) {
            JOptionPane.showMessageDialog(null, "输入编号不合法，编号应为1000-999999之间的数字");
            this.getIdTextField().setText(oldId);
        } else if (!name.matches("[a-zA-Z]{3,}") && !name.matches("[\u4e00-\u9fa5]{2,}")) {
            JOptionPane.showMessageDialog(null, "输入姓名不合法，姓名应为至少3个英文字符或至少2个汉子");
            this.getNameTextField().setText("");
        } else if (!salary.matches("[1-9][0-9]*")) {
            JOptionPane.showMessageDialog(null, "输入薪资不合法，薪资应为正整数");
            this.getSalaryTextField().setText("");
        } else {
            Object[] personInfo = {id, name, sex, department, salary};
            Person person = new Person(Integer.valueOf(id), name, sex, department, Integer.valueOf(salary));
            mainWindow.getModel().removeRow(selectedRow);
            mainWindow.getModel().addRow(personInfo);
            /**
             * 将编辑面板中更改的单条人员信息同步到数据库中
             */
            personOperation.modifyPerson(Integer.valueOf(oldId), person);
            this.clearEditWindow();
            this.showEditWindow(false);
        }
    }

    /**
     * 将数据库中的所有人员信息显示到主面板中
     */
    public void jTableRenewPerson() {
        mainWindow.getModel().setRowCount(0);
        /**
         * 从数据库中查询获取所有人员信息
         */
        List<Object[]> objectsArrayList = personOperation.queryPersonAll();

        for (int i = 0; i < objectsArrayList.size(); i++) {
            mainWindow.getModel().addRow(objectsArrayList.get(i));
        }
    }

    /**
     * 从主面板中模糊查询符合编辑面板中查询条件的人员信息
     */
    public void jTableQueryPerson() {
        ArrayList<String> queryLabel = new ArrayList<>();
        ArrayList<String> queryContent = new ArrayList<>();
        String id = this.getIdTextField().getText().trim();
        String name = this.getNameTextField().getText().trim();
        String sex = String.valueOf(this.getSexCombobox().getSelectedItem()).trim();
        String department = String.valueOf(this.getDepartmentComboBox().getSelectedItem()).trim();
        String salary = this.getSalaryTextField().getText().trim();
        String option = String.valueOf(this.getQueryComboBox().getSelectedItem()).trim();

        if (id.length() > 0) {
            queryLabel.add("id");
            queryContent.add(id);
        }

        if (name.length() > 0) {
            queryLabel.add("name");
            queryContent.add(name);
        }

        if (!"null".equals(sex)) {
            queryLabel.add("sex");
            queryContent.add(sex);
        }

        if (!"null".equals(department)) {
            queryLabel.add("department");
            queryContent.add(department);
        }

        if (salary.length() > 0) {
            queryLabel.add("salary");
            queryContent.add(salary);
        }
        mainWindow.getModel().setRowCount(0);
        /**
         * 从数据库中模糊查询符合查询条件的所有人员信息
         */
        List<Object[]> objectsArrayList = personOperation.queryPerson(queryLabel, queryContent, option);
        for (int i = 0; i < objectsArrayList.size(); i++) {
            mainWindow.getModel().addRow(objectsArrayList.get(i));
        }
        this.clearEditWindow();
        this.showEditWindow(false);
    }

    /**
     * 从主面板中精确查询符合编辑面板中查询条件的人员信息
     */
    public void jTableExactQueryPerson() {
        ArrayList<String> queryLabel = new ArrayList<>();
        ArrayList<String> queryContent = new ArrayList<>();
        String id = this.getIdTextField().getText().trim();
        String name = this.getNameTextField().getText().trim();
        String sex = String.valueOf(this.getSexCombobox().getSelectedItem()).trim();
        String department = String.valueOf(this.getDepartmentComboBox().getSelectedItem()).trim();
        String salary = this.getSalaryTextField().getText().trim();
        String option = String.valueOf(this.getQueryComboBox().getSelectedItem()).trim();

        if (id.length() > 0) {
            queryLabel.add("id");
            queryContent.add(id);
        }

        if (name.length() > 0) {
            queryLabel.add("name");
            queryContent.add(name);
        }

        if (!"null".equals(sex)) {
            queryLabel.add("sex");
            queryContent.add(sex);
        }

        if (!"null".equals(department)) {
            queryLabel.add("department");
            queryContent.add(department);
        }

        if (salary.length() > 0) {
            queryLabel.add("salary");
            queryContent.add(salary);
        }
        mainWindow.getModel().setRowCount(0);
        /**
         * 从数据库中精确查询符合查询条件的所有人员信息
         */
        List<Object[]> objectsArrayList = personOperation.exactQueryPerson(queryLabel, queryContent, option);
        for (int i = 0; i < objectsArrayList.size(); i++) {
            mainWindow.getModel().addRow(objectsArrayList.get(i));
        }
        this.clearEditWindow();
        this.showEditWindow(false);
    }

    /**
     * 导入XML文件中的所有人员信息
     *
     * @param file
     * @return
     */
    public List<Person> importFile(File file) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        List<Person> personArrayList = new ArrayList<>();

        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element staff = document.getRootElement();
        List<Element> staffList = staff.elements();

        for (int i = 0; i < staffList.size(); i++) {
            Element staffInfo = staffList.get(i);
            List<Element> idList = staffInfo.elements("id");
            String id = idList.get(0).getText().trim();
            List<Element> nameList = staffInfo.elements("name");
            String name = nameList.get(0).getText().trim();
            List<Element> sexList = staffInfo.elements("sex");
            String sex = sexList.get(0).getText().trim();
            List<Element> departmentList = staffInfo.elements("department");
            String department = departmentList.get(0).getText().trim();
            List<Element> salaryList = staffInfo.elements("salary");
            String salary = salaryList.get(0).getText().trim();
            Object[] personInfo = {id, name, sex, department, salary};
            Person person = new Person(Integer.valueOf(id), name, sex, department, Integer.valueOf(salary));
            personArrayList.add(person);
            mainWindow.getModel().addRow(personInfo);
        }
        return personArrayList;
    }

    /**
     * 将数据库中查询到的所有人员信息导出到XML文件中
     *
     * @param file
     */
    public void exportFile(File file) {
        Document document = DocumentHelper.createDocument();
        Element staff = document.addElement("staff");
        /**
         * 从数据库中查询获取所有人员信息
         */
        List<Object[]> objectsArrayList = personOperation.queryPersonAll();

        for (int i = 0; i < objectsArrayList.size(); i++) {
            Object[] personInfo = objectsArrayList.get(i);
            Element staffInfo = staff.addElement("staffInfo");
            Element id = staffInfo.addElement("id");
            Element name = staffInfo.addElement("name");
            Element sex = staffInfo.addElement("sex");
            Element department = staffInfo.addElement("department");
            Element salary = staffInfo.addElement("salary");
            id.setText(String.valueOf(personInfo[0]));
            name.setText(String.valueOf(personInfo[1]));
            sex.setText(String.valueOf(personInfo[2]));
            department.setText(String.valueOf(personInfo[3]));
            salary.setText(String.valueOf(personInfo[4]));
        }
        BufferedWriter bufferedWriter;

        try {
            bufferedWriter =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf-8"));
            document.write(bufferedWriter);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}