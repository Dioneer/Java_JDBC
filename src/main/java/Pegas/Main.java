package Pegas;

import Pegas.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        List<Long> arr= getUsersId("%n ");
        System.out.println(arr);
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
//        try(Connection connection = ConnectionManager.open();
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
        try(Connection connection = ConnectionManager.open();
//            Statement statement = connection.createStatement()
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                users.add(result.getLong("id"));
            }
        }
        return users;
    }
}
