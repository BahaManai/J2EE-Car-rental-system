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
	    "/ajoutParc",
	    "/updateParc",
	    "/deleteParc",
	    "/listeParcs",
	    "/formAjoutParc",
	    "/formModifierParc"
	})
public class ServletParc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModelParc modelParc = new ModelParc();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletParc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/ajoutParc":
                ajouterParc(request, response);
                break;
            case "/updateParc":
                modifierParc(request, response);
                break;
            case "/deleteParc":
                supprimerParc(request, response);
                break;
            case "/listeParcs":
                afficherListeParcs(request, response);
                break;
            case "/formAjoutParc":
                afficherFormAjoutParc(request, response);
                break;
            case "/formModifierParc":
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

        response.sendRedirect("Parc/reussit.html?action=ajout");
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

        response.sendRedirect("Parc/reussit.html?action=modification");
    }

    private void supprimerParc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeParc = Integer.parseInt(request.getParameter("codeParc"));
        modelParc.supprimerParc(codeParc);
        response.sendRedirect("Parc/reussit.html?action=suppression");
    }

    private void afficherListeParcs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Parc> parcs = modelParc.getAllParcs();
        request.setAttribute("parcs", parcs);
        request.setAttribute("page", "Parc/listeParcs.jsp");
        request.getRequestDispatcher("adminLayout.jsp").forward(request, response);
    }

    private void afficherFormAjoutParc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("page", "Parc/ajoutParc.jsp");
        request.getRequestDispatcher("adminLayout.jsp").forward(request, response);
    }

    private void afficherFormModifierParc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int codeParc = Integer.parseInt(idParam);
                Parc parc = modelParc.getParcById(codeParc);
                if (parc != null) {
                    request.setAttribute("parc", parc);
                    request.setAttribute("page", "Parc/modifierParc.jsp");
                    request.getRequestDispatcher("adminLayout.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }
        response.sendRedirect("listeParcs?error=parc_not_found");
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
