package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelParc;
import model.ModelVoiture;
import java.io.IOException;
import java.io.File;
import java.util.List;
import entities.Parc;
import entities.Voiture;

@WebServlet(urlPatterns = {
        "/admin/ajoutVoiture", "/admin/updateVoiture", "/admin/deleteVoiture",
        "/admin/listeVoitures", "/admin/formModifierVoiture", "/admin/formAjoutVoiture"
})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class ServletVoiture extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModelVoiture modelVoiture = new ModelVoiture();
    private static final String UPLOAD_DIRECTORY = "uploads/cars";

    public ServletVoiture() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/admin/listeVoitures":
                afficherListeVoitures(request, response);
                break;
            case "/admin/formModifierVoiture":
                afficherFormModification(request, response);
                break;
            case "/admin/formAjoutVoiture":
                afficherFormAjoutVoiture(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/listeVoitures");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            default:
                response.sendRedirect(request.getContextPath() + "/admin/listeVoitures");
                break;
        }
    }

    private void ajouterVoiture(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            // Validate required parameters
            String matricule = request.getParameter("matricule");
            String model = request.getParameter("model");
            String kilometrageStr = request.getParameter("kilometrage");
            String prixParJourStr = request.getParameter("prix_par_jour");
            String codeParcStr = request.getParameter("code_parc");

            if (matricule == null || model == null || kilometrageStr == null ||
                    prixParJourStr == null || codeParcStr == null) {
                response.sendRedirect(request.getContextPath() + "/admin/formAjoutVoiture?error=missing_fields");
                return;
            }

            float kilometrage = Float.parseFloat(kilometrageStr);
            float prixParJour = Float.parseFloat(prixParJourStr);
            int codeParc = Integer.parseInt(codeParcStr);

            // Validate prix_par_jour
            if (prixParJour < 0) {
                response.sendRedirect(request.getContextPath() + "/admin/formAjoutVoiture?error=invalid_price");
                return;
            }

            // Handle file upload
            String imagePath = null;
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + getSubmittedFileName(filePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                filePart.write(uploadPath + File.separator + fileName);
                imagePath = UPLOAD_DIRECTORY + "/" + fileName;
            }

            Parc parc = new Parc();
            parc.setCodeParc(codeParc);

            Voiture voiture = new Voiture(0, matricule, model, kilometrage, prixParJour, imagePath, parc);
            modelVoiture.setVoiture(voiture);
            modelVoiture.ajouterVoiture();

            response.sendRedirect(request.getContextPath() + "/admin/Voiture/reussit.html?action=ajoutVoiture");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/formAjoutVoiture?error=invalid_input");
        }
    }

    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    private void modifierVoiture(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int codeVoiture = Integer.parseInt(request.getParameter("codevoiture"));
            String matricule = request.getParameter("matricule");
            String model = request.getParameter("model");
            float kilometrage = Float.parseFloat(request.getParameter("kilometrage"));
            float prixParJour = Float.parseFloat(request.getParameter("prix_par_jour"));
            int codeParc = Integer.parseInt(request.getParameter("codeParc"));

            // Validate prix_par_jour
            if (prixParJour < 0) {
                response.sendRedirect(request.getContextPath() + "/admin/formModifierVoiture?id=" + codeVoiture
                        + "&error=invalid_price");
                return;
            }

            // Handle file upload
            String imagePath = null;
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + getSubmittedFileName(filePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                filePart.write(uploadPath + File.separator + fileName);
                imagePath = UPLOAD_DIRECTORY + "/" + fileName;
            } else {
                // Keep existing image if no new image is uploaded
                Voiture existingVoiture = modelVoiture.getVoitureById(codeVoiture);
                if (existingVoiture != null) {
                    imagePath = existingVoiture.getImage();
                }
            }

            Parc parc = new Parc();
            parc.setCodeParc(codeParc);

            Voiture voiture = new Voiture(codeVoiture, matricule, model, kilometrage, prixParJour, imagePath, parc);
            modelVoiture.setVoiture(voiture);
            modelVoiture.modifierVoiture();

            response.sendRedirect(request.getContextPath() + "/admin/Voiture/reussit.html?action=modificationVoiture");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/formModifierVoiture?id="
                    + request.getParameter("codevoiture") + "&error=invalid_input");
        }
    }

    private void supprimerVoiture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codeVoiture = Integer.parseInt(request.getParameter("codevoiture"));
        modelVoiture.supprimerVoiture(codeVoiture);
        response.sendRedirect(request.getContextPath() + "/admin/Voiture/reussit.html?action=suppressionVoiture");
    }

    private void afficherListeVoitures(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Voiture> voitures = modelVoiture.listeVoitures();
        request.setAttribute("voitures", voitures);
        request.setAttribute("page", "admin/Voiture/listeVoitures.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
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
                    request.setAttribute("page", "admin/Voiture/modifierVoiture.jsp");
                    request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/listeVoitures?error=voiture_not_found");
    }

    private void afficherFormAjoutVoiture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelParc modelParc = new ModelParc();
        List<Parc> parcs = modelParc.getAllParcs();
        request.setAttribute("parcs", parcs);
        request.setAttribute("page", "admin/Voiture/ajoutVoiture.jsp");
        request.getRequestDispatcher("/adminLayout.jsp").forward(request, response);
    }
}