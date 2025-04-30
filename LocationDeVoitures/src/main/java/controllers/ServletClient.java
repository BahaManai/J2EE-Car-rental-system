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
	@WebServlet(urlPatterns = {"/ajout", "/update", "/delete", "/listeClients"})
	
	public class ServletClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ModelClient modelClient = new ModelClient();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
			case "/ajout":
				ajouterClient(request, response);
				break;
			case "/update":
				modifierClient(request, response);
				break;
			case "/delete":
				supprimerClient(request, response);
				break;
			case "/listeClients":
				 afficherListeClients(request, response);
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

        response.sendRedirect("Client/reussit.html?action=ajout");
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

        response.sendRedirect("Client/reussit.html?action=modification");
	}

	private void supprimerClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int codeClient = Integer.parseInt(request.getParameter("codeClient"));
        modelClient.supprimerClient(codeClient);
        response.sendRedirect("Client/reussit.html?action=suppression");
	}
	

	private void afficherListeClients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    List<Client> clients = modelClient.listeClients();
	    request.setAttribute("clients", clients);
	    request.getRequestDispatcher("Client/listeClients.jsp").forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
