package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import entities.Client;
import dao.IDaoClient;
import dao.ImpDaoClient;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IDaoClient clientDao;

    public void init() {
        clientDao = new ImpDaoClient();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get form data
        String cin = request.getParameter("cin");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        int age = Integer.parseInt(request.getParameter("age"));
        String motDePasse = request.getParameter("motDePasse");

        // 2. Check if email already exists (you might want to add this method to IDaoClient)
        Client existingClient = clientDao.findByEmailAndPassword(email, motDePasse);
        if (existingClient != null) {
            request.setAttribute("error", "Email déjà utilisé");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 3. Create new client
        Client newClient = new Client();
        newClient.setCIN(cin);
        newClient.setNom(nom);
        newClient.setPrenom(prenom);
        newClient.setAdresse(adresse);
        newClient.setEmail(email);
        newClient.setTel(tel);
        newClient.setAge(age);
        newClient.setMotDePasse(motDePasse); // Note: In production, hash this password!
        newClient.setRole("client"); // Default role

        // 4. Save to database
        clientDao.ajouterClient(newClient);

        // 5. Auto-login (optional)
        HttpSession session = request.getSession();
        session.setAttribute("user", newClient);
        session.setAttribute("role", "client");

        // 6. Redirect to client home
        response.sendRedirect(request.getContextPath() + "/client/home");
    }
}