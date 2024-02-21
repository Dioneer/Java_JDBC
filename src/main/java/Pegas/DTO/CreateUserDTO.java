package Pegas.DTO;

import Pegas.entity.Gender;
import Pegas.entity.Role;
import lombok.Builder;

@Builder
public record CreateUserDTO (String user_name, String birthday, String email, String password, String role,
                             String gender){}
