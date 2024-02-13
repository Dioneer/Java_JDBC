package Pegas.mapper;

import Pegas.DTO.CreateUserDTO;
import Pegas.entity.UserAdminPanel;

import java.time.LocalDate;

public final class UserAdminPanelMapper implements Mapper<CreateUserDTO, UserAdminPanel>{

    @Override
    public CreateUserDTO mapFrom(UserAdminPanel userAdminPanel) {
        return CreateUserDTO.builder()
                .id(userAdminPanel.getId())
                .user_name(userAdminPanel.getUser_name())
                .birthday(String.valueOf(userAdminPanel.getBirthday()))
                .email(userAdminPanel.getEmail())
                .email(userAdminPanel.getPassword())
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
