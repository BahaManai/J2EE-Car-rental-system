<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entities.Voiture, java.text.SimpleDateFormat" %>
<%
List<Voiture> voitures = (List<Voiture>) request.getAttribute("voitures");
if (voitures == null) {
    voitures = new model.ModelVoiture().listeVoitures();
}
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
Boolean isAuthenticated = (Boolean) request.getAttribute("isAuthenticated");
if (isAuthenticated == null) {
    isAuthenticated = false; // Default to unauthenticated if not set
}
String success = request.getParameter("success");
String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Accueil - RentRover</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    animation: {
                        'fade': 'fadeIn 1s ease-in-out',
                        'slide-up': 'slideUp 0.5s ease-out'
                    },
                    keyframes: {
                        fadeIn: {
                            '0%': { opacity: '0' },
                            '100%': { opacity: '1' }
                        },
                        slideUp: {
                            '0%': { transform: 'translateY(20px)', opacity: '0' },
                            '100%': { transform: 'translateY(0)', opacity: '1' }
                        }
                    }
                }
            }
        }
    </script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .car-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .car-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
        }
        .placeholder-image {
            background-color: #e5e7eb;
            color: #9ca3af;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
        }
        .alert {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0.75rem 1rem;
            border-radius: 0.375rem;
            margin-bottom: 1rem;
        }
        .alert-success {
            background-color: #d1fae5;
            color: #065f46;
        }
        .alert-error {
            background-color: #fee2e2;
            color: #991b1b;
        }
    </style>
