package Pegas.DAO;

import java.util.List;
import java.util.Optional;

public class EmployeeDao implements DAO<Employee, Long>{

    @Override
    public boolean update(Employee user) {
        return false;
    }

    @Override
    public boolean update(Employee employee) {
        return false;
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public Optional<Employee> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Employee save(Employee employee) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }
}
