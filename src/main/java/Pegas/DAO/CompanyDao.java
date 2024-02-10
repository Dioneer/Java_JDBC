package Pegas.DAO;

import Pegas.DTO.CompanyFilter;
import Pegas.entity.Company;
import Pegas.entity.Employee;
import Pegas.exception.DaoException;
import Pegas.utils.ConnectionManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompanyDao implements DAO<Company, Long>{

    private final static String SAVE_COMPANY=
            """
            insert into company (user_name, reg_date) values (?,?)
            """;
    private final static String UPDATE_COMPANY=
            """
            update company 
            set user_name = ?,
            reg_date = ?
            where id = ?
            """;
    private final static String DELETE_COMPANY=
            """
            delete from company
            where id = ?
            """;
    private final static String FINDBY_ID_COMPANY=
            """
            select * from company
            where id = ?
            """;
    private final static String FIND_ALL_COMPANY=
            """
            select * from company
            """;
    @Override
    public boolean update(Company company) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY)) {
            statement.setString(1,company.getUser_name());
            statement.setDate(2, Date.valueOf(company.getReg_date()));
            statement.setLong(3, company.getId());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Company> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_COMPANY)) {
            statement.setFetchSize(2);
            ResultSet resultSet = statement.executeQuery();
            List<Company> arr = new ArrayList<>();
            while (resultSet.next()) {
                arr.add(buildCompany(resultSet));
            }
            return arr;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Company> findAll(CompanyFilter filter) {
        List<Object> parametrs = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(filter.user_name()!=null){
            parametrs.add(filter.user_name());
            whereSql.add(" user_name = ? ");
        }
        if(filter.reg_date()!=null){
            parametrs.add(filter.reg_date());
            whereSql.add(" reg_date = ? ");
        }
        parametrs.add(filter.limit());
        parametrs.add(filter.offset());
        String query = whereSql.stream().collect(Collectors.joining(" AND ",
                parametrs.size()>2?" WHERE ":" ", " LIMIT ? OFFSET ?"));
        String sql = FIND_ALL_COMPANY + query;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parametrs.size(); i++) {
                statement.setObject(i+1, parametrs.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            List<Company> arr = new ArrayList<>();
            while (resultSet.next()){
                arr.add(buildCompany(resultSet));
            }
            return arr;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        @Override
    public Optional<Company> findByID (Long id) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FINDBY_ID_COMPANY)){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            Company company = null;
            while (resultSet.next()){
                company=buildCompany(resultSet);
            }
            return Optional.ofNullable(company);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY)){
            statement.setLong(1, id);
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Company save(Company company){
        try(Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(SAVE_COMPANY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,company.getUser_name());
            statement.setDate(2, Date.valueOf(company.getReg_date()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                company.setId(resultSet.getLong("id"));
            }
            return company;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    private Company buildCompany(ResultSet resultSet) throws SQLException {
        return new Company(resultSet.getLong("id"), resultSet.getString("user_name"),
                LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate("reg_date"))));
    }

    private volatile static CompanyDao INSTANCE;
    private CompanyDao(){};
    public static CompanyDao getINSTANCE(){
        if(INSTANCE==null){
            synchronized (Company.class){
                if(INSTANCE==null){
                    INSTANCE=new CompanyDao();
                }
            }
        }
        return INSTANCE;
    }
}
