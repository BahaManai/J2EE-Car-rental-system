<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entities.Location, entities.Client, entities.Voiture, java.text.SimpleDateFormat" %>
<%
Location location = (Location) request.getAttribute("location");
List<Client> clients = (List<Client>) request.getAttribute("clients");
List<Voiture> voitures = (List<Voiture>) request.getAttribute("voitures");
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Modifier une Location | Administration Location Voitures</title>
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
        .form-label {
            font-weight: 500;
            color: #495057;
        }
        .form-control {
            padding: 0.5rem 0.75rem;
            border-radius: 0.375rem;
            border: 1px solid #ced4da;
        }
        .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(52, 152, 219, 0.25);
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 text-primary">
                <i class="bi bi-calendar-check"></i> Gestion des Locations
            </h1>
            <a href="/LocationDeVoitures/admin/listeLocations" class="btn btn-primary">
                <i class="bi bi-list-ul"></i> Liste des locations
            </a>
        </div>

        <!-- Error Message -->
        <% if (error != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i>
                <%= error.equals("invalid_dates") ? "Les dates sont invalides." :
                    error.equals("invalid_client_or_voiture") ? "Client ou voiture invalide." :
                    error.equals("invalid_statut") ? "Statut invalide." :
                    error.equals("date_format") ? "Format de date invalide." :
                    error.equals("missing_parameters") ? "Tous les champs sont requis." : "Erreur inconnue." %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <% } %>

        <div class="admin-card">
            <div class="card-header">
                <h2 class="h4 mb-0">
                    <i class="bi bi-pencil-square"></i> Modifier la location
                </h2>
            </div>
            <div class="card-body" style="padding:20px">
                <form id="modifierLocation" action="/LocationDeVoitures/admin/updateLocation" method="post" class="row g-3">
                    <input type="hidden" name="codeLocation" value="<%= location.getCodeLocation() %>">

                    <div class="col-md-6">
                        <label for="codeClient" class="form-label">Client :</label>
                        <select id="codeClient" name="codeClient" class="form-control" required>
                            <% for (Client client : clients) { %>
                                <option value="<%= client.getCodeClient() %>" <%= client.getCodeClient() == location.getClient().getCodeClient() ? "selected" : "" %>>
                                    <%= client.getNom() %> <%= client.getPrenom() %>
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label for="codeVoiture" class="form-label">Voiture :</label>
                        <select id="codeVoiture" name="codeVoiture" class="form-control" required>
                            <% for (Voiture voiture : voitures) { %>
                                <option value="<%= voiture.getCodeVoiture() %>" <%= voiture.getCodeVoiture() == location.getVoiture().getCodeVoiture() ? "selected" : "" %>>
                                    <%= voiture.getModel() %> ($<%= String.format("%.2f", voiture.getPrixParJour()) %>/day)
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label for="dateDeb" class="form-label">Date Début :</label>
                        <input type="date" id="dateDeb" name="dateDeb" class="form-control" value="<%= sdf.format(location.getDateDeb()) %>" required>
                    </div>

                    <div class="col-md-6">
                        <label for="dateFin" class="form-label">Date Fin :</label>
                        <input type="date" id="dateFin" name="dateFin" class="form-control" value="<%= sdf.format(location.getDateFin()) %>" required>
                    </div>

                    <div class="col-md-6">
                        <label for="statut" class="form-label">Statut :</label>
                        <select id="statut" name="statut" class="form-control" required>
                            <option value="en attente" <%= "en attente".equals(location.getStatut()) ? "selected" : "" %>>En attente</option>
                            <option value="accepté" <%= "accepté".equals(location.getStatut()) ? "selected" : "" %>>Accepté</option>
                            <option value="refusé" <%= "refusé".equals(location.getStatut()) ? "selected" : "" %>>Refusé</option>
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
        document.getElementById('modifierLocation').addEventListener('submit', function(e) {
            const dateDeb = new Date(document.getElementById('dateDeb').value);
            const dateFin = new Date(document.getElementById('dateFin').value);
            const today = new Date();
            today.setHours(0, 0, 0, 0);

            if (dateDeb < today) {
                alert('La date de début ne peut pas être antérieure à aujourd\'hui');
                e.preventDefault();
            } else if (dateFin <= dateDeb) {
                alert('La date de fin doit être postérieure à la date de début');
                e.preventDefault();
            }
        });
    </script>
</body>
</html>