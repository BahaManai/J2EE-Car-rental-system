<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="java.util.*, entities.Client" %>
<%
    String pageToInclude = (String) request.getAttribute("page");
    if (pageToInclude == null) {
        pageToInclude = "/Client/gestionClient.html";
    }
    // Get the logged-in user from session
    Client user = (Client) session.getAttribute("user");
    String userName = user != null ? user.getPrenom() + " " + user.getNom() : "Utilisateur";
    String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Admin - Location de Voitures</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">    
    <style>
        .sidebar {
            transition: all 0.3s ease;
        }
        .sidebar-collapsed {
            width: 100px !important;
        }
        .sidebar-collapsed .nav-text {
            display: none;
        }
        .sidebar-collapsed .logo-text {
            display: none;
        }
        .sidebar-collapsed .user-nom-div {
            display: none;
        }
        .sidebar-collapsed .user-icon-circle {
            display: none;
        }
        .sidebar-collapsed .nav-item {
            justify-content: center;
        }
        .active-nav {
            background-color: #3b82f6;
            color: white;
        }
        .active-nav:hover {
            background-color: #3b82f6 !important;
        }
        .sidebar a {
            text-decoration: none;
            color: white;
        }
        .sidebar a:hover {
            text-decoration: none;
        }
        .user-icon-circle {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: #3b82f6;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 20px;
        }
    </style>
