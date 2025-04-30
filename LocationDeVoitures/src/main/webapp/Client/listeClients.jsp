<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, entities.Client" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Liste des Clients | Administration Location Voitures</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2980b9;
            --danger-color: #e74c3c;
            --warning-color: #f39c12;
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
        
        .table tbody tr {
            transition: all 0.2s ease;
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
            border-color: var(--secondary-color);
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
        
        .action-buttons {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
        }
        
        .action-buttons .btn {
            padding: 0.375rem 0.75rem;
            font-size: 0.875rem;
        }
        
        @media (max-width: 768px) {
            .action-buttons {
                justify-content: flex-start;
                flex-wrap: wrap;
            }
            
            .table-responsive {
                border: 1px solid #dee2e6;
            }
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <!-- En-tête de la page -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 text-primary">
                <i class="bi bi-people-fill"></i> Gestion des Clients
            </h1>
            <div class="action-buttons">
                <a href="Client/gestionClient.html" class="btn btn-primary">
                    <i class="bi bi-person-plus"></i> Ajouter un client
                </a>
            </div>
        </div>
        
        <!-- Carte principale -->
        <div class="admin-card">
            <div class="card-header">
                <h2 class="h4 mb-0">
                    <i class="bi bi-list-ul"></i> Liste des clients
                </h2>
            </div>
            
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>CIN</th>
                                <th>Nom</th>
                                <th>Prénom</th>
                                <th>Email</th>
                                <th>Téléphone</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            List<Client> l = (ArrayList<Client>)request.getAttribute("clients");
                            if(l != null) {
                                for(Client client : l) {
                            %>
                                <tr>
                                    <td><%= client.getCIN() %></td>
                                    <td><%= client.getNom() %></td>
                                    <td><%= client.getPrenom() %></td>
                                    <td><%= client.getEmail() %></td>
                                    <td><%= client.getTel() %></td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="Client/modifierClient.jsp?id=<%= client.getCodeClient() %>" 
                                               class="btn btn-warning btn-sm">
                                               <i class="bi bi-pencil-square"></i> Modifier
                                            </a>
                                            <a href="delete?codeClient=<%= client.getCodeClient() %>" 
                                               class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce client?')">
                                               <i class="bi bi-trash"></i> Supprimer
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            <% }} %>
                        </tbody>
                    </table>
                </div>
                
                <% if(l == null || l.isEmpty()) { %>
                    <div class="alert alert-info mt-3">
                        <i class="bi bi-info-circle"></i> Aucun client trouvé.
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>