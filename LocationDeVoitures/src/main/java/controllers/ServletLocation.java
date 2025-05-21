package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

@WebServlet(urlPatterns = {
    "/admin/listeLocations", "/admin/formAjoutLocation", "/admin/ajoutLocation",
    "/admin/formModifierLocation", "/admin/updateLocation", "/admin/deleteLocation",
    "/admin/updateLocationStatus", "/client/home", "/client/ajoutLocation", 
    "/client/listeReservations", "/client/cancelLocation"
})
public class ServletLocation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModelLocation modelLocation = new ModelLocation();
    private ModelClient modelClient = new ModelClient();
    private ModelVoiture modelVoiture = new ModelVoiture();

    public ServletLocation() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/admin/ajoutLocation":
                ajouterLocation(request, response);
                break;
            case "/admin/updateLocation":
                modifierLocation(request, response);
                break;
            case "/admin/updateLocationStatus":
                updateLocationStatus(request, response);
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
            case "/client/home":
                afficherHome(request, response);
                break;
            case "/client/ajoutLocation":
                ajouterLocationClient(request, response);
                break;
            case "/client/listeReservations":
                afficherListeReservations(request, response);
                break;
            case "/client/cancelLocation":
                cancelLocationClient(request, response);
                break;
        }
    }

    private void afficherHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        request.setAttribute("isAuthenticated", userId != null);

        List<Voiture> voitures = modelVoiture.listeVoitures();
        request.setAttribute("voitures", voitures);
        request.setAttribute("page", "Client/home.jsp");
        request.getRequestDispatcher("/clientLayout.jsp").forward(request, response);
    }

    private void ajouterLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeClient = Integer.parseInt(request.getParameter("codeClient"));
        int codeVoiture = Integer.parseInt(request.getParameter("codeVoiture"));
        String dateDebStr = request.getParameter("dateDeb");
        String dateFinStr = request.getParameter("dateFin");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateDeb = sdf.parse(dateDebStr);
            java.util.Date dateFin = sdf.parse(dateFinStr);

            // Validate dates
            if (dateDeb.after(dateFin) || dateDeb.before(new java.util.Date())) {
                response.sendRedirect(request.getContextPath() + "/admin/formAjoutLocation?error=invalid_dates");
                return;
            }

            Client client = modelClient.getClientById(codeClient);
            Voiture voiture = modelVoiture.getVoitureById(codeVoiture);

            if (client == null || voiture == null) {
                response.sendRedirect(request.getContextPath() + "/admin/formAjoutLocation?error=invalid_client_or_voiture");
                return;
            }

            // Check car availability
            if (!modelLocation.estVoitureDisponible(codeVoiture, dateDeb, dateFin)) {
                response.sendRedirect(request.getContextPath() + "/admin/formAjoutLocation?error=voiture_unavailable");
                return;
            }

            // Admin-added locations are assumed to be accepted
            Location location = new Location(0, voiture, client, dateDeb, dateFin, "accepté");
            modelLocation.setLocation(location);
            modelLocation.ajouterLocation();

            response.sendRedirect(request.getContextPath() + "/admin/Location/reussit.html?action=ajout");
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutLocation?error=date_format");
        }
    }

    private void ajouterLocationClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=not_logged_in");
            return;
        }

        String codeVoitureStr = request.getParameter("codeVoiture");
        String dateDebStr = request.getParameter("dateDeb");
        String dateFinStr = request.getParameter("dateFin");

        if (codeVoitureStr == null || codeVoitureStr.trim().isEmpty() ||
            dateDebStr == null || dateDebStr.trim().isEmpty() ||
            dateFinStr == null || dateFinStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/client/home?error=missing_parameters");
            return;
        }

        try {
            int codeVoiture = Integer.parseInt(codeVoitureStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateDeb = sdf.parse(dateDebStr);
            java.util.Date dateFin = sdf.parse(dateFinStr);

            if (dateDeb.after(dateFin) || dateDeb.before(new java.util.Date())) {
                response.sendRedirect(request.getContextPath() + "/client/home?error=invalid_dates");
                return;
            }

            Client client = modelClient.getClientById(userId);
            Voiture voiture = modelVoiture.getVoitureById(codeVoiture);

            if (client == null || voiture == null) {
                response.sendRedirect(request.getContextPath() + "/client/home?error=invalid_client_or_voiture");
                return;
            }

            if (!modelLocation.estVoitureDisponible(codeVoiture, dateDeb, dateFin)) {
                response.sendRedirect(request.getContextPath() + "/client/home?error=voiture_unavailable");
                return;
            }

            Location location = new Location(0, voiture, client, dateDeb, dateFin, "en attente");
            modelLocation.setLocation(location);
            modelLocation.ajouterLocation();

            response.sendRedirect(request.getContextPath() + "/client/listeReservations?success=reservation_added");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/client/home?error=invalid_code_voiture");
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/client/home?error=date_format");
        }
    }

    private void modifierLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeLocation = Integer.parseInt(request.getParameter("codeLocation"));
        int codeClient = Integer.parseInt(request.getParameter("codeClient"));
        int codeVoiture = Integer.parseInt(request.getParameter("codeVoiture"));
        String dateDebStr = request.getParameter("dateDeb");
        String dateFinStr = request.getParameter("dateFin");
        String statut = request.getParameter("statut");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateDeb = sdf.parse(dateDebStr);
            java.util.Date dateFin = sdf.parse(dateFinStr);

            // Validate dates
            if (dateDeb.after(dateFin) || dateDeb.before(new java.util.Date())) {
                response.sendRedirect(request.getContextPath() + "/admin/formModifierLocation?error=invalid_dates");
                return;
            }

            Client client = modelClient.getClientById(codeClient);
            Voiture voiture = modelVoiture.getVoitureById(codeVoiture);

            if (client == null || voiture == null) {
                response.sendRedirect(request.getContextPath() + "/admin/formModifierLocation?error=invalid_client_or_voiture");
                return;
            }

            // Validate statut
            if (!"en attente".equals(statut) && !"accepté".equals(statut) && !"refusé".equals(statut)) {
                response.sendRedirect(request.getContextPath() + "/admin/formModifierLocation?error=invalid_statut");
                return;
            }

            // Check car availability if statut is not refusé
            if (!"refusé".equals(statut)) {
                if (!modelLocation.estVoitureDisponible(codeVoiture, dateDeb, dateFin)) {
                    response.sendRedirect(request.getContextPath() + "/admin/formModifierLocation?error=voiture_unavailable");
                    return;
                }
            }

            Location location = new Location(codeLocation, voiture, client, dateDeb, dateFin, statut);
            modelLocation.setLocation(location);
            modelLocation.modifierLocation();

            response.sendRedirect(request.getContextPath() + "/admin/Location/reussit.html?action=modification");
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/formModifierLocation?error=date_format");
        }
    }

    private void updateLocationStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeLocation = Integer.parseInt(request.getParameter("codeLocation"));
        String statut = request.getParameter("statut");

        if (!"accepté".equals(statut) && !"refusé".equals(statut)) {
            response.sendRedirect(request.getContextPath() + "/admin/listeLocations?error=invalid_statut");
            return;
        }

        if ("accepté".equals(statut)) {
            Location location = modelLocation.getLocationById(codeLocation);
            if (location != null && !modelLocation.estVoitureDisponible(
                location.getVoiture().getCodeVoiture(),
                location.getDateDeb(),
                location.getDateFin()
            )) {
                response.sendRedirect(request.getContextPath() + "/client/listeReservations?error=voiture_unavailable");
                return;
            }
        }

        modelLocation.updateLocationStatus(codeLocation, statut);
        response.sendRedirect(request.getContextPath() + "/admin/Location/reussit.html?action=statut_" + statut);
    }

    private void supprimerLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeLocation = Integer.parseInt(request.getParameter("codeLocation"));
        modelLocation.supprimerLocation(codeLocation);
        response.sendRedirect(request.getContextPath() + "/admin/Location/reussit.html?action=suppression");
    }

    private void cancelLocationClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=not_logged_in");
            return;
        }

        String codeLocationStr = request.getParameter("codeLocation");
        if (codeLocationStr == null || codeLocationStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/client/listeReservations?error=missing_code_location");
            return;
        }

        try {
            int codeLocation = Integer.parseInt(codeLocationStr);
            Location location = modelLocation.getLocationById(codeLocation);

            if (location == null) {
                response.sendRedirect(request.getContextPath() + "/client/listeReservations?error=location_not_found");
                return;
            }

            if (location.getClient().getCodeClient() != userId) {
                response.sendRedirect(request.getContextPath() + "/client/listeReservations?error=unauthorized");
                return;
            }

            if (!"en attente".equals(location.getStatut()) && !"accepté".equals(location.getStatut())) {
                response.sendRedirect(request.getContextPath() + "/client/listeReservations?error=cannot_cancel");
                return;
            }

            modelLocation.supprimerLocation(codeLocation);
            response.sendRedirect(request.getContextPath() + "/client/listeReservations?success=reservation_cancelled");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/client/listeReservations?error=invalid_code_location");
        }
    }

    private void afficherListeLocations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Location> locations = modelLocation.listeLocations();
        request.setAttribute("locations", locations);
        request.setAttribute("page", "admin/Location/listeLocations.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    private void afficherListeReservations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=not_logged_in");
            return;
        }

        List<Location> reservations = modelLocation.getLocationsByClientId(userId);
        request.setAttribute("reservations", reservations);
        request.setAttribute("page", "Client/reservations.jsp");
        request.getRequestDispatcher("/clientLayout.jsp").forward(request, response);
    }

    private void afficherFormModification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int codeLocation = Integer.parseInt(idParam);
                Location location = modelLocation.getLocationById(codeLocation);
                if (location != null) {
                    request.setAttribute("location", location);
                    List<Client> clients = modelClient.listeClients();
                    List<Voiture> voitures = modelVoiture.listeVoitures();
                    request.setAttribute("clients", clients);
                    request.setAttribute("voitures", voitures);
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
        List<Client> clients = modelClient.listeClients();
        List<Voiture> voitures = modelVoiture.listeVoitures();

        request.setAttribute("clients", clients);
        request.setAttribute("voitures", voitures);
        request.setAttribute("page", "admin/Location/ajoutLocation.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}