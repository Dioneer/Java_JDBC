package Pegas.DTO;

import Pegas.entity.Gender;
import Pegas.entity.Role;
import lombok.Builder;

@Builder
public record CreateUserDTO (Long id, String user_name, String birthday, String email, String password, Role role, Gender gender){}
