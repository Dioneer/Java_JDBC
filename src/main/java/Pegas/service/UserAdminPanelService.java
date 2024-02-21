package Pegas.service;

import Pegas.DAO.UserAdminPanelDAO;
import Pegas.DAO.UserDao;
import Pegas.DTO.CreateUserDTO;
import Pegas.DTO.UserDTO;
import Pegas.entity.UserAdminPanel;
import Pegas.exception.ValidationException;
import Pegas.mapper.CreateUserMapper;
import Pegas.mapper.UserAdminPanelMapper;
import Pegas.validator.CreateUserValidator;
import Pegas.validator.ValidationResult;

import java.util.Optional;

public class UserAdminPanelService {
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserAdminPanelDAO userAdminPanelDAO = UserAdminPanelDAO.getINSTANCE();
    private final UserDao userDao = UserDao.getINSTANCE();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getINSTACE();
    private final UserAdminPanelMapper userAdminPanelMapper = UserAdminPanelMapper.getInstance();

    public Optional<UserDTO> findForLogin(String email, String password){
        System.out.println("UserAdminPanelService"+password);
        return userDao.findByEP(email,password).map(userAdminPanelMapper::mapFrom);
    }

    public Integer save(CreateUserDTO createUserDTO) throws ValidationException {
        ValidationResult validationResult = createUserValidator.isValid(createUserDTO);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        UserAdminPanel user = createUserMapper.mapFrom(createUserDTO);
        UserAdminPanel result = userAdminPanelDAO.save(user);
        return Math.toIntExact(result.getId());
    }

    private static volatile UserAdminPanelService INSTANCE;
    private UserAdminPanelService() {}

    public static UserAdminPanelService getINSTANCE(){
        if(INSTANCE==null){
            synchronized (UserAdminPanelService.class){
                if(INSTANCE==null){
                    return INSTANCE = new UserAdminPanelService();
                }
            }
        }
        return INSTANCE;
    }

    public void login(String email, String password) {

    }
}
