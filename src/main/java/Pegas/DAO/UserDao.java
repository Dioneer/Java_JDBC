package Pegas.DAO;

import Pegas.DTO.UserFilter;
import Pegas.entity.Gender;
import Pegas.entity.Role;
import Pegas.entity.User;
import Pegas.entity.UserAdminPanel;
import Pegas.exception.DaoException;
import Pegas.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class UserDao{

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
    private final static String GET_BY_EMAIL_AND_PASSWORD =
            """
            select * from admin_panel_users where email= ? and password = ?
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
//    public List<User> findAll(){
//        try(Connection connection = ConnectionManager.get();
//        PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)){
//            ResultSet result = statement.executeQuery();
//            List<User> arr = new ArrayList<>();
//            while (result.next()){
//                arr.add(buildUser(result));
//            }
//            return arr;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public List<User> findAll(UserFilter filter){
//        List<Object> parametrs = new ArrayList<>();
//        List<String> whereSql = new ArrayList<>();
//        if(filter.firstName()!=null) {
//            parametrs.add(filter.firstName());
//            whereSql.add("firstname = ?");
//        }
//        if(filter.lastName()!=null) {
//            parametrs.add(filter.lastName());
//            whereSql.add("lastname = ?");
//        }
//        parametrs.add(filter.limit());
//        parametrs.add(filter.offset());
//        String query = whereSql.stream().collect(Collectors.joining(" AND ",
//                parametrs.size()>2?" WHERE ":" "," LIMIT ? OFFSET ? "));
//        String sql = FIND_ALL_USERS + query;
//        try(Connection connection = ConnectionManager.get();
//            PreparedStatement statement = connection.prepareStatement(sql)){
//            List<User> arr = new ArrayList<>();
//            for (int i = 0; i < parametrs.size(); i++) {
//                statement.setObject(i+1,parametrs.get(i));
//            }
//            System.out.println(statement);
//            ResultSet result = statement.executeQuery();
//            while (result.next()){
//                arr.add(buildUser(result));
//            }
//            return arr;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public Optional<User> findByID(Long id){
//        try(Connection connection = ConnectionManager.get();
//            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
//            statement.setLong(1,id);
//            ResultSet result = statement.executeQuery();
//            UserAdminPanel user = null;
//            while (result.next()){
//                user = buildUser(result);
//            }
//            return Optional.ofNullable(user);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public Optional<UserAdminPanel> findByEP(String email,String pass){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD)){
            statement.setString(1,email);
            statement.setString(2,pass);
            ResultSet result = statement.executeQuery();
            System.out.println("user"+result);
            UserAdminPanel user = null;
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

    private UserAdminPanel buildUser(ResultSet result) throws SQLException {
        return UserAdminPanel.builder()
                .id(result.getLong("id"))
                .user_name(result.getString("user_name"))
                .birthday(result.getObject("birthday", Date.class).toLocalDate())
                .email(result.getString("email"))
                .password(result.getString("password"))
                .role(Role.valueOf(result.getString("role")))
                .gender(Gender.valueOf(result.getString("gender")))
                .build();
    }

    private volatile static UserDao INSTANCE;
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
