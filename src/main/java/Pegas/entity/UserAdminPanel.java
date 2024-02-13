package Pegas.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class UserAdminPanel {
    private Long id;
    private String user_name;
    private LocalDate birthday;
    private String email;
    private String password;
    private Role role;
    private Gender gender;
}
