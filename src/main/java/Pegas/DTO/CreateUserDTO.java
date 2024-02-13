package Pegas.DTO;

import lombok.Builder;

@Builder
public record CreateUserDTO (Long id, String user_name, String birthday, String email,String password, String role, String gender){}
