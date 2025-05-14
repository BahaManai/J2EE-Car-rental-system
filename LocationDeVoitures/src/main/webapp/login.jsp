<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Connexion | Location de Voitures</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #3498db;
            --light-bg: #f8f9fa;
        }
        body {
            background-color: var(--light-bg);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            border: none;
            max-width: 400px;
            width: 100%;
            background-color: #ffffff;
        }
        .card-header {
            background-color: var(--primary-color);
            color: white;
            padding: 1.2rem;
            text-align: center;
        }
        .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(52, 152, 219, 0.25);
        }
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
        
    </style>
</head>
<body>
    <div class="login-card">
        <div class="card-header">
            <h2 class="h4 mb-0">
                <i class="bi bi-lock-fill"></i> Connexion
            </h2>
        </div>
        <div class="card-body p-4">
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <form action="<%= request.getContextPath() %>/login" method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">Email :</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="motDePasse" class="form-label">Mot de passe :</label>
                    <input type="password" id="motDePasse" name="motDePasse" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">
                    <i class="bi bi-box-arrow-in-right"></i> Se connecter
                </button>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>