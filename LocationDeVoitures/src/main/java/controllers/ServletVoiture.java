package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelVoiture;
import java.io.IOException;
import java.util.List;
import entities.Parc;
import entities.Voiture;

/**
 * Servlet implementation class ServletVoiture
 */
@WebServlet(urlPatterns = {"/ajoutVoiture", "/updateVoiture", "/deleteVoiture", "/listeVoitures"})
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

        response.sendRedirect("reussit.html?action=ajoutVoiture");
    }

    private void modifierVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeVoiture = Integer.parseInt(request.getParameter("codevoiture"));
        String matricule = request.getParameter("matricule");
        String model = request.getParameter("model");
        float kilometrage = Float.parseFloat(request.getParameter("kilometrage"));
        int codeParc = Integer.parseInt(request.getParameter("code_parc"));
        Parc parc = new Parc();
        parc.setCodeParc(codeParc);

        Voiture voiture = new Voiture(codeVoiture, matricule, model, kilometrage, parc);
        modelVoiture.setVoiture(voiture);
        modelVoiture.modifierVoiture();

        response.sendRedirect("reussit.html?action=modificationVoiture");
    }

    private void supprimerVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeVoiture = Integer.parseInt(request.getParameter("codevoiture"));
        modelVoiture.supprimerVoiture(codeVoiture);
        response.sendRedirect("reussit.html?action=suppressionVoiture");
    }

    private void afficherListeVoitures(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Voiture> voitures = modelVoiture.listeVoitures();
        request.setAttribute("voitures", voitures);
        request.getRequestDispatcher("/listeVoitures.jsp").forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
