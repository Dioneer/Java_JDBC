package Pegas.servlet;

import Pegas.DTO.EmployeeDto;
import Pegas.service.EmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/content")
public class ContentServlet extends HttpServlet {
    private final EmployeeService employeeService = EmployeeService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<EmployeeDto> arr = employeeService.findAll();
        req.setAttribute("employee", arr);
        req.getRequestDispatcher("/employee.jsp").forward(req, resp);
    }
}
