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

        if (!isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        if (path.startsWith("/admin") && !"admin".equals(role)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/Client/dashbord.jsp");
            return;
        }

        if (path.startsWith("/client") && !"client".equals(role)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/dashbord.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}