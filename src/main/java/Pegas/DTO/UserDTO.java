package Pegas.DTO;

import Pegas.entity.Gender;
import Pegas.entity.Role;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

@Builder
public record UserDTO(Long id, String  user_name, LocalDate birthday, String email, String password, Role role,
                      Gender gender) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
