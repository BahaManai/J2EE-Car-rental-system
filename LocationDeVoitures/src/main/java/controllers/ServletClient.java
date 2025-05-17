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

import java.io.IOException;
import java.util.List;

import entities.Client;

/**
 * Servlet implementation class ServletClient
 */
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

    private void ajouterClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String cin = request.getParameter("CIN");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        int age = Integer.parseInt(request.getParameter("age"));

        Client client = new Client(0, cin, nom, prenom, adresse, email, tel, age);
        modelClient.setClient(client);
        modelClient.ajouterClient();

        response.sendRedirect(request.getContextPath() + "/admin/Client/reussit.html?action=ajout");
    }

    private void modifierClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeClient = Integer.parseInt(request.getParameter("codeClient"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String cin = request.getParameter("CIN");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        int age = Integer.parseInt(request.getParameter("age"));

        Client client = new Client(codeClient, cin, nom, prenom, adresse, email, tel, age);
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
        // Récupérer les comptes pour les graphiques
        int clientCount = modelClient.countClients();
        int locationCount = modelLocation.countLocations();
        int parcCount = modelParc.countParcs();
        int voitureCount = modelVoiture.countVoitures();
        
        // Supposons que les locations actives sont celles où la date de fin est dans le futur
        int activeLocations = modelLocation.countActiveLocations();
        int completedLocations = locationCount - activeLocations;

        // Définir les attributs de la requête pour le JSP
        request.setAttribute("clientCount", clientCount);
        request.setAttribute("locationCount", locationCount);
        request.setAttribute("parcCount", parcCount);
        request.setAttribute("voitureCount", voitureCount);
        request.setAttribute("activeLocations", activeLocations);
        request.setAttribute("completedLocations", completedLocations);

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