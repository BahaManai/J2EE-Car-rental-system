<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, entities.Location, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Liste des Locations | Administration Location Voitures</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2980b9;
            --danger-color: #e74c3c;
            --warning-color: #f39c12;
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
            overflow: hidden;
            margin-bottom: 2rem;
        }

        .card-header {
            background-color: var(--primary-color);
            color: white;
            padding: 1.2rem;
        }

        .table-responsive {
            border-radius: 8px;
            overflow: hidden;
        }

        .table {
            margin-bottom: 0;
        }

        .table thead th {
            border-bottom: none;
            padding: 1rem;
        }

        .table tbody tr:hover {
            background-color: rgba(52, 152, 219, 0.05);
        }

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: var(--secondary-color);
        }

        .btn-warning {
            background-color: var(--warning-color);
            border-color: var(--warning-color);
            color: white;
        }

        .btn-danger {
            background-color: var(--danger-color);
            border-color: var(--danger-color);
        }

        .btn-success {
            background-color: var(--success-color);
            border-color: var(--success-color);
        }

        .action-buttons {
            display: flex;
            gap: 0.5rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        .action-buttons .btn {
            padding: 0.375rem 0.75rem;
            font-size: 0.875rem;
        }

        .badge {
            font-size: 0.875rem;
            padding: 0.5em 0.75em;
        }

        @media (max-width: 768px) {
            .action-buttons {
                justify-content: flex-start;
            }
        }
    </style>
</head>
<body>
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 text-primary">
            <i class="bi bi-calendar-check"></i> Gestion des Locations
        </h1>
        <div class="action-buttons">
            <a href="/LocationDeVoitures/admin/formAjoutLocation" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Ajouter une location
            </a>
        </div>
    </div>

    <div class="admin-card">
        <div class="card-header">
            <h2 class="h4 mb-0">
                <i class="bi bi-list-ul"></i> Liste des locations
            </h2>
        </div>

        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>Code Location</th>
                            <th>Client</th>
                            <th>Voiture</th>
                            <th>Date Début</th>
                            <th>Date Fin</th>
                            <th>Statut</th>
                            <th style="text-align:center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<Location> locations = (ArrayList<Location>) request.getAttribute("locations");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        if (locations != null) {
                            for (Location l : locations) {
                        %>
                        <tr>
                            <td><%= l.getCodeLocation() %></td>
                            <td><%= l.getClient() != null ? l.getClient().getNom() + " " + l.getClient().getPrenom() : "-" %></td>
                            <td><%= l.getVoiture() != null ? l.getVoiture().getModel() : "-" %></td>
                            <td><%= l.getDateDeb() != null ? sdf.format(l.getDateDeb()) : "-" %></td>
                            <td><%= l.getDateFin() != null ? sdf.format(l.getDateFin()) : "-" %></td>
                            <td>
                                <span class="badge <%= l.getStatut().equals("en attente") ? "bg-warning" : l.getStatut().equals("accepté") ? "bg-success" : "bg-danger" %>">
                                    <%= l.getStatut() %>
                                </span>
                            </td>
                            <td>
                                <div class="action-buttons">
                                    <% if ("en attente".equals(l.getStatut())) { %>
                                        <a href="/LocationDeVoitures/admin/updateLocationStatus?codeLocation=<%= l.getCodeLocation() %>&statut=accepté" class="btn btn-success btn-sm"
                                           onclick="return confirm('Confirmez-vous l\'acceptation de cette location ?')">
                                            <i class="bi bi-check-circle"></i> Accepter
                                        </a>
                                        <a href="/LocationDeVoitures/admin/updateLocationStatus?codeLocation=<%= l.getCodeLocation() %>&statut=refusé" class="btn btn-danger btn-sm"
                                           onclick="return confirm('Confirmez-vous le refus de cette location ?')">
                                            <i class="bi bi-x-circle"></i> Refuser
                                        </a>
                                    <% } %>
                                    <a href="/LocationDeVoitures/admin/formModifierLocation?id=<%= l.getCodeLocation() %>" class="btn btn-warning btn-sm">
                                        <i class="bi bi-pencil-square"></i> Modifier
                                    </a>
                                    <a href="/LocationDeVoitures/admin/deleteLocation?codeLocation=<%= l.getCodeLocation() %>" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Confirmez-vous la suppression de cette location ?')">
                                        <i class="bi bi-trash"></i> Supprimer
                                    </a>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
                        }
                        %>
                    </tbody>
                </table>
            </div>

            <% if (locations == null || locations.isEmpty()) { %>
                <div class="alert alert-info mt-3">
                    <i class="bi bi-info-circle"></i> Aucune location trouvée.
                </div>
            <% } %>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>