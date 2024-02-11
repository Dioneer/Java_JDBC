package Pegas.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/cookies")
public class CookiesServlet extends HttpServlet {
    private final static String UNIQ_USER = "user_id";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       Cookie[] cookies = req.getCookies();
       if(cookies==null ||
        Arrays.stream(cookies).filter(cookie -> UNIQ_USER.equals(cookie.getName())).findFirst().isEmpty()){
           Cookie cookie = new Cookie(UNIQ_USER, "1");
           cookie.setMaxAge(60*60);
           cookie.setPath("/cookies");
           resp.addCookie(cookie);
       }
    }
}
