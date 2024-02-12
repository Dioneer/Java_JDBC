package Pegas.DTO;

import java.time.LocalDate;

public record CompanyFilter(String user_name, LocalDate reg_date,int limit, int offset) {
}
