package Pegas.mapper;

import Pegas.DTO.CreateUserDTO;
import Pegas.DTO.UserDTO;
import Pegas.entity.UserAdminPanel;

public final class UserAdminPanelMapper implements Mapper<UserDTO, UserAdminPanel>{

    @Override
    public UserDTO mapFrom(UserAdminPanel userAdminPanel) {
        return UserDTO.builder()
                .id(userAdminPanel.getId())
                .user_name(userAdminPanel.getUser_name())
                .birthday(userAdminPanel.getBirthday())
                .email(userAdminPanel.getEmail())
                .password(userAdminPanel.getPassword())
                .role(userAdminPanel.getRole())
                .gender(userAdminPanel.getGender())
                .build();
    }
    private static volatile UserAdminPanelMapper INSTANCE;
    private UserAdminPanelMapper() {}

    public static UserAdminPanelMapper getInstance(){
        if(INSTANCE==null){
            synchronized (UserAdminPanelMapper.class){
                if (INSTANCE==null){
                    INSTANCE = new UserAdminPanelMapper();
                }
            }
        }
        return INSTANCE;
    }

}
