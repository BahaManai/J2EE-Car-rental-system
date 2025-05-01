<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
<%
    String pageToInclude = (String) request.getAttribute("page");
    if (pageToInclude == null) {
        pageToInclude = "/Client/gestionClient.html";
    }
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
                    <span class="logo-text text-xl font-bold">J2EE Project</span>
                </div>
                <button id="toggleSidebar" class="text-gray-400 hover:text-white focus:outline-none">
                    <i class="fas fa-bars"></i>
                </button>
            </div>
            
            <!-- Menu de navigation -->
            <nav class="flex-1 overflow-y-auto py-4">
                <div class="space-y-2 px-4">
                    <!-- Dashboard -->
                    <a href="#" class="nav-item flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-700 active-nav">
                        <i class="fas fa-tachometer-alt text-lg"></i>
                        <span class="nav-text">Tableau de bord</span>
                    </a>
                    
                    <!-- Gestion des voitures -->
                    <div class="group">
                        <a href="#" class="nav-item flex items-center justify-between space-x-3 p-3 rounded-lg hover:bg-gray-700">
                            <div class="flex items-center space-x-3">
                                <i class="fas fa-car text-lg"></i>
                                <span class="nav-text">Gestion des voitures</span>
                            </div>
                            <i class="fas fa-chevron-down text-xs transition-transform group-hover:rotate-180"></i>
                        </a>
                        <div class="pl-11 mt-1 hidden group-hover:block">
                            <a href="/LocationDeVoitures/listeVoitures" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Liste des voitures</a>
                            <a href="/LocationDeVoitures/formAjoutVoiture" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Ajouter une voiture</a>
                        </div>
                    </div>
                    
                    <!-- Gestion des clients -->
                    <div class="group">
                        <a href="#" class="nav-item flex items-center justify-between space-x-3 p-3 rounded-lg hover:bg-gray-700">
                            <div class="flex items-center space-x-3">
                                <i class="fas fa-car text-lg"></i>
                                <span class="nav-text">Gestion des clients</span>
                            </div>
                            <i class="fas fa-chevron-down text-xs transition-transform group-hover:rotate-180"></i>
                        </a>
                        <div class="pl-11 mt-1 hidden group-hover:block">
                            <a href="/LocationDeVoitures/listeClients" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Liste des clients</a>
                            <a href="/LocationDeVoitures/formAjoutClient" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Ajouter un client</a>
                        </div>
                    </div>
                    
                    
                    <!-- Gestion des parcs -->
                    <div class="group">
                        <a href="#" class="nav-item flex items-center justify-between space-x-3 p-3 rounded-lg hover:bg-gray-700">
                            <div class="flex items-center space-x-3">
                                <i class="fas fa-warehouse text-lg"></i>
                                <span class="nav-text">Gestion des parcs</span>
                            </div>
                            <i class="fas fa-chevron-down text-xs transition-transform group-hover:rotate-180"></i>
                        </a>
                        <div class="pl-11 mt-1 hidden group-hover:block">
                            <a href="/LocationDeVoitures/listeParcs" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Liste des parcs</a>
                            <a href="/LocationDeVoitures/formAjoutParc" class="block py-2 px-3 text-sm rounded hover:bg-gray-700">Ajouter un parc</a>
                        </div>
                    </div>
                    <!-- Réservations 
                    <a href="#" class="nav-item flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-700">
                        <i class="fas fa-calendar-check text-lg"></i>
                        <span class="nav-text">Réservations</span>
                    </a>
                    -->
                    <!-- Statistiques 
                    <a href="#" class="nav-item flex items-center space-x-3 p-3 rounded-lg hover:bg-gray-700">
                        <i class="fas fa-chart-line text-lg"></i>
                        <span class="nav-text">Statistiques</span>
                    </a>
                    -->
                </div>
            </nav>
            
            <!-- Profil admin 
            <div class="p-4 border-t border-gray-700">
                <div class="flex items-center space-x-3">
                    <img src="https://randomuser.me/api/portraits/men/33.jpg" alt="Admin" class="w-10 h-10 rounded-full">
                    <div>
                        <div class="font-medium">Admin</div>
                        <div class="text-xs text-gray-400">Administrateur</div>
                    </div>
                    <button class="ml-auto text-gray-400 hover:text-white">
                        <i class="fas fa-sign-out-alt"></i>
                    </button>
                </div>
            </div>
            -->
        </div>

        <!-- Contenu principal -->
        <div class="flex-1 overflow-auto">
            <!-- HEADER
            <header class="bg-white shadow-sm">
                <div class="flex items-center justify-between p-4">
                    <div class="flex items-center space-x-4">
                        <h1 class="text-xl font-semibold text-gray-800">Tableau de bord</h1>
                    </div>
                    <div class="flex items-center space-x-4">
                        
                    </div>
                </div>
            </header>
 			-->
            <!-- Injection de contenu -->
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
            item.addEventListener('click', function() {
                navItems.forEach(i => i.classList.remove('active-nav'));
                this.classList.add('active-nav');
            });
        });
    </script>
</body>
</html>
