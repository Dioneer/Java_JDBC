package Pegas.DAO;

import Pegas.DTO.UserFilter;
import Pegas.entity.User;
import Pegas.exception.DaoException;
import Pegas.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class UserDao implements DAO<User, Long>{
    private volatile static UserDao INSTANCE;
    private final static String SAVE_USER = """
            insert into users (firstName,lastName,email,phone) values (?,?,?,?);
            """;

    private final static String DELETE_USER = """
            delete from users
            where id = ?;
            """;
    private final static String FIND_ALL_USERS = """
            select id,firstName,lastName,email,phone from users
            """;
    private final static String FIND_BY_ID = """
            select id,firstName,lastName,email,phone from users
            where id=?;
            """;
    private final static String UPDATE_USER = """
            update users 
            set firstName = ?,
            lastName = ?,
            email = ?,
            phone = ?
            where id = ?;
            """;
    public User save(User user){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setString(3,user.getEmail());
            statement.setLong(4,user.getPhone());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                user.setId(resultSet.getLong("id"));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id){
        try(Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(DELETE_USER)){
            statement.setLong(1, id);
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public List<User> findAll(){
        try(Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)){
            ResultSet result = statement.executeQuery();
            List<User> arr = new ArrayList<>();
            while (result.next()){
                arr.add(buildUser(result));
            }
            return arr;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<User> findAll(UserFilter filter){
        List<Object> parametrs = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(filter.firstName()!=null) {
            parametrs.add(filter.firstName());
            whereSql.add("firstname = ?");
        }
        if(filter.lastName()!=null) {
            parametrs.add(filter.lastName());
            whereSql.add("lastname = ?");
        }
        parametrs.add(filter.limit());
        parametrs.add(filter.offset());
        String query = whereSql.stream().collect(Collectors.joining(" AND ",
                parametrs.size()>2?" WHERE ":" "," LIMIT ? OFFSET ? "));
        String sql = FIND_ALL_USERS + query;
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)){
            List<User> arr = new ArrayList<>();
            for (int i = 0; i < parametrs.size(); i++) {
                statement.setObject(i+1,parametrs.get(i));
            }
            System.out.println(statement);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                arr.add(buildUser(result));
            }
            return arr;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<User> findByID(Long id){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            User user = null;
            while (result.next()){
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(User user){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER)){
            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setString(3,user.getEmail());
            statement.setLong(4,user.getPhone());
            statement.setLong(5,user.getId());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User buildUser(ResultSet result) throws SQLException {
        return new User(result.getLong("id"),
       result.getString("firstname"),
        result.getString("lastname"),
        result.getString("email"),
        result.getLong("phone"));
    }

    private UserDao() {
    }
    public static UserDao getINSTANCE() {
        if(INSTANCE==null){
            synchronized (UserDao.class){
                if(INSTANCE==null){
                    INSTANCE = new UserDao();
                }
            }
        }
        return INSTANCE;
    }
}
