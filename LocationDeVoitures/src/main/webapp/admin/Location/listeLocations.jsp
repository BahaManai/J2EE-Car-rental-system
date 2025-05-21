<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, entities.Location, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Liste des Locations | Administration Location Voitures</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-gray-100 font-sans">
<div class="container mx-auto py-6 px-4 sm:px-6 lg:px-8">
    <div class="flex flex-col sm:flex-row justify-between items-center mb-6">
        <h1 class="text-2xl font-bold text-blue-600">
            <i class="fas fa-calendar-check mr-2"></i> Gestion des Locations
        </h1>
        <div class="mt-4 sm:mt-0">
            <a href="/LocationDeVoitures/admin/formAjoutLocation" class="inline-flex items-center px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition">
                <i class="fas fa-plus-circle mr-2"></i> Ajouter une location
            </a>
        </div>
    </div>

    <div class="bg-white shadow-lg rounded-lg overflow-hidden">
        <div class="bg-blue-500 text-white px-6 py-4">
            <h2 class="text-xl font-semibold">
                <i class="fas fa-list-ul mr-2"></i> Liste des locations
            </h2>
        </div>

        <div class="p-6">
            <div class="overflow-x-auto">
                <table class="w-full table-auto border-collapse">
                    <thead>
                        <tr class="bg-gray-50 text-gray-700">
                            <th class="px-4 py-3 text-left text-sm font-semibold">Code Location</th>
                            <th class="px-4 py-3 text-left text-sm font-semibold">Client</th>
                            <th class="px-4 py-3 text-left text-sm font-semibold">Voiture</th>
                            <th class="px-4 py-3 text-left text-sm font-semibold">Date Début</th>
                            <th class="px-4 py-3 text-left text-sm font-semibold">Date Fin</th>
                            <th class="px-4 py-3 text-left text-sm font-semibold">Statut</th>
                            <th class="px-4 py-3 text-center text-sm font-semibold">Actions</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <%
                        List<Location> locations = (ArrayList<Location>) request.getAttribute("locations");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        if (locations != null) {
                            for (Location l : locations) {
                        %>
                        <tr class="hover:bg-gray-50">
                            <td class="px-4 py-3 text-sm"><%= l.getCodeLocation() %></td>
                            <td class="px-4 py-3 text-sm"><%= l.getClient() != null ? l.getClient().getNom() + " " + l.getClient().getPrenom() : "-" %></td>
                            <td class="px-4 py-3 text-sm"><%= l.getVoiture() != null ? l.getVoiture().getModel() : "-" %></td>
                            <td class="px-4 py-3 text-sm"><%= l.getDateDeb() != null ? sdf.format(l.getDateDeb()) : "-" %></td>
                            <td class="px-4 py-3 text-sm"><%= l.getDateFin() != null ? sdf.format(l.getDateFin()) : "-" %></td>
                            <td class="px-4 py-3 text-sm">
                                <span class="inline-block px-2 py-0.5 text-sm font-medium rounded-md border-2 <%= l.getStatut().equals("en attente") ? "border-yellow-500 text-yellow-500" : l.getStatut().equals("accepté") ? "border-green-500 text-green-500" : "border-red-500 text-red-500" %>">
                                    <%= l.getStatut() %>
                                </span>
                                <% if ("en attente".equals(l.getStatut())) { %>
                                        <a href="/LocationDeVoitures/admin/updateLocationStatus?codeLocation=<%= l.getCodeLocation() %>&statut=accepté" 
                                           class="inline-flex items-center px-3 py-1 bg-green-500 text-white text-sm rounded hover:bg-green-600 transition"
                                           onclick="return confirm('Confirmez-vous l\'acceptation de cette location ?')">
                                            <i class="fas fa-check-circle mr-1"></i> Accepter
                                        </a>
                                        <a href="/LocationDeVoitures/admin/updateLocationStatus?codeLocation=<%= l.getCodeLocation() %>&statut=refusé" 
                                           class="inline-flex items-center px-3 py-1 bg-red-500 text-white text-sm rounded hover:bg-red-600 transition"
                                           onclick="return confirm('Confirmez-vous le refus de cette location ?')">
                                            <i class="fas fa-times-circle mr-1"></i> Refuser
                                        </a>
                                    <% } %>
                            </td>
                            <td class="px-4 py-3">
                                <div class="flex justify-center gap-2 flex-wrap">
                                    <a href="/LocationDeVoitures/admin/formModifierLocation?id=<%= l.getCodeLocation() %>" 
                                       class="inline-flex items-center p-2 bg-blue-500 text-white text-sm rounded hover:bg-blue-600 transition"
                                       title="Modifier">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <a href="/LocationDeVoitures/admin/deleteLocation?codeLocation=<%= l.getCodeLocation() %>" 
                                       class="inline-flex items-center p-2 bg-red-500 text-white text-sm rounded hover:bg-red-600 transition"
                                       title="Supprimer"
                                       onclick="return confirm('Confirmez-vous la suppression de cette location ?')">
                                        <i class="fas fa-trash"></i>
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
                <div class="mt-4 p-4 bg-blue-50 text-blue-700 rounded-lg flex items-center">
                    <i class="fas fa-info-circle mr-2"></i> Aucune location trouvée.
                </div>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>