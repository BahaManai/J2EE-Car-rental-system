<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entities.Location, entities.Voiture, java.text.SimpleDateFormat, java.util.concurrent.TimeUnit" %>
<%
List<Location> reservations = (List<Location>) request.getAttribute("reservations");
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
String success = request.getParameter("success");
String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mes Réservations - Location de Voitures</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .table-container {
            background: white;
            border-radius: 0.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .status-badge {
            padding: 0.25rem 0.5rem;
            border-radius: 9999px;
            font-size: 0.875rem;
        }
        .thumbnail {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 0.25rem;
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
        <h1 class="text-3xl font-bold text-gray-800 mb-6 text-center">
            <i class="fas fa-calendar-check mr-2"></i> Mes Réservations
        </h1>

        <!-- Success/Error Messages -->
        <% if (success != null && success.equals("reservation_cancelled")) { %>
            <div class="alert alert-success">
                <span>Réservation annulée avec succès.</span>
                <button onclick="this.parentElement.style.display='none'" class="text-gray-600 hover:text-gray-800">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        <% } %>
        <% if (success != null && success.equals("reservation_added")) { %>
            <div class="alert alert-success">
                <span>Demande de réservation envoyée avec succès.</span>
                <button onclick="this.parentElement.style.display='none'" class="text-gray-600 hover:text-gray-800">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert alert-error">
                <span>
                    <%= error.equals("missing_code_location") ? "Code de réservation manquant." :
                        error.equals("location_not_found") ? "Réservation non trouvée." :
                        error.equals("unauthorized") ? "Vous n'êtes pas autorisé à annuler cette réservation." :
                        error.equals("cannot_cancel") ? "Cette réservation ne peut pas être annulée." :
                        error.equals("invalid_code_location") ? "Code de réservation invalide." : "Erreur inconnue." %>
                </span>
                <button onclick="this.parentElement.style.display='none'" class="text-gray-600 hover:text-gray-800">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        <% } %>

        <div class="table-container overflow-x-auto">
            <table class="w-full text-left">
                <thead class="bg-gray-200">
                    <tr>
                        <th class="px-4 py-3 text-sm font-semibold text-gray-700">Code</th>
                        <th class="px-4 py-3 text-sm font-semibold text-gray-700">Voiture</th>
                        <th class="px-4 py-3 text-sm font-semibold text-gray-700">Date Début</th>
                        <th class="px-4 py-3 text-sm font-semibold text-gray-700">Date Fin</th>
                        <th class="px-4 py-3 text-sm font-semibold text-gray-700">Statut</th>
                        <th class="px-4 py-3 text-sm font-semibold text-gray-700">Coût Total</th>
                        <th class="px-4 py-3 text-sm font-semibold text-gray-700">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (reservations != null && !reservations.isEmpty()) {
                        for (Location reservation : reservations) {
                            Voiture voiture = reservation.getVoiture();
                            long diffInMillies = Math.abs(reservation.getDateFin().getTime() - reservation.getDateDeb().getTime());
                            long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
                            float totalCost = voiture != null ? voiture.getPrixParJour() * days : 0;
                    %>
                    <tr class="border-b border-gray-200 hover:bg-gray-50">
                        <td class="px-4 py-3 text-sm text-gray-600"><%= reservation.getCodeLocation() %></td>
                        <td class="px-4 py-3 text-sm text-gray-600 flex items-center space-x-2">
                            <% if (voiture != null && voiture.getImage() != null && !voiture.getImage().isEmpty()) { %>
                                <img src="<%= voiture.getImage() %>" alt="<%= voiture.getModel() %>" class="thumbnail">
                            <% } else { %>
                                <div class="thumbnail bg-gray-200 flex items-center justify-center">
                                    <i class="fas fa-car text-gray-400"></i>
                                </div>
                            <% } %>
                            <span><%= voiture != null ? voiture.getModel() : "-" %></span>
                        </td>
                        <td class="px-4 py-3 text-sm text-gray-600"><%= reservation.getDateDeb() != null ? sdf.format(reservation.getDateDeb()) : "-" %></td>
                        <td class="px-4 py-3 text-sm text-gray-600"><%= reservation.getDateFin() != null ? sdf.format(reservation.getDateFin()) : "-" %></td>
                        <td class="px-4 py-3 text-sm">
                            <span class="status-badge <%= reservation.getStatut().equals("en attente") ? "bg-yellow-100 text-yellow-800" : reservation.getStatut().equals("accepté") ? "bg-green-100 text-green-800" : "bg-red-100 text-red-800" %>">
                                <%= reservation.getStatut() %>
                            </span>
                        </td>
                        <td class="px-4 py-3 text-sm text-gray-600"><%= String.format("%.2f", totalCost) %> DT</td>
                        <td class="px-4 py-3 text-sm">
                            <% if ("en attente".equals(reservation.getStatut())) { %>
                                <a href="/LocationDeVoitures/client/cancelLocation?codeLocation=<%= reservation.getCodeLocation() %>" 
                                   onclick="return confirm('Êtes-vous sûr de vouloir annuler cette réservation ?')"
                                   class="inline-block bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">
                                    <i class="fas fa-trash-alt mr-1"></i> Annuler
                                </a>
                            <% } %>
                        </td>
                    </tr>
                    <% }
                    } else { %>
                    <tr>
                        <td colspan="7" class="px-4 py-3 text-center text-gray-600">Aucune réservation trouvée.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>