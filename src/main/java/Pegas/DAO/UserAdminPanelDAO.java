package Pegas.DAO;

import Pegas.entity.UserAdminPanel;
import Pegas.exception.DaoException;
import Pegas.utils.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public final class UserAdminPanelDAO implements DAO<UserAdminPanel, Long>{
    private final static String SAVE_USERADMINPANEL=
            """
            insert into admin_panel_users (user_name, birthday, email,password, role, gender) values (?,?,?,?,?,?)
            """;
    private final static String UPDATE_USERADMINPANEL=
            """
            update admin_panel_users 
            set user_name = ?,
            reg_date = ?
            where id = ?
            """;
    private final static String DELETE_USERADMINPANEL=
            """
            delete from admin_panel_users
            where id = ?
            """;
    private final static String FINDBY_ID_USERADMINPANEL=
            """
            select * from admin_panel_users
            where id = ?
            """;
    private final static String FIND_ALL_USERADMINPANEL=
            """
            select * from admin_panel_users
            """;
    @Override
    public boolean update(UserAdminPanel userAdminPanel) {
        return false;
    }

    @Override
    public List<UserAdminPanel> findAll() {
        return null;
    }

    @Override
    public Optional<UserAdminPanel> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public UserAdminPanel save(UserAdminPanel userAdminPanel) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_USERADMINPANEL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,userAdminPanel.getUser_name());
            statement.setString(2, String.valueOf(userAdminPanel.getBirthday()));
            statement.setString(3,userAdminPanel.getEmail());
            statement.setString(4,userAdminPanel.getPassword());
            statement.setString(5,userAdminPanel.getRole().name());
            statement.setString(6,userAdminPanel.getGender().name());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                userAdminPanel.setId(resultSet.getLong("id"));
            }
            return userAdminPanel;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static volatile UserAdminPanelDAO INSTANCE;

    private UserAdminPanelDAO() {};

    public static UserAdminPanelDAO getINSTANCE() {
        if(INSTANCE==null){
            synchronized (UserAdminPanelDAO.class){
                if(INSTANCE==null){
                    INSTANCE = new UserAdminPanelDAO();
                }
            }
        }
        return INSTANCE;
    }
}
