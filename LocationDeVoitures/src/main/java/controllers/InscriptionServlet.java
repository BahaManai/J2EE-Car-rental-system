package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelClient;
import entities.Client;
import java.io.IOException;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModelClient modelClient = new ModelClient();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/inscription.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération des paramètres du formulaire
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String cin = request.getParameter("CIN");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        int age;

        // Validation des champs requis
        if (nom == null || prenom == null || cin == null || adresse == null ||
                email == null || tel == null || request.getParameter("age") == null ||
                password == null || passwordConfirmation == null) {
            request.setAttribute("error", "Tous les champs sont obligatoires");
            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
            return;
        }

        // Validation de l'âge
        try {
            age = Integer.parseInt(request.getParameter("age"));
            if (age < 18 || age > 99) {
                request.setAttribute("error", "L'âge doit être compris entre 18 et 99 ans");
                request.getRequestDispatcher("/inscription.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "L'âge doit être un nombre valide");
            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
            return;
        }

        // Validation du format du téléphone (8 chiffres)
        if (!tel.matches("^[0-9]{8}$")) {
            request.setAttribute("error", "Le numéro de téléphone doit contenir 8 chiffres");
            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
            return;
        }

        // Validation du mot de passe
        if (password.length() < 6) {
            request.setAttribute("error", "Le mot de passe doit contenir au moins 6 caractères");
            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
            return;
        }
        if (!password.equals(passwordConfirmation)) {
            request.setAttribute("error", "Les mots de passe ne correspondent pas");
            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
            return;
        }

        // Création et sauvegarde du client
        Client client = new Client(0, cin, nom, prenom, adresse, email, tel, age);
        client.setMotDePasse(password);
        client.setRole("CLIENT"); // Définir le rôle par défaut

        modelClient.setClient(client);
        modelClient.ajouterClient();

        // Redirection vers la page de connexion avec un message de succès
        response.sendRedirect(request.getContextPath() + "/login?success=inscription");
    }
}