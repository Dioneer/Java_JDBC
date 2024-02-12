package Pegas.service;

import Pegas.DAO.EmployeeDao;
import Pegas.DTO.EmployeeDto;

import java.util.List;
import java.util.stream.Collectors;

public final class EmployeeService {
    private final EmployeeDao employeeDao = EmployeeDao.getINSTANCE();

    public List<EmployeeDto> findAll(){
        return employeeDao.findAll().stream().map(i->new EmployeeDto(i.getId(), ("%s - %s - %d").formatted(
                i.getFirstname(), i.getLastName(), i.getSalary()),i.getCompany().getId()))
                .collect(Collectors.toList());
    }

    private static volatile EmployeeService INSTANCE;
    private EmployeeService() {}

    public static EmployeeService getINSTANCE(){
        if(INSTANCE==null){
            synchronized (EmployeeService.class){
                if(INSTANCE==null){
                    return INSTANCE = new EmployeeService();
                }
            }
        }
        return INSTANCE;
    }
}
