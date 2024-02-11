package Pegas.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/dispatcher")
public class DispatcherServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/employee");
        resp.setHeader("dispatcher", "true");
        req.setAttribute("dispatcher", "true");
//        dispatcher.forward(req,resp);
//        dispatcher.include(req,resp);
        resp.sendRedirect("/user.jsp");
    }
}
