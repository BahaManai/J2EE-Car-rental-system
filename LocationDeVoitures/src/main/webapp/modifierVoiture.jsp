<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entities.Voiture, model.ModelVoiture, model.ModelParc, entities.Parc" %>
<%
String idParam = request.getParameter("id");
Voiture voiture = null;
ModelParc modelParc = new ModelParc();

if (idParam != null && !idParam.isEmpty()) { 
    try {
        int codeVoiture = Integer.parseInt(idParam);
        ModelVoiture model = new ModelVoiture();
        voiture = model.getVoitureById(codeVoiture);
    } catch (NumberFormatException e) {
    }
}

if (voiture == null) {
    response.sendRedirect(request.getContextPath() + "/listeVoitures?error=voiture_not_found");
    return;
}
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier Voiture | Administration Location Voitures</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2980b9;
            --success-color: #2ecc71;
            --light-bg: #f8f9fa;
        }

        body {
            background-color: var(--light-bg);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .admin-card {
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            border: none;
        }

        .card-header {
            background-color: var(--primary-color);
            color: white;
            padding: 1.2rem;
        }

        .form-section {
            background-color: white;
            padding: 2rem;
            border-radius: 8px;
        }

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: var(--secondary-color);
        }

        .btn-success {
            background-color: var(--success-color);
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 text-primary">
                <i class="bi bi-car-front"></i> Gestion des Voitures
            </h1>
            <a href="listeVoitures" class="btn btn-primary">
                <i class="bi bi-list-ul"></i> Liste des voitures
            </a>
        </div>

        <div class="admin-card">
            <div class="card-header">
                <h2 class="h4 mb-0">
                    <i class="bi bi-pencil-square"></i> Modifier la voiture
                </h2>
            </div>
            <div class="card-body">
                <form id="modification" action="updateVoiture" method="post" class="row g-3">
                    <input type="hidden" name="codeVoiture" value="<%= voiture.getCodeVoiture() %>">

                    <div class="col-md-6">
                        <label for="matricule" class="form-label">Matricule :</label>
                        <input type="text" id="matricule" name="matricule" class="form-control" 
                               value="<%= voiture.getMatricule() %>" required>
                    </div>

                    <div class="col-md-6">
                        <label for="model" class="form-label">Modèle :</label>
                        <input type="text" id="model" name="model" class="form-control" 
                               value="<%= voiture.getModel() %>" required>
                    </div>

                    <div class="col-md-6">
                        <label for="kilometrage" class="form-label">Kilométrage :</label>
                        <input type="number" step="0.1" id="kilometrage" name="kilometrage" class="form-control" 
                               value="<%= voiture.getKilometrage() %>" required>
                    </div>

                    <div class="col-md-6">
                        <label for="codeParc" class="form-label">Parc :</label>
                        <select name="codeParc" id="codeParc" class="form-control" required>
                            <%
                            for (Parc parc : modelParc.getAllParcs()) {
                                boolean selected = voiture.getParc() != null && parc.getCodeParc() == voiture.getParc().getCodeParc();
                            %>
                            <option value="<%= parc.getCodeParc() %>" <%= selected ? "selected" : "" %>>
                                <%= parc.getNomParc() %>
                            </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="col-12 mt-4">
                        <button type="submit" class="btn btn-success px-4 py-2">
                            <i class="bi bi-save"></i> Enregistrer les modifications
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('modification').addEventListener('submit', function(e) {
            const kilometrage = document.getElementById('kilometrage').value;
            if (kilometrage < 0) {
                alert("Le kilométrage ne peut pas être négatif.");
                e.preventDefault();
            }
        });
    </script>
</body>
</html>
