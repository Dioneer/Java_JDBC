package Pegas.servlet;

import Pegas.DTO.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/session")
public class SessionServlet extends HttpServlet {
    private final static String USER = "user";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = new UserDTO(5L,"adasd@ads.ru");
        session.setAttribute(USER, user);
//        System.out.println(session.isNew());
    }
}
