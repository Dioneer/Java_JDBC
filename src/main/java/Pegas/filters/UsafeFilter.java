package Pegas.filters;

import Pegas.DTO.CreateUserDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/admin")
public class UsafeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var user = (CreateUserDTO)((HttpServletRequest) request).getSession().getAttribute("user");
        if(user != null){
            chain.doFilter(request, response);
        } else{
            ((HttpServletResponse)response).sendRedirect("/registration");
        }
    }
}
