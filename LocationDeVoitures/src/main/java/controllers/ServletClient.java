package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelClient;

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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletClient() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
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
                afficherAdminDashbord(request, response);
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
    
    private void afficherAdminDashbord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("page", "admin/dashbord.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    private void afficherFormModifierClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int codeClient = Integer.parseInt(idParam);
                ModelClient model = new ModelClient();
                Client client = model.getClientById(codeClient);
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

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}