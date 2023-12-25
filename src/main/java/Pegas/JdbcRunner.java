package Pegas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args){
        String sql = """
                insert into game.info(data) values ('2017-08-15'),
                ('2018-08-15'),('2019-08-15'),('2020-08-15'),('2021-08-15')
                """;
        try(Connection connection = ConnectionManager.open();
        Statement statement = connection.createStatement()){
            System.out.println(statement.execute(sql));
        }catch(SQLException ex) {System.out.println(ex.getMessage());}

    }
}
