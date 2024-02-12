package Pegas.DAO;

import Pegas.DTO.EmployeeFilter;
import Pegas.entity.Company;
import Pegas.entity.Employee;
import Pegas.utils.ConnectionManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeDao implements DAO<Employee, Long>{

    private final static String SAVE_EMPLOYEE= """
            insert into employee (first_name,last_name,salary,company_id) values (?,?,?,?);
            """;

    private final static String DELETE_EMPLOYEE= """
            delete from employee
            where id = ?;
            """;
    private final static String FIND_ALL_EMPLOYEE = """
            select e.id,e.first_name,e.last_name,e.salary,e.company_id,
            c.user_name, c.reg_date
            from employee e
            JOIN company c on c.id = e.company_id
            """;
    private final static String FIND_BY_ID = """
            select e.id,e.first_name,e.last_name,e.salary,e.company_id,
            c.user_name, c.reg_date
            from employee e
            JOIN company c on c.id = e.company_id
            where e.id=?;
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
            statement.setLong(4,employee.getCompany().getId());
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
    public List<Employee> findAll(EmployeeFilter filter){
        List<Object> parametrs = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(filter.firstName()!=null) {
            parametrs.add(filter.firstName());
            whereSql.add("e.first_name = ?");
        }
        if(filter.lastName()!=null) {
            parametrs.add(filter.lastName());
            whereSql.add("e.last_name = ?");
        }
        parametrs.add(filter.limit());
        parametrs.add(filter.offset());
        String query = whereSql.stream().collect(Collectors.joining(" AND ",
                parametrs.size()>2?" WHERE ":" "," LIMIT ? OFFSET ? "));
        String sql = FIND_ALL_EMPLOYEE + query;
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(sql)){
            List<Employee> arr = new ArrayList<>();
            for (int i = 0; i < parametrs.size(); i++) {
                statement.setObject(i+1,parametrs.get(i));
            }
            ResultSet result = statement.executeQuery();
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
            statement.setLong(4,employee.getCompany().getId());
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

    private Employee buildEmployee(ResultSet result) throws SQLException {
        Company company = new Company(result.getLong("company_id"),
                result.getString("user_name"),
                LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(result.getDate("reg_date"))));
        return new Employee(result.getLong("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getInt("salary"),
                company);
    }

    private volatile static EmployeeDao INSTANCE;
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
