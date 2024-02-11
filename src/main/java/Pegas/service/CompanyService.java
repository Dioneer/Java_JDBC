package Pegas.service;

import Pegas.DAO.CompanyDao;
import Pegas.DTO.CompanyDTO;
import java.util.List;
import java.util.stream.Collectors;

public final class CompanyService {
    private final CompanyDao companyDao= CompanyDao.getINSTANCE();

    public List<CompanyDTO> findAllByEmployeeId(Long id){
        return companyDao.findAllByEmployeeId(id).stream().map(i->
                new CompanyDTO(i.getId(),i.getUser_name(),i.getReg_date())).collect(Collectors.toList());
    }

    private CompanyService(){};
    private static volatile CompanyService INSTANCE;
    public static CompanyService getINSTANCE(){
        if(INSTANCE==null){
            synchronized (CompanyService.class){
                if(INSTANCE==null){
                    INSTANCE = new CompanyService();
                }
            }
        }
        return INSTANCE;
    }
}
