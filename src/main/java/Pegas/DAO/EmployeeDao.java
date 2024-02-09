package Pegas.DAO;

import Pegas.entity.Employee;
import Pegas.entity.User;
import Pegas.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDao implements DAO<Employee, Long>{

    private final static String SAVE_EMPLOYEE= """
            insert into employee (first_name,last_name,salary,company_id) values (?,?,?,?);
            """;

    private final static String DELETE_EMPLOYEE= """
            delete from employee
            where id = ?;
            """;
    private final static String FIND_ALL_EMPLOYEE = """
            select id,first_name,last_name,salary,company_id from employee
            """;
    private final static String FIND_BY_ID = """
            select id,first_name,last_name,salary,company_id from employee
            where id=?;
            """;
    private final static String UPDATE_EMPLOYEE = """
            update employee
            set first_name = ?,
            last_name = ?,
            salary = ?,
            company_id = ?
            where id = ?;
            """;

    @Override
    public boolean update(Employee employee) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE)){
            statement.setString(1,employee.getFirstname());
            statement.setString(2,employee.getLastName());
            statement.setInt(3,employee.getSalary());
            statement.setLong(4,employee.getCompany_id());
            statement.setLong(5,employee.getId());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> findAll() {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_EMPLOYEE)){
            ResultSet result = statement.executeQuery();
            List<Employee> arr = new ArrayList<>();
            while (result.next()){
                arr.add(buildEmployee(result));
            }
            return arr;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Employee> findByID(Long id) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            Employee employee = null;
            while (resultSet.next()){
                employee = buildEmployee(resultSet);
            }
            return Optional.ofNullable(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE)){
            statement.setLong(1,id);
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee save(Employee employee) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_EMPLOYEE, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,employee.getFirstname());
            statement.setString(2,employee.getLastName());
            statement.setInt(3,employee.getSalary());
            statement.setLong(4,employee.getCompany_id());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet!= null){
                employee.setId(resultSet.getLong("id"));
            }
            return  employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private volatile static EmployeeDao INSTANCE;

    private Employee buildEmployee(ResultSet result) throws SQLException {
        return new Employee(result.getLong("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getInt("salary"),
                result.getLong("company_id"));
    }

    private EmployeeDao() {
    }
    public static EmployeeDao getINSTANCE() {
        if(INSTANCE==null){
            synchronized (EmployeeDao.class){
                if(INSTANCE==null){
                    INSTANCE = new EmployeeDao();
                }
            }
        }
        return INSTANCE;
    }
}