</head>
<body class="bg-gray-100 font-sans">
    <div class="flex h-screen overflow-hidden">
        <!-- Sidebar -->
        <div class="sidebar bg-gray-800 text-white w-64 flex flex-col">
            <!-- Logo et bouton de réduction -->
            <div class="p-4 flex items-center justify-between border-b border-gray-700">
                <div class="flex items-center space-x-2">
                    <i class="fas fa-car text-blue-400 text-2xl"></i>
                    <span class="logo-text text-xl font-bold">RentRover</span>
                </div>
                <button id="toggleSidebar" class="text-gray-400 hover:text-white focus:outline-none">
                    <i class="fas fa-bars"></i>
                </button>
            </div>
            
            <!-- Menu de navigation -->
            <nav class="flex-1 overflow-y-auto py-4">
                <div class="space-y-2 px-4">
                    <%
                        String activeSection = "";
                        if (pageToInclude.contains("Voiture")) {
                            activeSection = "voitures";
                        } else if (pageToInclude.contains("Client")) {
                            activeSection = "clients";
                        } else if (pageToInclude.contains("Parc")) {
                            activeSection = "parcs";
                        } else if (pageToInclude.contains("Location")) {
                            activeSection = "locations";
                        } else {
                            activeSection = "dashboard";
                        }
                    %>
                    <!-- Dashboard -->
                    <a href="/LocationDeVoitures/admin/dashboard" class="nav-item flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-700 <%= activeSection.equals("dashboard") ? "active-nav" : "" %>">
                        <i class="fas fa-tachometer-alt text-lg"></i>
                        <span class="nav-text">Tableau de bord</span>
                    </a>
                    
                    <!-- Gestion des voitures -->
                    <div class="group">
                        <a href="#" class="nav-item flex items-center justify-between space-x-3 p-3 rounded-lg hover:bg-gray-700 <%= activeSection.equals("voitures") ? "active-nav" : "" %>">
                            <div class="flex items-center space-x-3">
                                <i class="fas fa-car text-lg"></i>
                                <span class="nav-text">Gestion des voitures</span>
                            </div>
                            <i class="fas fa-chevron-down text-xs transition-transform group-hover:rotate-180"></i>
                        </a>
                        <div class="pl-11 mt-1 hidden group-hover:block">
                            <a href="/LocationDeVoitures/admin/listeVoitures" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Liste des voitures</a>
                            <a href="/LocationDeVoitures/admin/formAjoutVoiture" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Ajouter une voiture</a>
                        </div>
                    </div>
                    
                    <!-- Gestion des clients -->
                    <div class="group">
                        <a href="#" class="nav-item flex items-center justify-between space-x-3 p-3 rounded-lg hover:bg-gray-700 <%= activeSection.equals("clients") ? "active-nav" : "" %>">
                            <div class="flex items-center space-x-3">
                                <i class="fas fa-user text-lg"></i>
                                <span class="nav-text">Gestion des clients</span>
                            </div>
                            <i class="fas fa-chevron-down text-xs transition-transform group-hover:rotate-180"></i>
                        </a>
                        <div class="pl-11 mt-1 hidden group-hover:block">
                            <a href="/LocationDeVoitures/admin/listeClients" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Liste des clients</a>
                            <a href="/LocationDeVoitures/admin/formAjoutClient" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Ajouter un client</a>
                        </div>
                    </div>
                    
                    <!-- Gestion des parcs -->
                    <div class="group">
                        <a href="#" class="nav-item flex items-center justify-between space-x-3 p-3 rounded-lg hover:bg-gray-700 <%= activeSection.equals("parcs") ? "active-nav" : "" %>">
                            <div class="flex items-center space-x-3">
                                <i class="fas fa-warehouse text-lg"></i>
                                <span class="nav-text">Gestion des parcs</span>
                            </div>
                            <i class="fas fa-chevron-down text-xs transition-transform group-hover:rotate-180"></i>
                        </a>
                        <div class="pl-11 mt-1 hidden group-hover:block">
                            <a href="/LocationDeVoitures/admin/listeParcs" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Liste des parcs</a>
                            <a href="/LocationDeVoitures/admin/formAjoutParc" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Ajouter un parc</a>
                        </div>
                    </div>
                    
                    <!-- Gestion des locations -->
                    <div class="group">
                        <a href="#" class="nav-item flex items-center justify-between space-x-3 p-3 rounded-lg hover:bg-gray-700 <%= activeSection.equals("locations") ? "active-nav" : "" %>">
                            <div class="flex items-center space-x-3">
                                <i class="fas fa-calendar-check text-lg"></i>
                                <span class="nav-text">Gestion des locations</span>
                            </div>
                            <i class="fas fa-chevron-down text-xs transition-transform group-hover:rotate-180"></i>
                        </a>
                        <div class="pl-11 mt-1 hidden group-hover:block">
                            <a href="/LocationDeVoitures/admin/listeLocations" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Liste des locations</a>
                            <a href="/LocationDeVoitures/admin/formAjoutLocation" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Ajouter une location</a>
                        </div>
                    </div>
                </div>
            </nav>
            
            <!-- Profil utilisateur -->
            <div class="p-4 border-t border-gray-700">
                <div class="flex items-center space-x-3">
                    <div class="user-icon-circle">
                        <i class="fas <%= "admin".equals(role) ? "fa-user-tie" : "fa-user" %>"></i>
                    </div>
                    <div class ="user-nom-div">
                        <div class="font-medium"><%= userName %></div>
                        <div class="text-xs text-gray-400"><%= role != null ? role.substring(0, 1).toUpperCase() + role.substring(1) : "Inconnu" %></div>
                    </div>
                    <a href="<%= request.getContextPath() %>/logout" class="ml-auto text-gray-400 hover:text-white">
                        <i class="fas fa-sign-out-alt"></i>
                    </a>
                </div>
            </div>
        </div>

        <!-- Contenu principal -->
        <div class="flex-1 overflow-auto">
            <main class="p-6">
                <jsp:include page="<%= pageToInclude %>" />
            </main>
        </div>
    </div>

    <script>
        document.getElementById('toggleSidebar').addEventListener('click', function() {
            document.querySelector('.sidebar').classList.toggle('sidebar-collapsed');
        });

        const navItems = document.querySelectorAll('.nav-item');
        navItems.forEach(item => {
            item.addEventListener('click', function(e) {
                if (e.target.closest('.nav-item') && !e.target.closest('.pl-11')) {
                    navItems.forEach(i => i.classList.remove('active-nav'));
                    this.classList.add('active-nav');
                }
            });
        });
    </script>
</body>
</html>