package Pegas.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Company {
    private Long id;
    private String user_name;
    private LocalDate reg_date;

    public Company(Long id, String user_name, LocalDate reg_date) {
        this.id = id;
        this.user_name = user_name;
        this.reg_date = reg_date;
    }
    public Company(String user_name, LocalDate reg_date) {
        this.user_name = user_name;
        this.reg_date = reg_date;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public LocalDate getReg_date() {
        return reg_date;
    }
    public void setReg_date(LocalDate reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(user_name, company.user_name) && Objects.equals(reg_date, company.reg_date);
    }

    @Override
    public String toString() {
        return "Company{" +
               "id=" + id +
               ", user_name='" + user_name + '\'' +
               ", reg_date=" + reg_date +
               '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_name, reg_date);
    }
}
