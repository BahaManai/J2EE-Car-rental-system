/* Protects admin and client pages, redirecting unauthenticated users to the login page. */
package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/client/*"})
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
        String role = isLoggedIn ? (String) session.getAttribute("role") : null;
        String path = httpRequest.getServletPath();

        // Allow unauthenticated access to /client/home and /login
        if (path.equals("/client/home") || path.equals("/login.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // Redirect unauthenticated users to login
        if (!isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // Restrict /admin to admin role
        if (path.startsWith("/admin") && !"admin".equals(role)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/client/home");
            return;
        }

        // Restrict /client to client role
        if (path.startsWith("/client") && !"client".equals(role)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/dashbord.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}