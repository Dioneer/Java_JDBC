package Pegas.service;

import Pegas.DAO.UserAdminPanelDAO;
import Pegas.DTO.CreateUserDTO;
import Pegas.entity.UserAdminPanel;
import Pegas.mapper.CreateUserMapper;

public class UserAdminPanelService {
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserAdminPanelDAO userAdminPanelDAO = UserAdminPanelDAO.getINSTANCE();
    public Integer save(CreateUserDTO createUserDTO){
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
}
