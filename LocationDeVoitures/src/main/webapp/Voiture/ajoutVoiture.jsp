<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, entities.Parc" %>
<%
List<Parc> parcs = (List<Parc>) request.getAttribute("parcs");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ajout d'une Voiture | Administration Location Voitures</title>
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
            overflow: hidden;
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
            margin-bottom: 2rem;
        }
        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }
        .btn-primary:hover {
            background-color: var(--secondary-color);
            border-color: var(--secondary-color);
        }
        .btn-success {
            background-color: var(--success-color);
            border-color: var(--success-color);
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
        .action-buttons {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
        }
        @media (max-width: 768px) {
            .action-buttons {
                justify-content: center;
                margin-bottom: 1rem;
            }
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <!-- En-tÃªte -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 text-primary">
                <i class="bi bi-truck-front-fill"></i> Gestion des Voitures
            </h1>
            <div class="action-buttons">
                <a href="/LocationDeVoitures/listeVoitures" class="btn btn-primary">
                    <i class="bi bi-list-ul"></i> Liste des voitures
                </a>
            </div>
        </div>

        <!-- Carte -->
        <div class="admin-card">
            <div class="card-header">
                <h2 class="h4 mb-0">
                    <i class="bi bi-plus-square"></i> Ajouter une voiture
                </h2>
            </div>
            <div class="card-body" style="padding:20px">
                <form id="ajoutVoiture" action="/LocationDeVoitures/ajoutVoiture" method="post" class="row g-3">
                    <div class="col-md-6">
                        <label for="matricule" class="form-label">Matricule :</label>
                        <input type="text" id="matricule" name="matricule" class="form-control" required placeholder="Matricule">
                    </div>

                    <div class="col-md-6">
                        <label for="model" class="form-label">Modèle :</label>
                        <input type="text" id="model" name="model" class="form-control" required placeholder="Modèle">
                    </div>

                    <div class="col-md-6">
                        <label for="kilometrage" class="form-label">Kilométrage :</label>
                        <input type="number" id="kilometrage" name="kilometrage" step="0.1" class="form-control" required placeholder="Ex : 15000.5">
                    </div>

                    <div class="col-md-6">
                        <label for="code_parc" class="form-label">Parc :</label>
                        <select id="code_parc" name="code_parc" class="form-control" required>
						    <option value="">-- Sélectionnez un parc --</option>
						    <% for (Parc parc : parcs) { %>
						        <option value="<%= parc.getCodeParc() %>"><%= parc.getNomParc() %></option>
						    <% } %>
						</select>
                    </div>

                    <div class="col-12 mt-4">
                        <button type="submit" class="btn btn-success px-4 py-2">
                            <i class="bi bi-save"></i> Enregistrer la voiture
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('ajoutVoiture').addEventListener('submit', function(e) {
            const kilometrage = document.getElementById('kilometrage').value;
            if (kilometrage < 0) {
                alert('Le kilomÃ©trage ne peut pas Ãªtre nÃ©gatif');
                e.preventDefault();
            }
        });
    </script>
</body>
</html>
