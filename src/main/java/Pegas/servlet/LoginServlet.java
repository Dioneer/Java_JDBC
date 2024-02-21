package Pegas.servlet;

import Pegas.DTO.CreateUserDTO;
import Pegas.DTO.UserDTO;
import Pegas.service.UserAdminPanelService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserAdminPanelService userAdminPanelService = UserAdminPanelService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userAdminPanelService.findForLogin(req.getParameter("email"), req.getParameter("password"))
                .ifPresentOrElse(userDTO-> {
                    try {
                        onLoginSuccess(userDTO,req,resp);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, ()-> {
                    try {
                        onLoginFail(req,resp);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/login?error&email="+req.getParameter("email"));
    }

    private void onLoginSuccess(UserDTO userDTO, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("user",userDTO);
        resp.sendRedirect("/user");
    }
}
