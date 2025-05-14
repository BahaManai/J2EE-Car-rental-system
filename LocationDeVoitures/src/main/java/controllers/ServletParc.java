package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.ModelParc;
import entities.Parc;
import java.util.List;
/**
 * Servlet implementation class ServletParc
 */
@WebServlet(urlPatterns = {
    "/admin/ajoutParc",
    "/admin/updateParc",
    "/admin/deleteParc",
    "/admin/listeParcs",
    "/admin/formAjoutParc",
    "/admin/formModifierParc"
})
public class ServletParc extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModelParc modelParc = new ModelParc();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletParc() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/admin/ajoutParc":
                ajouterParc(request, response);
                break;
            case "/admin/updateParc":
                modifierParc(request, response);
                break;
            case "/admin/deleteParc":
                supprimerParc(request, response);
                break;
            case "/admin/listeParcs":
                afficherListeParcs(request, response);
                break;
            case "/admin/formAjoutParc":
                afficherFormAjoutParc(request, response);
                break;
            case "/admin/formModifierParc":
                afficherFormModifierParc(request, response);
                break;
        }
    }

    private void ajouterParc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nom = request.getParameter("nomParc");
        String libelle = request.getParameter("libelle");
        int capacite = Integer.parseInt(request.getParameter("capacite"));

        Parc parc = new Parc();
        parc.setNomParc(nom);
        parc.setLibelle(libelle);
        parc.setCapacite(capacite);

        modelParc.setParc(parc);
        modelParc.ajouterParc();

        response.sendRedirect(request.getContextPath() + "/admin/Parc/reussit.html?action=ajout");
    }

    private void modifierParc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeParc = Integer.parseInt(request.getParameter("codeParc"));
        String nom = request.getParameter("nomParc");
        String libelle = request.getParameter("libelle");
        int capacite = Integer.parseInt(request.getParameter("capacite"));

        Parc parc = new Parc();
        parc.setCodeParc(codeParc);
        parc.setNomParc(nom);
        parc.setLibelle(libelle);
        parc.setCapacite(capacite);

        modelParc.setParc(parc);
        modelParc.modifierParc();

        response.sendRedirect(request.getContextPath() + "/admin/Parc/reussit.html?action=modification");
    }

    private void supprimerParc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeParc = Integer.parseInt(request.getParameter("codeParc"));
        modelParc.supprimerParc(codeParc);
        response.sendRedirect(request.getContextPath() + "/admin/Parc/reussit.html?action=suppression");
    }

    private void afficherListeParcs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Parc> parcs = modelParc.getAllParcs();
        request.setAttribute("parcs", parcs);
        request.setAttribute("page", "admin/Parc/listeParcs.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    private void afficherFormAjoutParc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("page", "admin/Parc/ajoutParc.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }

    private void afficherFormModifierParc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int codeParc = Integer.parseInt(idParam);
                Parc parc = modelParc.getParcById(codeParc);
                if (parc != null) {
                    request.setAttribute("parc", parc);
                    request.setAttribute("page", "admin/Parc/modifierParc.jsp");
                    request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/listeParcs?error=parc_not_found");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}