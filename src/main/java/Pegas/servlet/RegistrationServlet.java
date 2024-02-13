package Pegas.servlet;

import Pegas.DTO.CreateUserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setAttribute("roles", List.of("Admin","User","Support"));
        req.setAttribute("genders", List.of("male","female"));
        req.getServletContext().getRequestDispatcher("/registration.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.setContentType("text/html; charset=UTF-8");
        try(PrintWriter writer= resp.getWriter()){
            CreateUserDTO createUserDTO = CreateUserDTO.builder()
                    .user_name(req.getParameter("name"))
                    .birthday(req.getParameter("birthday"))
                    .email(req.getParameter("email"))
                    .password(req.getParameter("password"))
                    .role(req.getParameter("role"))
                    .gender(req.getParameter("gender"))
                    .build();
        }
    }
}
