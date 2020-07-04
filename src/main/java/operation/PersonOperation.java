package operation;

import lombok.Data;
import person.Person;
import util.DatabaseUtil;
import window.EditWindow;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Baltan
 * @date 2020-06-30 23:33
 */
@Data
public class PersonOperation {
    private EditWindow editWindow;

    public PersonOperation(EditWindow editWindow) {
        this.editWindow = editWindow;
    }

    /**
     * 从编辑面板中向数据库添加单条人员信息
     *
     * @param person
     */
    public void addPerson(Person person) {
        Connection connection = DatabaseUtil.getConnection();
        String sql = "insert into person values(?,?,?,?,?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getSex());
            preparedStatement.setString(4, person.getDepartment());
            preparedStatement.setInt(5, person.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.DatabaseClose(null, preparedStatement, connection);
        }
    }

    /**
     * 将主面板中所有人员信息导入到数据库中
     *
     * @param personArrayList
     */
    public void addPersonAll(List<Person> personArrayList) {
        Connection connection = DatabaseUtil.getConnection();
        String sql = "insert into person values(?,?,?,?,?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < personArrayList.size(); i++) {
                Person person = personArrayList.get(i);
                preparedStatement.setInt(1, person.getId());
                preparedStatement.setString(2, person.getName());
                preparedStatement.setString(3, person.getSex());
                preparedStatement.setString(4, person.getDepartment());
                preparedStatement.setInt(5, person.getSalary());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.DatabaseClose(null, preparedStatement, connection);
        }
    }

    /**
     * 将编辑面板中更改的单条人员信息同步到数据库中
     *
     * @param id
     * @param person
     */
    public void modifyPerson(int id, Person person) {
        Connection connection = DatabaseUtil.getConnection();
        String sql = "update person set id=?,name=?,sex=?,department=?,salary=? where id=" + id;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getSex());
            preparedStatement.setString(4, person.getDepartment());
            preparedStatement.setInt(5, person.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.DatabaseClose(null, preparedStatement, connection);
        }
    }

    /**
     * 从数据库中模糊查询符合查询条件的所有人员信息
     *
     * @param queryLabel
     * @param queryContent
     * @param option
     * @return
     */
    public List<Object[]> queryPerson(List<String> queryLabel, ArrayList<String> queryContent,
                                      String option) {
        List<Object[]> objectsArrayList = new ArrayList<>();
        Connection connection = DatabaseUtil.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from person where ";
        /**
         * 如果无查询字段，则从数据库中显示所有人员信息
         */
        if (queryLabel.size() == 0) {
            editWindow.jTableRenewPerson();
            /**
             * 如果有查询字段，则从数据库中模糊查询符合查询条件的所有人员信息
             */
        } else if (queryLabel.size() > 0) {
            for (int i = 0; i < queryLabel.size(); i++) {
                sql = sql + queryLabel.get(i) + " like'%" + queryContent.get(i) + "%'";

                if (i < queryLabel.size() - 1) {
                    sql = sql + " " + option + " ";
                }
            }

            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String sex = resultSet.getString("sex");
                    String department = resultSet.getString("department");
                    int salary = resultSet.getInt("salary");
                    Object[] personInfo = {String.valueOf(id), name, sex, department, String.valueOf(salary)};
                    objectsArrayList.add(personInfo);
                }

                if (objectsArrayList.size() == 0) {
                    JOptionPane.showMessageDialog(null, "没有找到匹配记录");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseUtil.DatabaseClose(resultSet, statement, connection);
            }
        }
        return objectsArrayList;
    }

    /**
     * 从数据库中精确查询符合查询条件的所有人员信息
     *
     * @param queryLabel
     * @param queryContent
     * @param option
     * @return
     */
    public List<Object[]> exactQueryPerson(ArrayList<String> queryLabel, ArrayList<String> queryContent,
                                           String option) {
        List<Object[]> objectsArrayList = new ArrayList<>();
        Connection connection = DatabaseUtil.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from person where ";
        /**
         * 如果无查询字段，则从数据库中显示所有人员信息
         */
        if (queryLabel.size() == 0) {
            editWindow.jTableRenewPerson();
            /**
             * 如果有查询字段，则从数据库中精确查询符合查询条件的所有人员信息
             */
        } else if (queryLabel.size() > 0) {
            for (int i = 0; i < queryLabel.size(); i++) {
                if (queryLabel.get(i).equals("name") || queryLabel.get(i).equals("sex")
                        || queryLabel.get(i).equals("department")) {
                    sql = sql + queryLabel.get(i) + "='" + queryContent.get(i) + "'";
                } else if (queryLabel.get(i).equals("id") || queryLabel.get(i).equals("salary")) {
                    sql = sql + queryLabel.get(i) + "=" + Integer.valueOf(queryContent.get(i));
                }
                if (i < queryLabel.size() - 1) {
                    sql = sql + " " + option + " ";
                }
            }
        }

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                String department = resultSet.getString("department");
                int salary = resultSet.getInt("salary");
                Object[] personInfo = {String.valueOf(id), name, sex, department, String.valueOf(salary)};
                objectsArrayList.add(personInfo);
            }

            if (objectsArrayList.size() == 0) {
                JOptionPane.showMessageDialog(null, "没有找到匹配记录");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.DatabaseClose(resultSet, statement, connection);
        }
        return objectsArrayList;
    }

    /**
     * 从数据库中查询获取所有人员信息
     *
     * @return
     */
    public List<Object[]> queryPersonAll() {
        Connection connection = DatabaseUtil.getConnection();
        String sql = "select * from person";
        Statement statement = null;
        ResultSet resultSet = null;
        List<Object[]> objectsArrayList = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                String department = resultSet.getString("department");
                int salary = resultSet.getInt("salary");
                Object[] personInfo = {String.valueOf(id), name, sex, department, String.valueOf(salary)};
                objectsArrayList.add(personInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.DatabaseClose(resultSet, statement, connection);
        }
        return objectsArrayList;
    }

    /**
     * 从数据库中删除单条人员信息
     *
     * @param id
     */
    public void deletePerson(int id) {
        Connection connection = DatabaseUtil.getConnection();
        String sql = "delete from person where id=" + id;
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.DatabaseClose(null, statement, connection);
        }
    }
}