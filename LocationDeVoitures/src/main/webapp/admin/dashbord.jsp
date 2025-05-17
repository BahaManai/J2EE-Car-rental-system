<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tableau de Bord Admin | Administration Location Voitures</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
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
            margin-bottom: 2rem;
        }

        .card-header {
            background-color: var(--primary-color);
            color: white;
            padding: 1.2rem;
        }

        .chart-container {
            max-width: 600px;
            margin: 2rem auto;
        }

        .action-buttons {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
        }

        @media (max-width: 768px) {
            .chart-container {
                max-width: 100%;
            }
            .action-buttons {
                justify-content: center;
                flex-wrap: wrap;
            }
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <!-- En-tête -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 text-primary">
                <i class="bi bi-speedometer2"></i> Tableau de Bord Admin
            </h1>
            <div class="action-buttons">
                <a href="/LocationDeVoitures/admin/listeClients" class="btn btn-primary">
                    <i class="bi bi-people-fill"></i> Clients
                </a>
                <a href="/LocationDeVoitures/admin/listeLocations" class="btn btn-primary">
                    <i class="bi bi-car-front-fill"></i> Locations
                </a>
                <a href="/LocationDeVoitures/admin/listeParcs" class="btn btn-primary">
                    <i class="bi bi-buildings"></i> Parcs
                </a>
                <a href="/LocationDeVoitures/admin/listeVoitures" class="btn btn-primary">
                    <i class="bi bi-truck"></i> Voitures
                </a>
            </div>
        </div>

        <!-- Carte principale -->
        <div class="admin-card">
            <div class="card-header">
                <h2 class="h4 mb-0">
                    <i class="bi bi-bar-chart"></i> Statistiques Générales
                </h2>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="chart-container">
                            <canvas id="entityCountChart"></canvas>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="chart-container">
                            <canvas id="locationStatusChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Données passées depuis le servlet
        const entityCounts = {
            clients: <%= request.getAttribute("clientCount") %>,
            locations: <%= request.getAttribute("locationCount") %>,
            parcs: <%= request.getAttribute("parcCount") %>,
            voitures: <%= request.getAttribute("voitureCount") %>
        };

        const locationStatuses = {
            active: <%= request.getAttribute("activeLocations") %>,
            completed: <%= request.getAttribute("completedLocations") %>
        };

        // Bar Chart pour les counts
        const entityCountChart = new Chart(document.getElementById('entityCountChart'), {
            type: 'bar',
            data: {
                labels: ['Clients', 'Locations', 'Parcs', 'Voitures'],
                datasets: [{
                    label: 'Nombre',
                    data: [
                        entityCounts.clients,
                        entityCounts.locations,
                        entityCounts.parcs,
                        entityCounts.voitures
                    ],
                    backgroundColor: [
                        'rgba(52, 152, 219, 0.6)',
                        'rgba(46, 204, 113, 0.6)',
                        'rgba(241, 196, 15, 0.6)',
                        'rgba(231, 76, 60, 0.6)'
                    ],
                    borderColor: [
                        'rgba(52, 152, 219, 1)',
                        'rgba(46, 204, 113, 1)',
                        'rgba(241, 196, 15, 1)',
                        'rgba(231, 76, 60, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Nombre'
                        }
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: true,
                        text: 'Nombre d\'Entités'
                    }
                }
            }
        });

        // Pie Chart pour les statuts des locations
        const locationStatusChart = new Chart(document.getElementById('locationStatusChart'), {
            type: 'pie',
            data: {
                labels: ['Actives', 'Terminées'],
                datasets: [{
                    data: [locationStatuses.active, locationStatuses.completed],
                    backgroundColor: [
                        'rgba(52, 152, 219, 0.6)',
                        'rgba(46, 204, 113, 0.6)'
                    ],
                    borderColor: [
                        'rgba(52, 152, 219, 1)',
                        'rgba(46, 204, 113, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                plugins: {
                    title: {
                        display: true,
                        text: 'Répartition des Locations'
                    }
                }
            }
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>