package Pegas;

import Pegas.DAO.EmployeeDao;
import Pegas.DAO.UserDao;
import Pegas.DTO.EmployeeFilter;
import Pegas.DTO.UserFilter;
import Pegas.entity.Employee;
import Pegas.entity.User;
import Pegas.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        /**
         * CRUD
         */
        UserDao userDao = UserDao.getINSTANCE();
        EmployeeDao employeeDao = EmployeeDao.getINSTANCE();
//        System.out.println(userDao.save(new User("Mim","Minivskiy","arlozxzx50@example.org", 9561235496L)));
//        System.out.println(userDao.delete(8L));
//        System.out.println(userDao.save(new User("Mim","Minivskiy","arlozxzx52@example.org", 9561235496L)));
//        System.out.println(userDao.findAll());
//        System.out.println(userDao.findByID(59L));
//        User user = userDao.findByID(48L).get();
//        user.setPhone(77777777L);
//        System.out.println(userDao.update(user));
        UserFilter filter = new UserFilter(null, "Nienow", 5, 0);
        System.out.println(userDao.findAll(filter));
        Employee employee = employeeDao.findByID(4L).get();
        employee.setFirstname("Elianora");
        EmployeeFilter employeeFilter = new EmployeeFilter("Jon", "Jonson", 10, 0);
        System.out.println(employeeDao.findAll(employeeFilter));
        /**
         * connection try
         */
//        List<Long> arr= getUsersId("%n");
//        System.out.println(arr);
//        checkMetaData();
//        String sql =
//                """
//                create table info(
//                id serial primary key,
//                data varchar(256)
//                );
//                """
//                """
//                insert into info (data) values
//                 ('2010'),('2021'),('2010'),('2022'),('2013'),('2024'),('2012'),('2016');
//                """;
//                """
//                SELECT * FROM EMPLOYEE;
//                """;
//                """
//                drop schema game;
//                """;
//                """
//                create schema game;
//                """;
//        try(Connection connection = ConnectionManager.get();
//            Statement statement = connection.createStatement()){
//            ResultSet result = statement.executeQuery(sql);
//            while (result.next()){
//                System.out.println(result.getString("first_name"));
//            }
//        }
    }
    public static List<Long> getUsersId(String name) throws SQLException {
        List<Long> users = new ArrayList<>();
        String sql =
                """
                select * from users
                where firstname LIKE ?;
                """;
        try(Connection connection = ConnectionManager.get();
//            Statement statement = connection.createStatement()
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setFetchSize(3);
            statement.setMaxRows(5);
            statement.setQueryTimeout(1000);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                users.add(result.getLong("id"));
            }
        }
        return users;
    }
    public static void checkMetaData() throws SQLException{
        try(Connection connection = ConnectionManager.get()){
            DatabaseMetaData dmd = connection.getMetaData();
            ResultSet resultSet = dmd.getCatalogs();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        }
    }
}
