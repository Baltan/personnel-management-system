package listener;

import lombok.Data;
import person.Person;
import window.MainWindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class MyMouseListener extends MouseAdapter implements MouseListener {
    private MainWindow mainWindow;
    private boolean idFlag, nameFlag, sexFlag, departmentFlag, salaryFlag = false;
    private PersonComparator idAscend = new PersonComparator(true, "id");
    private PersonComparator idDescend = new PersonComparator(false, "id");
    private PersonComparator nameAscend = new PersonComparator(true, "name");
    private PersonComparator nameDescend = new PersonComparator(false, "name");
    private PersonComparator sexAscend = new PersonComparator(true, "sex");
    private PersonComparator sexDescend = new PersonComparator(false, "sex");
    private PersonComparator departmentAscend = new PersonComparator(true, "department");
    private PersonComparator departmentDescend = new PersonComparator(false, "department");
    private PersonComparator salaryAscend = new PersonComparator(true, "salary");
    private PersonComparator salaryDescend = new PersonComparator(false, "salary");

    public MyMouseListener(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /**
         * 获得点击表头的所在列的索引
         */
        int column = mainWindow.getJTable().getTableHeader().columnAtPoint(e.getPoint());
        /**
         * 方法：获取主面板中的所有人员信息
         */
        List<Person> personArrayList = mainWindow.getPersonInfoAll();

        switch (column) {
            case 0:
                idFlag = !idFlag;

                if (idFlag) {
                    Collections.sort(personArrayList, idAscend);
                } else {
                    Collections.sort(personArrayList, idDescend);
                }
                break;
            case 1:
                nameFlag = !nameFlag;

                if (nameFlag) {
                    Collections.sort(personArrayList, nameAscend);
                } else {
                    Collections.sort(personArrayList, nameDescend);
                }
                break;
            case 2:
                sexFlag = !sexFlag;

                if (sexFlag) {
                    Collections.sort(personArrayList, sexAscend);
                } else {
                    Collections.sort(personArrayList, sexDescend);
                }
                break;
            case 3:
                if (departmentFlag) {
                    departmentFlag = false;
                    Collections.sort(personArrayList, departmentAscend);
                } else {
                    departmentFlag = true;
                    Collections.sort(personArrayList, departmentDescend);
                }
                break;
            case 4:
                salaryFlag = !salaryFlag;

                if (salaryFlag) {
                    Collections.sort(personArrayList, salaryAscend);
                } else {
                    Collections.sort(personArrayList, salaryDescend);
                }
                break;
        }
        /**
         * 将数组中所有的人员信息显示到主面板中
         */
        mainWindow.showPersonInfoAll(personArrayList);
    }
}