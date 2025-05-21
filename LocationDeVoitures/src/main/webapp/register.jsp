<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Inscription | Location de Voitures</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        /* Reuse login page styles */
        body {
            background: linear-gradient(-45deg, #3498db, #8e44ad, #1abc9c, #f39c12);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }
        
        .login-card {
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            border: none;
            max-width: 500px;
            width: 100%;
            background-color: #ffffff;
        }
        
        .card-header {
            background-color: #3498db;
            color: white;
            padding: 1.2rem;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="login-card">
        <div class="card-header">
            <h2 class="h4 mb-0">
                <i class="bi bi-person-plus"></i> Créer un compte
            </h2>
        </div>
        <div class="card-body p-4">
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <form action="<%= request.getContextPath() %>/register" method="post">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="cin" class="form-label">CIN</label>
                        <input type="text" id="cin" name="cin" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="age" class="form-label">Âge</label>
                        <input type="number" id="age" name="age" class="form-control" required min="18">
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="nom" class="form-label">Nom</label>
                        <input type="text" id="nom" name="nom" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="prenom" class="form-label">Prénom</label>
                        <input type="text" id="prenom" name="prenom" class="form-control" required>
                    </div>
                </div>
                
                <div class="mb-3">
                    <label for="adresse" class="form-label">Adresse</label>
                    <input type="text" id="adresse" name="adresse" class="form-control" required>
                </div>
                
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>
                
                <div class="mb-3">
                    <label for="tel" class="form-label">Téléphone</label>
                    <input type="tel" id="tel" name="tel" class="form-control" required>
                </div>
                
                <div class="mb-3">
                    <label for="motDePasse" class="form-label">Mot de passe</label>
                    <input type="password" id="motDePasse" name="motDePasse" class="form-control" required>
                </div>
                
                <button type="submit" class="btn btn-primary w-100">
                    <i class="bi bi-person-check"></i> S'inscrire
                </button>
                
                <div class="mt-3 text-center">
                    <a href="<%= request.getContextPath() %>/login" class="text-decoration-none">
                        Déjà un compte? Se connecter
                    </a>
                </div>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>