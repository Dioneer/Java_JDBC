package Pegas.mapper;

import Pegas.DTO.CreateUserDTO;
import Pegas.entity.Gender;
import Pegas.entity.Role;
import Pegas.entity.UserAdminPanel;
import Pegas.utils.LocalDateFormatter;

public final class CreateUserMapper implements Mapper<UserAdminPanel, CreateUserDTO>{
    @Override
    public UserAdminPanel mapFrom(CreateUserDTO createUserDTO) {
        return UserAdminPanel.builder()
                .user_name(createUserDTO.user_name())
                .birthday(LocalDateFormatter.format(createUserDTO.birthday()))
                .email(createUserDTO.email())
                .password(createUserDTO.password())
                .gender(Gender.valueOf(createUserDTO.gender()))
                .role(Role.valueOf(createUserDTO.role()))
                .build();
    }
    private static volatile CreateUserMapper INSTANCE;
    private CreateUserMapper() {}
    public static CreateUserMapper getInstance(){
        if(INSTANCE==null){
            synchronized (CreateUserMapper.class){
                if (INSTANCE==null){
                    INSTANCE = new CreateUserMapper();
                }
            }
        }
        return INSTANCE;
    }
}
