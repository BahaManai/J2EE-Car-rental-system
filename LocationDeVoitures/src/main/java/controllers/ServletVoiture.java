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

/**
 * Servlet implementation class ServletVoiture
 */
@WebServlet(urlPatterns = {"/ajoutVoiture", "/updateVoiture", "/deleteVoiture", "/listeVoitures", "/formModifierVoiture", "/formAjoutVoiture"})
public class ServletVoiture extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModelVoiture modelVoiture = new ModelVoiture();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletVoiture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
        switch (path) {
            case "/ajoutVoiture":
                ajouterVoiture(request, response);
                break;
            case "/updateVoiture":
                modifierVoiture(request, response);
                break;
            case "/deleteVoiture":
                supprimerVoiture(request, response);
                break;
            case "/listeVoitures":
                afficherListeVoitures(request, response);
                break;
            case "/formModifierVoiture":
                afficherFormModification(request, response);
                break;
            case "/formAjoutVoiture":
                afficherFormAjoutVoiture(request, response);
                break;

        }
	}

	private void ajouterVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String matricule = request.getParameter("matricule");
        String model = request.getParameter("model");
        float kilometrage = Float.parseFloat(request.getParameter("kilometrage"));
        int codeParc = Integer.parseInt(request.getParameter("code_parc"));
        Parc parc = new Parc();
        parc.setCodeParc(codeParc);

        Voiture voiture = new Voiture(0, matricule, model, kilometrage, parc);
        modelVoiture.setVoiture(voiture);
        modelVoiture.ajouterVoiture();

        response.sendRedirect("Voiture/reussit.html?action=ajoutVoiture");
    }

    private void modifierVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeVoiture = Integer.parseInt(request.getParameter("codevoiture"));
        String matricule = request.getParameter("matricule");
        String model = request.getParameter("model");
        float kilometrage = Float.parseFloat(request.getParameter("kilometrage"));
        int codeParc = Integer.parseInt(request.getParameter("codeParc"));
        Parc parc = new Parc();
        parc.setCodeParc(codeParc);

        Voiture voiture = new Voiture(codeVoiture, matricule, model, kilometrage, parc);
        modelVoiture.setVoiture(voiture);
        modelVoiture.modifierVoiture();

        response.sendRedirect("Voiture/reussit.html?action=modificationVoiture");
    }

    private void supprimerVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeVoiture = Integer.parseInt(request.getParameter("codevoiture"));
        modelVoiture.supprimerVoiture(codeVoiture);
        response.sendRedirect("Voiture/reussit.html?action=suppressionVoiture");
    }

    private void afficherListeVoitures(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Voiture> voitures = modelVoiture.listeVoitures();
        request.setAttribute("voitures", voitures);
        request.setAttribute("page", "Voiture/listeVoitures.jsp");
	    request.getRequestDispatcher("adminLayout.jsp").forward(request, response);
    }
    
    private void afficherFormModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                    request.setAttribute("page", "Voiture/modifierVoiture.jsp"); 
                    request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }

        response.sendRedirect("listeVoitures?error=voiture_not_found");
    }

    private void afficherFormAjoutVoiture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelParc modelParc = new ModelParc();
        List<Parc> parcs = modelParc.getAllParcs();
        
        request.setAttribute("parcs", parcs);
        request.setAttribute("page", "Voiture/ajoutVoiture.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
