package Pegas.entity;

import java.util.Objects;

public class Employee {
    private Long id;
    private String firstname;
    private String lastName;
    private int salary;
    private Long company_id;

    public Employee(Long id, String firstname, String lastName, int salary, Long company_id) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.salary = salary;
        this.company_id = company_id;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
               "id=" + id +
               ", firstname='" + firstname + '\'' +
               ", lastName='" + lastName + '\'' +
               ", salary=" + salary +
               ", company_id=" + company_id +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return salary == employee.salary && Objects.equals(id, employee.id) && Objects.equals(firstname, employee.firstname) && Objects.equals(lastName, employee.lastName) && Objects.equals(company_id, employee.company_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastName, salary, company_id);
    }
}
