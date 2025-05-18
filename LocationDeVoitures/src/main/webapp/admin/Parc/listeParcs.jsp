<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, entities.Parc" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Liste des Parcs | Administration Location Voitures</title>
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
        }
    </style>
</head>
<body>
<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 text-primary">
            <i class="bi bi-buildings"></i> Gestion des Parcs
        </h1>
        <div class="action-buttons">
            <a href="/LocationDeVoitures/admin/formAjoutParc" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Ajouter un parc
            </a>
        </div>
    </div>

    <div class="admin-card">
        <div class="card-header">
            <h2 class="h4 mb-0">
                <i class="bi bi-list-ul"></i> Liste des parcs
            </h2>
        </div>

        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>Code</th>
                            <th>Nom</th>
                            <th>Libellé</th>
                            <th>Capacité</th>
                            <th>Nb voitures actuels</th>
                            <th style="text-align:center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<Parc> l = (ArrayList<Parc>) request.getAttribute("parcs");
                        System.out.println("listeParcs.jsp: parcs attribute is " + (l == null ? "null" : "size=" + l.size()));
                        if (l != null && !l.isEmpty()) {
                            for (Parc p : l) {
                                System.out.println("Displaying parc: " + p.getCodeParc() + ", " + p.getNomParc());
                        %>
                        <tr>
                            <td><%= p.getCodeParc() %></td>
                            <td><%= p.getNomParc() != null ? p.getNomParc() : "-" %></td>
                            <td><%= p.getLibelle() != null ? p.getLibelle() : "-" %></td>
                            <td><%= p.getCapacite() %></td>
                            <td><%= p.getNbVoitures() %></td>
                            <td>
                                <div class="action-buttons">
                                    <a href="/LocationDeVoitures/admin/formModifierParc?id=<%= p.getCodeParc() %>" class="btn btn-warning btn-sm">
                                        <i class="bi bi-pencil-square"></i> Modifier
                                    </a>
                                    <a href="/LocationDeVoitures/admin/deleteParc?codeParc=<%= p.getCodeParc() %>" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Confirmez-vous la suppression de ce parc ?')">
                                        <i class="bi bi-trash"></i> Supprimer
                                    </a>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                            System.out.println("No parcs to display");
                        %>
                            <tr>
                                <td colspan="6" class="text-center">Aucun parc trouvé.</td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>