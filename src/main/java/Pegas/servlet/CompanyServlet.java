package Pegas.servlet;


import Pegas.service.CompanyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/company")
public class CompanyServlet extends HttpServlet {
    private final CompanyService service = CompanyService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Long companyId = Long.valueOf(req.getParameter("companyid"));
//        try(PrintWriter writer = resp.getWriter()){
//            writer.write("<h1>Page of company:</h1>");
//            service.findAllByEmployeeId(companyId).forEach(i->writer.write("<h2>%s</h2>\r\n<h3>id%d ---> %s%n</h3>"
//                    .formatted(i.userName(),i.id(),i.regDate())));
//        }
        req.setAttribute("company", service.findAllByEmployeeId(companyId));
        req.getRequestDispatcher("/user.jsp").forward(req,resp);
    }
}
