<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="java.util.*, entities.Client" %>
<%
    String pageToInclude = (String) request.getAttribute("page");
    if (pageToInclude == null) {
        pageToInclude = "Client/home.jsp"; // Default to home page
    }
    // Get the logged-in user from session
    Client user = (Client) session.getAttribute("user");
    boolean isAuthenticated = user != null;
    String userName = isAuthenticated ? user.getPrenom() + " " + user.getNom() : "Utilisateur";
    String role = (String) session.getAttribute("role");
    if (role == null) {
        role = "client"; // Default for client layout
    }
    // Define activeSection for nav highlighting
    String activeSection = "";
    if (isAuthenticated) {
        if (pageToInclude.contains("home")) {
            activeSection = "home";
        } else if (pageToInclude.contains("reservations")) {
            activeSection = "reservations";
        }
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Client - RentRover</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .user-icon-circle {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background-color: #3b82f6;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 16px;
        }
        .navbar {
            transition: all 0.3s ease;
        }
        .nav-link {
            transition: background-color 0.2s ease;
        }
        .nav-link:hover {
            background-color: #4b5563; /* Tailwind gray-700 */
        }
        .active-nav {
            background-color: #1d4ed8; /* Tailwind blue-700 */
            color: white;
        }
    </style>
</head>
<body class="bg-gray-100 font-sans">
    <!-- Navbar -->
    <nav class="navbar bg-gray-800 text-white shadow-lg">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between h-16">
                <!-- Left: Logo and Brand -->
                <div class="flex items-center">
                    <a href="/LocationDeVoitures/client/home" class="flex items-center space-x-2">
                        <i class="fas fa-car text-blue-400 text-xl"></i>
                        <span class="text-lg font-bold">RentRover</span>
                    </a>
                </div>

                <!-- Center: Navigation Links (Authenticated Users Only) -->
                <% if (isAuthenticated) { %>
                <div class="hidden sm:flex sm:items-center sm:space-x-4">
                    <a href="/LocationDeVoitures/client/home" class="nav-link px-3 py-2 rounded-md text-sm font-medium <%= activeSection.equals("home") ? "active-nav" : "" %>">
                        <i class="fas fa-home mr-1"></i> Accueil
                    </a>
                    <a href="/LocationDeVoitures/client/listeReservations" class="nav-link px-3 py-2 rounded-md text-sm font-medium <%= activeSection.equals("reservations") ? "active-nav" : "" %>">
                        <i class="fas fa-calendar-check mr-1"></i> Mes Réservations
                    </a>
                </div>
                <% } %>

                <!-- Right: User Profile/Logout or Login -->
                <div class="flex items-center space-x-4">
                    <% if (isAuthenticated) { %>
                    <div class="flex items-center space-x-2">
                        <div class="user-icon-circle">
                            <i class="fas fa-user"></i>
                        </div>
                        <div class="hidden sm:block">
                            <div class="font-medium text-sm"><%= userName %></div>
                            <div class="text-xs text-gray-400 capitalize"><%= role %></div>
                        </div>
                    </div>
                    <a href="/LocationDeVoitures/logout" class="text-gray-400 hover:text-white px-3 py-2 rounded-md text-sm">
                        <i class="fas fa-sign-out-alt"></i> Déconnexion
                    </a>
                    <% } else { %>
                    <a href="/LocationDeVoitures/login.jsp" class="text-white bg-blue-600 hover:bg-blue-700 px-3 py-2 rounded-md text-sm font-medium">
                        <i class="fas fa-sign-in-alt mr-1"></i> Se connecter
                    </a>
                    <% } %>
                </div>

                <!-- Mobile Menu Button (Authenticated Users Only) -->
                <% if (isAuthenticated) { %>
                <div class="sm:hidden flex items-center">
                    <button id="mobileMenuButton" class="text-gray-400 hover:text-white focus:outline-none">
                        <i class="fas fa-bars text-lg"></i>
                    </button>
                </div>
                <% } %>
            </div>
        </div>

        <!-- Mobile Menu (Authenticated Users Only) -->
        <% if (isAuthenticated) { %>
        <div id="mobileMenu" class="hidden sm:hidden bg-gray-800">
            <div class="px-2 pt-2 pb-3 space-y-1">
                <a href="/LocationDeVoitures/client/home" class="nav-link block px-3 py-2 rounded-md text-base font-medium <%= activeSection.equals("home") ? "active-nav" : "" %>">
                    <i class="fas fa-home mr-1"></i> Accueil
                </a>
                <a href="/LocationDeVoitures/client/listeReservations" class="nav-link block px-3 py-2 rounded-md text-base font-medium <%= activeSection.equals("reservations") ? "active-nav" : "" %>">
                    <i class="fas fa-calendar-check mr-1"></i> Mes Réservations
                </a>
            </div>
        </div>
        <% } %>
    </nav>

    <!-- Main Content -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <main>
            <jsp:include page="<%= pageToInclude %>" />
        </main>
    </div>

    <script>
        // Toggle mobile menu (only if authenticated)
        const mobileMenuButton = document.getElementById('mobileMenuButton');
        if (mobileMenuButton) {
            mobileMenuButton.addEventListener('click', function() {
                const mobileMenu = document.getElementById('mobileMenu');
                mobileMenu.classList.toggle('hidden');
            });
        }

        // Highlight active nav link (only if authenticated)
        const navLinks = document.querySelectorAll('.nav-link');
        navLinks.forEach(link => {
            link.addEventListener('click', function() {
                navLinks.forEach(l => l.classList.remove('active-nav'));
                this.classList.add('active-nav');
            });
        });
    </script>
</body>
</html>