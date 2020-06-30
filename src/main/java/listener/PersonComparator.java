package listener;

import lombok.Data;
import person.Person;

import java.util.Comparator;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class PersonComparator implements Comparator<Person> {
    private Boolean flag;
    private String field;

    public PersonComparator(Boolean flag, String field) {
        this.flag = flag;
        this.field = field;
    }

    @Override
    public int compare(Person o1, Person o2) {
        if (flag) {
            /**
             * 单击表头奇数次，对该列数据进行升序排列
             */
            switch (field) {
                case "id":
                    return o1.getId() - o2.getId();
                case "name":
                    return o1.getName().compareTo(o2.getName());
                case "sex":
                    return o1.getSex().compareTo(o2.getSex());
                case "department":
                    return o1.getDepartment().compareTo(o2.getDepartment());
                case "salary":
                    return o1.getSalary() - o2.getSalary();
                default:
                    return 0;
            }
        } else {
            /**
             * 单击表头偶数次，对该列数据进行降序排列
             */
            switch (field) {
                case "id":
                    return o2.getId() - o1.getId();
                case "name":
                    return o2.getName().compareTo(o1.getName());
                case "sex":
                    return o2.getSex().compareTo(o1.getSex());
                case "department":
                    return o2.getDepartment().compareTo(o1.getDepartment());
                case "salary":
                    return o2.getSalary() - o1.getSalary();
                default:
                    return 0;
            }
        }
    }
}