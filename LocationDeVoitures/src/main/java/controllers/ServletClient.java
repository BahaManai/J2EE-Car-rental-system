package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelClient;
import model.ModelLocation;
import model.ModelParc;
import model.ModelVoiture;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Client;

@WebServlet(urlPatterns = {
    "/admin/ajoutClient", "/admin/updateClient", "/admin/deleteClient",
    "/admin/listeClients", "/admin/formModifierClient", "/admin/formAjoutClient", "/admin/dashboard"
})
public class ServletClient extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ModelClient modelClient = new ModelClient();
    private ModelLocation modelLocation = new ModelLocation();
    private ModelParc modelParc = new ModelParc();
    private ModelVoiture modelVoiture = new ModelVoiture();

    public ServletClient() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/admin/ajoutClient":
                ajouterClient(request, response);
                break;
            case "/admin/updateClient":
                modifierClient(request, response);
                break;
            case "/admin/deleteClient":
                supprimerClient(request, response);
                break;
            case "/admin/listeClients":
                afficherListeClients(request, response);
                break;
            case "/admin/formAjoutClient":
                afficherFormAjoutClient(request, response);
                break;
            case "/admin/formModifierClient":
                afficherFormModifierClient(request, response);
                break;
            case "/admin/dashboard":
                afficherAdminDashboard(request, response);
                break;
        }
    }

    private void ajouterClient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String cin = request.getParameter("CIN");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        int age;
        
        if (nom == null || prenom == null || cin == null || adresse == null || 
            email == null || tel == null || request.getParameter("age") == null ||
            password == null || passwordConfirmation == null) {
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutClient?error=missing_fields");
            return;
        }

        try {
            age = Integer.parseInt(request.getParameter("age"));
            if (age < 18 || age > 99) {
                response.sendRedirect(request.getContextPath() + "/admin/formAjoutClient?error=invalid_age");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutClient?error=invalid_age");
            return;
        }

        if (!tel.matches("^[0-9]{8}$")) {
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutClient?error=invalid_tel");
            return;
        }

        if (password.length() < 6) {
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutClient?error=short_password");
            return;
        }
        if (!password.equals(passwordConfirmation)) {
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutClient?error=password_mismatch");
            return;
        }

        Client client = new Client(0, cin, nom, prenom, adresse, email, tel, age);
        client.setMotDePasse(password);
        modelClient.setClient(client);
        modelClient.ajouterClient();

        response.sendRedirect(request.getContextPath() + "/admin/Client/reussit.html?action=ajout");
    }

    private void modifierClient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String codeClientParam = request.getParameter("codeClient");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String cin = request.getParameter("CIN");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        int codeClient;
        int age;

        if (codeClientParam == null || nom == null || prenom == null || cin == null || 
            adresse == null || email == null || tel == null || request.getParameter("age") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/formModifierClient?id=" + 
                request.getParameter("codeClient") + "&error=missing_fields");
            return;
        }

        try {
            codeClient = Integer.parseInt(codeClientParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/listeClients?error=invalid_client_id");
            return;
        }

        try {
            age = Integer.parseInt(request.getParameter("age"));
            if (age < 18 || age > 99) {
                response.sendRedirect(request.getContextPath() + "/admin/formModifierClient?id=" + 
                    codeClient + "&error=invalid_age");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/formModifierClient?id=" + 
                codeClient + "&error=invalid_age");
            return;
        }

        if (!tel.matches("^[0-9]{8}$")) {
            response.sendRedirect(request.getContextPath() + "/admin/formModifierClient?id=" + 
                codeClient + "&error=invalid_tel");
            return;
        }

        if (password != null && !password.isEmpty()) {
            if (password.length() < 6) {
                response.sendRedirect(request.getContextPath() + "/admin/formModifierClient?id=" + 
                    codeClient + "&error=short_password");
                return;
            }
            if (!password.equals(passwordConfirmation)) {
                response.sendRedirect(request.getContextPath() + "/admin/formModifierClient?id=" + 
                    codeClient + "&error=password_mismatch");
                return;
            }
        }

        Client client = new Client(codeClient, cin, nom, prenom, adresse, email, tel, age);
        if (password != null && !password.isEmpty()) {
            client.setMotDePasse(password);
        }

        modelClient.setClient(client);
        modelClient.modifierClient();

        response.sendRedirect(request.getContextPath() + "/admin/Client/reussit.html?action=modification");
    }

    private void supprimerClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeClient = Integer.parseInt(request.getParameter("codeClient"));
        modelClient.supprimerClient(codeClient);
        response.sendRedirect(request.getContextPath() + "/admin/Client/reussit.html?action=suppression");
    }

    private void afficherListeClients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clients = modelClient.listeClients();
        request.setAttribute("clients", clients);
        request.setAttribute("page", "admin/Client/listeClients.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }
    
    private void afficherFormAjoutClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("page", "admin/Client/ajoutClient.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }
    
    private void afficherAdminDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Statistiques de base
        int clientCount = modelClient.countClients();
        int locationCount = modelLocation.countLocations();
        int activeLocations = modelLocation.countActiveLocations();
        double totalRevenue = modelLocation.calculateTotalRevenue();
        int voitureCount = modelVoiture.countVoitures();
        double occupancyRate = modelVoiture.calculateOccupancyRate();

        // Données pour les graphiques
        List<String> parcNames = modelParc.getParcNames();
        List<Double> revenuePerParc = modelLocation.getRevenuePerParc();
        List<String> revenueMonths = modelLocation.getRevenueMonths();
        List<Double> revenueEvolutionData = modelLocation.getMonthlyRevenue();

        // Données pour le graphique des voitures les plus réservées
        List<Object[]> mostReservedCars = modelVoiture.getMostReservedCars();
        List<String> mostReservedCarLabels = new ArrayList<>();
        List<Integer> mostReservedCarCounts = new ArrayList<>();
        for (Object[] car : mostReservedCars) {
            mostReservedCarLabels.add((String) car[0]);
            mostReservedCarCounts.add((Integer) car[1]);
        }

        // Définir les attributs de la requête
        request.setAttribute("clientCount", clientCount);
        request.setAttribute("activeLocations", activeLocations);
        request.setAttribute("totalRevenue", String.format("%.2f", totalRevenue));
        request.setAttribute("occupancyRate", String.format("%.1f", occupancyRate));
        request.setAttribute("parcNames", new Gson().toJson(parcNames));
        request.setAttribute("revenuePerParc", new Gson().toJson(revenuePerParc));
        request.setAttribute("mostReservedCarLabels", new Gson().toJson(mostReservedCarLabels));
        request.setAttribute("mostReservedCarCounts", new Gson().toJson(mostReservedCarCounts));
        request.setAttribute("revenueMonths", new Gson().toJson(revenueMonths));
        request.setAttribute("revenueEvolutionData", new Gson().toJson(revenueEvolutionData));

        request.setAttribute("page", "admin/dashbord.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    private void afficherFormModifierClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int codeClient = Integer.parseInt(idParam);
                Client client = modelClient.getClientById(codeClient);
                if (client != null) {
                    request.setAttribute("client", client);
                    request.setAttribute("page", "admin/Client/modifierClient.jsp");
                    request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/listeClients?error=client_not_found");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}