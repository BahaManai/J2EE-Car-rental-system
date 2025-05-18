<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestion des Clients | Administration Location Voitures</title>
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
        <!-- En-tête de la page -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 text-primary">
                <i class="bi bi-people-fill"></i> Gestion des Clients
            </h1>
            <div class="action-buttons">
                <a href="/LocationDeVoitures/admin/listeClients" class="btn btn-primary">
                    <i class="bi bi-list-ul"></i> Liste des clients
                </a>
            </div>
        </div>
        
        <!-- Carte principale -->
        <div class="admin-card">
            <div class="card-header">
                <h2 class="h4 mb-0">
                    <i class="bi bi-person-plus"></i> Ajouter un nouveau client
                </h2>
            </div>
            
            <div class="card-body" style="padding:20px">
                <!-- Formulaire d'ajout -->
                <form id="ajout" action="/LocationDeVoitures/admin/ajoutClient" method="post" class="row g-3">
                    <div class="col-md-6">
                        <label for="CIN" class="form-label">CIN :</label>
                        <input type="text" id="CIN" name="CIN" class="form-control" required placeholder="CIN">
                    </div>
                    
                    <div class="col-md-6">
                        <label for="nom" class="form-label">Nom :</label>
                        <input type="text" id="nom" name="nom" class="form-control" required placeholder="Nom">
                    </div>
                    
                    <div class="col-md-6">
                        <label for="prenom" class="form-label">Prénom :</label>
                        <input type="text" id="prenom" name="prenom" class="form-control" required placeholder="Prénom">
                    </div>
                    
                    <div class="col-md-6">
                        <label for="email" class="form-label">Email :</label>
                        <input type="email" id="email" name="email" class="form-control" required placeholder="yourname@example.com">
                    </div>
                    
                    <div class="col-md-6">
                        <label for="tel" class="form-label">Téléphone :</label>
                        <input type="tel" id="tel" name="tel" class="form-control" required placeholder="Téléphone">
                    </div>
                    
                    <div class="col-md-6">
                        <label for="age" class="form-label">Âge :</label>
                        <input type="number" id="age" name="age" class="form-control" required min="18" max="99" placeholder="Age">
                    </div>
                    
                    <div class="col-12">
                        <label for="adresse" class="form-label">Adresse :</label>
                        <input type="text" id="adresse" name="adresse" class="form-control" required placeholder="Adresse">
                    </div>
                    
                    <div class="col-md-6">
                        <label for="password" class="form-label">Mot de passe :</label>
                        <input type="password" id="password" name="password" class="form-control" required placeholder="Mot de passe">
                    </div>
                    
                    <div class="col-md-6">
                        <label for="passwordConfirmation" class="form-label">Confirmation du mot de passe :</label>
                        <input type="password" id="passwordConfirmation" name="passwordConfirmation" class="form-control" required placeholder="Confirmer le mot de passe">
                    </div>
                    
                    <div class="col-12 mt-4">
                        <button type="submit" class="btn btn-success px-4 py-2">
                            <i class="bi bi-save"></i> Enregistrer le client
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('ajout').addEventListener('submit', function(e) {
            const tel = document.getElementById('tel').value;
            const age = document.getElementById('age').value;
            const password = document.getElementById('password').value;
            const passwordConfirmation = document.getElementById('passwordConfirmation').value;
            
            if (!/^[0-9]{8}$/.test(tel)) {
                alert('Veuillez entrer un numéro de téléphone valide (8 chiffres)');
                e.preventDefault();
                return;
            }
            
            if (age < 18 || age > 99) {
                alert('L\'âge doit être compris entre 18 et 99 ans');
                e.preventDefault();
                return;
            }
            
            if (password.length < 6) {
                alert('Le mot de passe doit contenir au moins 6 caractères');
                e.preventDefault();
                return;
            }
            
            if (password !== passwordConfirmation) {
                alert('Les mots de passe ne correspondent pas');
                e.preventDefault();
                return;
            }
        });
    </script>
</body>
</html>