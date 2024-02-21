package Pegas.servlet;

import Pegas.DTO.CreateUserDTO;
import Pegas.entity.Gender;
import Pegas.entity.Role;
import Pegas.exception.ValidationException;
import Pegas.service.UserAdminPanelService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserAdminPanelService userAdminPanelService = UserAdminPanelService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getServletContext().getRequestDispatcher("/registration.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(PrintWriter writer= resp.getWriter()){
            CreateUserDTO createUserDTO = CreateUserDTO.builder()
                    .user_name(req.getParameter("name"))
                    .birthday(req.getParameter("birthday"))
                    .email(req.getParameter("email"))
                    .password(req.getParameter("pwd"))
                    .role(req.getParameter("role"))
                    .gender(req.getParameter("gender"))
                    .build();
            try {
                userAdminPanelService.save(createUserDTO);
                resp.sendRedirect("/login");
            } catch (ValidationException e) {
                req.setAttribute("errors", e.getErrors());
                System.out.println(e.getErrors());
                doGet(req, resp);
            }
        }
    }
}
