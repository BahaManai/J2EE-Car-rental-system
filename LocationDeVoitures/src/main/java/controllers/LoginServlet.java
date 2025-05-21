package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utilitaire.PasswordUtil;
import entities.Client;
import dao.IDaoClient;
import dao.ImpDaoClient;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IDaoClient clientDao;

    public void init() {
        clientDao = new ImpDaoClient();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        // First find client by email only
        Client client = clientDao.findByEmail(email);
        
        if (client != null) {
            // Verify password with stored hash and salt
            boolean passwordMatch = PasswordUtil.verifyPassword(
                motDePasse, 
                client.getMotDePasse(), 
                client.getSalt()
            );
            
            if (passwordMatch) {
                HttpSession session = request.getSession();
                session.setAttribute("user", client);
                session.setAttribute("role", client.getRole());
                session.setAttribute("userId", client.getCodeClient());

                if ("admin".equals(client.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/client/home");
                }
                return;
            }
        }
        
        // If we get here, authentication failed
        request.setAttribute("error", "Email ou mot de passe incorrect.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}