package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelParc;
import model.ModelVoiture;
import java.io.IOException;
import java.util.List;
import entities.Parc;
import entities.Voiture;

@WebServlet(urlPatterns = {
    "/admin/ajoutVoiture", "/admin/updateVoiture", "/admin/deleteVoiture",
    "/admin/listeVoitures", "/admin/formModifierVoiture", "/admin/formAjoutVoiture"
})
public class ServletVoiture extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModelVoiture modelVoiture = new ModelVoiture();

    public ServletVoiture() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/admin/ajoutVoiture":
                ajouterVoiture(request, response);
                break;
            case "/admin/updateVoiture":
                modifierVoiture(request, response);
                break;
            case "/admin/deleteVoiture":
                supprimerVoiture(request, response);
                break;
            case "/admin/listeVoitures":
                afficherListeVoitures(request, response);
                break;
            case "/admin/formModifierVoiture":
                afficherFormModification(request, response);
                break;
            case "/admin/formAjoutVoiture":
                afficherFormAjoutVoiture(request, response);
                break;
        }
    }

    private void ajouterVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String matricule = request.getParameter("matricule");
            String model = request.getParameter("model");
            float kilometrage = Float.parseFloat(request.getParameter("kilometrage"));
            float prixParJour = Float.parseFloat(request.getParameter("prix_par_jour"));
            String image = request.getParameter("image");
            int codeParc = Integer.parseInt(request.getParameter("code_parc"));

            // Validate prix_par_jour
            if (prixParJour < 0) {
                response.sendRedirect(request.getContextPath() + "/admin/formAjoutVoiture?error=invalid_price");
                return;
            }

            Parc parc = new Parc();
            parc.setCodeParc(codeParc);

            Voiture voiture = new Voiture(0, matricule, model, kilometrage, prixParJour, image, parc);
            modelVoiture.setVoiture(voiture);
            modelVoiture.ajouterVoiture();

            response.sendRedirect(request.getContextPath() + "/admin/Voiture/reussit.html?action=ajoutVoiture");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutVoiture?error=invalid_input");
        }
    }

    private void modifierVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int codeVoiture = Integer.parseInt(request.getParameter("codevoiture"));
            String matricule = request.getParameter("matricule");
            String model = request.getParameter("model");
            float kilometrage = Float.parseFloat(request.getParameter("kilometrage"));
            float prixParJour = Float.parseFloat(request.getParameter("prix_par_jour"));
            String image = request.getParameter("image");
            int codeParc = Integer.parseInt(request.getParameter("codeParc"));

            // Validate prix_par_jour
            if (prixParJour < 0) {
                response.sendRedirect(request.getContextPath() + "/admin/formModifierVoiture?id=" + codeVoiture + "&error=invalid_price");
                return;
            }

            Parc parc = new Parc();
            parc.setCodeParc(codeParc);

            Voiture voiture = new Voiture(codeVoiture, matricule, model, kilometrage, prixParJour, image, parc);
            modelVoiture.setVoiture(voiture);
            modelVoiture.modifierVoiture();

            response.sendRedirect(request.getContextPath() + "/admin/Voiture/reussit.html?action=modificationVoiture");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/formModifierVoiture?id=" + request.getParameter("codevoiture") + "&error=invalid_input");
        }
    }

    private void supprimerVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeVoiture = Integer.parseInt(request.getParameter("codevoiture"));
        modelVoiture.supprimerVoiture(codeVoiture);
        response.sendRedirect(request.getContextPath() + "/admin/Voiture/reussit.html?action=suppressionVoiture");
    }

    private void afficherListeVoitures(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Voiture> voitures = modelVoiture.listeVoitures();
        request.setAttribute("voitures", voitures);
        request.setAttribute("page", "admin/Voiture/listeVoitures.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    private void afficherFormModification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int codeVoiture = Integer.parseInt(idParam);
                ModelVoiture model = new ModelVoiture();
                Voiture voiture = model.getVoitureById(codeVoiture);
                if (voiture != null) {
                    request.setAttribute("voiture", voiture);
                    ModelParc modelParc = new ModelParc();
                    request.setAttribute("parcs", modelParc.getAllParcs());
                    request.setAttribute("page", "admin/Voiture/modifierVoiture.jsp");
                    request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/listeVoitures?error=voiture_not_found");
    }

    private void afficherFormAjoutVoiture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ModelParc modelParc = new ModelParc();
        List<Parc> parcs = modelParc.getAllParcs();
        request.setAttribute("parcs", parcs);
        request.setAttribute("page", "admin/Voiture/ajoutVoiture.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}