package Pegas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args){
        String sql = """
                select *  from game.info
                """;
        try(Connection connection = ConnectionManager.open();
        Statement statement = connection.createStatement()){
            statement.setFetchSize(2);
            statement.setMaxRows(2);
//            System.out.println(statement.execute(sql));
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                System.out.println(result.getString("id"));
                System.out.println(result.getString("data"));
            }
        }catch(SQLException ex) {System.out.println(ex.getMessage());}

    }
}