</head>
<body class="bg-gray-100">
    <div class="py-6 px-4 max-w-7xl mx-auto">
        <!-- Success/Error Messages -->
        <% if (success != null && success.equals("reservation_added")) { %>
            <div class="alert alert-success">
                <span>Réservation effectuée avec succès.</span>
                <button onclick="this.parentElement.style.display='none'" class="text-gray-600 hover:text-gray-800">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert alert-error">
                <span>
                    <%= error.equals("invalid_dates") ? "Dates invalides : la date de début doit être à partir de demain !" :
                        error.equals("past_date") ? "La date de début ne peut pas être antérieure à aujourd'hui." :
                        error.equals("voiture_unavailable") ? "La voiture n'est pas disponible pour ces dates." :
                        error.equals("invalid_code_voiture") ? "Code de voiture invalide." :
                        error.equals("db_error") ? "Erreur de base de données lors de la réservation." : "Erreur inconnue lors de la réservation." %>
                </span>
                <button onclick="this.parentElement.style.display='none'" class="text-gray-600 hover:text-gray-800">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        <% } %>

        <!-- Welcome Section (Unauthenticated Users Only) -->
        <% if (!isAuthenticated) { %>
        <section class="bg-gradient-to-r from-blue-500 to-indigo-600 text-white rounded-lg shadow-lg p-8 mb-8 text-center animate-fade">
            <h2 class="text-4xl font-bold mb-4 animate-slide-up">Bienvenue chez RentRover</h2>
            <p class="text-lg mb-6 max-w-2xl mx-auto animate-slide-up" style="animation-delay: 0.2s;">
                Découvrez notre plateforme intuitive pour louer des voitures en toute simplicité. Que ce soit pour un voyage, une escapade ou un besoin quotidien, nous offrons une large sélection de véhicules à des prix compétitifs. Connectez-vous pour commencer à réserver !
            </p>
            <a href="<%= request.getContextPath() %>/login.jsp" class="inline-block bg-white text-blue-600 px-6 py-3 rounded-full font-semibold hover:bg-gray-100 transition animate-slide-up" style="animation-delay: 0.4s;">
                <i class="fas fa-sign-in-alt mr-2"></i> Se connecter
            </a>
        </section>
        <% } %>

        <h1 id="voitures" class="text-3xl font-bold text-gray-800 mb-6 text-center">
            <i class="fas fa-car mr-2"></i> Nos Voitures Disponibles
        </h1>

        <!-- Search Filter -->
        <div class="max-w-md mx-auto mb-8">
            <div class="relative">
                <input type="text" id="searchModel" placeholder="Rechercher par modèle..." class="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <i class="fas fa-search absolute right-3 top-3 text-gray-400"></i>
            </div>
        </div>

        <!-- Car Grid -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            <% for (Voiture voiture : voitures) { %>
            <div class="car-card bg-white rounded-lg shadow-md overflow-hidden">
                <!-- Car Image -->
                <div class="h-48 w-full">
                    <% if (voiture.getImage() != null && !voiture.getImage().isEmpty()) { %>
                        <img src="<%= voiture.getImage() %>" alt="<%= voiture.getModel() %>" class="w-full h-full object-cover">
                    <% } else { %>
                        <div class="h-full w-full placeholder-image">
                            <i class="fas fa-car"></i>
                        </div>
                    <% } %>
                </div>
                <!-- Car Details -->
                <div class="p-4">
                    <h2 class="text-xl font-semibold text-gray-800"><%= voiture.getModel() %></h2>
                    <p class="text-gray-600">Prix: <%= String.format("%.2f", voiture.getPrixParJour()) %> DT/jour</p>
                    <p class="text-gray-600">Kilométrage: <%= String.format("%.1f", voiture.getKilometrage()) %> km</p>
                </div>
                <!-- Reservation Form or Login Prompt -->
                <% if (isAuthenticated) { %>
                <form action="/LocationDeVoitures/client/ajoutLocation" method="post" class="p-4 bg-gray-50">
                    <input type="hidden" name="codeVoiture" value="<%= voiture.getCodeVoiture() %>">
                    <div class="mb-3">
                        <label for="dateDeb_<%= voiture.getCodeVoiture() %>" class="block text-sm font-medium text-gray-700">Date Début</label>
                        <input type="date" id="dateDeb_<%= voiture.getCodeVoiture() %>" name="dateDeb" class="w-full px-3 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500" required>
                    </div>
                    <div class="mb-3">
                        <label for="dateFin_<%= voiture.getCodeVoiture() %>" class="block text-sm font-medium text-gray-700">Date Fin</label>
                        <input type="date" id="dateFin_<%= voiture.getCodeVoiture() %>" name="dateFin" class="w-full px-3 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500" required>
                    </div>
                    <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition">
                        <i class="fas fa-calendar-check mr-1"></i> Réserver
                    </button>
                </form>
                <% } else { %>
                <div class="p-4 bg-gray-50 text-center">
                    <p class="text-sm text-gray-600">Connectez-vous pour réserver cette voiture.</p>
                    <a href="<%= request.getContextPath() %>/login.jsp" class="inline-block bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition mt-2">
                        <i class="fas fa-sign-in-alt mr-1"></i> Se connecter
                    </a>
                </div>
                <% } %>
            </div>
            <% } %>
        </div>

        <% if (voitures == null || voitures.isEmpty()) { %>
        <div class="text-center mt-8">
            <p class="text-gray-600">Aucune voiture disponible pour le moment.</p>
        </div>
        <% } %>
    </div>

    <script>
        // Client-side search filter
        document.getElementById('searchModel').addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            const cards = document.querySelectorAll('.car-card');
            cards.forEach(card => {
                const model = card.querySelector('h2').textContent.toLowerCase();
                card.style.display = model.includes(searchTerm) ? 'block' : 'none';
            });
        });

        // Form validation for authenticated users
        <% if (isAuthenticated) { %>
        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', function(e) {
                const dateDeb = form.querySelector('input[name="dateDeb"]').value;
                const dateFin = form.querySelector('input[name="dateFin"]').value;
                const debut = new Date(dateDeb);
                const fin = new Date(dateFin);
                const today = new Date();
                today.setHours(0, 0, 0, 0);
                if (debut < today) {
                    alert('La date de début ne peut pas être antérieure à aujourd\'hui');
                    e.preventDefault();
                } else if (fin <= debut) {
                    alert('La date de fin doit être postérieure à la date de début');
                    e.preventDefault();
                }
            });
        });
        <% } %>
    </script>
</body>
</html>