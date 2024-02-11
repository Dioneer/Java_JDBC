package Pegas.servlet;

import Pegas.service.EmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeService employeeService = EmployeeService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try(PrintWriter writer= resp.getWriter()){
            writer.write("<h1>List of employee:</h1>");
            writer.write("<ul>");
            employeeService.findAll().forEach(employeeDto ->
                    writer.write("<li><a href='/company?companyid=%d'>".formatted(employeeDto.id_company())+employeeDto.id()+". "
                            +employeeDto.description()+"</a></li>"));
            writer.write("</ul>");
        }
    }
}
