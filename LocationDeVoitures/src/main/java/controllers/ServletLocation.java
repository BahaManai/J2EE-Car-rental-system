package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLocation;
import model.ModelClient;
import model.ModelVoiture;
import entities.Location;
import entities.Client;
import entities.Voiture;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Servlet implementation class ServletLocation
 */
@WebServlet(urlPatterns = {
    "/admin/listeLocations", "/admin/formAjoutLocation", "/admin/ajoutLocation",
    "/admin/formModifierLocation", "/admin/updateLocation", "/admin/deleteLocation"
})
public class ServletLocation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModelLocation modelLocation = new ModelLocation();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLocation() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/admin/ajoutLocation":
                ajouterLocation(request, response);
                break;
            case "/admin/updateLocation":
                modifierLocation(request, response);
                break;
            case "/admin/deleteLocation":
                supprimerLocation(request, response);
                break;
            case "/admin/listeLocations":
                afficherListeLocations(request, response);
                break;
            case "/admin/formModifierLocation":
                afficherFormModification(request, response);
                break;
            case "/admin/formAjoutLocation":
                afficherFormAjoutLocation(request, response);
                break;
        }
    }

    private void ajouterLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeLocation = Integer.parseInt(request.getParameter("codeLocation"));
        int codeClient = Integer.parseInt(request.getParameter("codeClient"));
        int codeVoiture = Integer.parseInt(request.getParameter("codeVoiture"));
        String dateDebStr = request.getParameter("dateDeb");
        String dateFinStr = request.getParameter("dateFin");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateDeb = sdf.parse(dateDebStr);
            java.util.Date dateFin = sdf.parse(dateFinStr);

            Client client = new Client();
            client.setCodeClient(codeClient);
            Voiture voiture = new Voiture();
            voiture.setCodeVoiture(codeVoiture);

            Location location = new Location(codeLocation, voiture, client, dateDeb, dateFin);
            modelLocation.setLocation(location);
            modelLocation.ajouterLocation();

            response.sendRedirect(request.getContextPath() + "/admin/Location/reussit.html?action=ajoutLocation");
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutLocation?error=date_format");
        }
    }

    private void modifierLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeLocation = Integer.parseInt(request.getParameter("codeLocation"));
        int codeClient = Integer.parseInt(request.getParameter("codeClient"));
        int codeVoiture = Integer.parseInt(request.getParameter("codeVoiture"));
        String dateDebStr = request.getParameter("dateDeb");
        String dateFinStr = request.getParameter("dateFin");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateDeb = sdf.parse(dateDebStr);
            java.util.Date dateFin = sdf.parse(dateFinStr);

            Client client = new Client();
            client.setCodeClient(codeClient);
            Voiture voiture = new Voiture();
            voiture.setCodeVoiture(codeVoiture);

            Location location = new Location(codeLocation, voiture, client, dateDeb, dateFin);
            modelLocation.setLocation(location);
            modelLocation.modifierLocation();

            response.sendRedirect(request.getContextPath() + "/admin/Location/reussit.html?action=modificationLocation");
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/formModifierLocation?error=date_format");
        }
    }

    private void supprimerLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeLocation = Integer.parseInt(request.getParameter("codeLocation"));
        modelLocation.supprimerLocation(codeLocation);
        response.sendRedirect(request.getContextPath() + "/admin/Location/reussit.html?action=suppressionLocation");
    }

    private void afficherListeLocations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Location> locations = modelLocation.listeLocations();
        request.setAttribute("locations", locations);
        request.setAttribute("page", "admin/Location/listeLocations.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    private void afficherFormModification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int codeLocation = Integer.parseInt(idParam);
                Location location = modelLocation.getLocationById(codeLocation);
                if (location != null) {
                    request.setAttribute("location", location);
                    ModelClient modelClient = new ModelClient();
                    ModelVoiture modelVoiture = new ModelVoiture();
                    request.setAttribute("clients", modelClient.listeClients());
                    request.setAttribute("voitures", modelVoiture.listeVoitures());
                    request.setAttribute("page", "admin/Location/modifierLocation.jsp");
                    request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/listeLocations?error=location_not_found");
    }

    private void afficherFormAjoutLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ModelClient modelClient = new ModelClient();
        ModelVoiture modelVoiture = new ModelVoiture();
        List<Client> clients = modelClient.listeClients();
        List<Voiture> voitures = modelVoiture.listeVoitures();

        request.setAttribute("clients", clients);
        request.setAttribute("voitures", voitures);
        request.setAttribute("page", "admin/Location/ajoutLocation.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}