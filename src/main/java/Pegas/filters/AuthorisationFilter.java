package Pegas.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthorisationFilter implements Filter {

    private static final String REGISTRATION = "/registration";
    private static final Set<String> PUBLIC_PATH = Set.of("/login", REGISTRATION);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) request).getRequestURI();
        if(isPublishPath(uri) || isUserLoggeIn(request)){
            chain.doFilter(request, response);
        }else{
            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }

    private boolean isUserLoggeIn(ServletRequest request) {
        var user = ((HttpServletRequest) request).getSession().getAttribute("user");
        return user!=null;
    }

    private boolean isPublishPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(i->uri.startsWith(i));
    }
}
