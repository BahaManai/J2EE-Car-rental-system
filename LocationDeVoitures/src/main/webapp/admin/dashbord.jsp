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

        .stats-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            padding: 1.5rem;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .stats-card:hover {
            transform: translateY(-5px);
        }

        .stats-card h3 {
            font-size: 1.2rem;
            margin-bottom: 0.5rem;
            color: #555;
        }

        .stats-card p {
            font-size: 2rem;
            font-weight: bold;
            color: var(--primary-color);
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
            max-width: 100%;
            margin: 2rem 0;
        }

        @media (max-width: 768px) {
            .stats-card p {
                font-size: 1.5rem;
            }
            .chart-container {
                max-width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <!-- En-tête -->
        <div class="mb-4">
            <h1 class="h3 text-primary">
                <i class="bi bi-speedometer2"></i> Tableau de Bord Admin
            </h1>
        </div>

        <!-- Cartes de statistiques -->
        <div class="row mb-4">
            <div class="col-md-3 col-sm-6 mb-3">
                <div class="stats-card">
                    <h3>Clients Actifs</h3>
                    <p><%= request.getAttribute("clientCount") %></p>
                </div>
            </div>
            <div class="col-md-3 col-sm-6 mb-3">
                <div class="stats-card">
                    <h3>Locations en Cours</h3>
                    <p><%= request.getAttribute("activeLocations") %></p>
                </div>
            </div>
            <div class="col-md-3 col-sm-6 mb-3">
                <div class="stats-card">
                    <h3>Revenu Total</h3>
                    <p><%= request.getAttribute("totalRevenue") %> DT</p>
                </div>
            </div>
            <div class="col-md-3 col-sm-6 mb-3">
                <div class="stats-card">
                    <h3>Taux d'Occupation</h3>
                    <p><%= request.getAttribute("occupancyRate") %> %</p>
                </div>
            </div>
        </div>

        <!-- Graphiques -->
        <div class="row">
            <div class="col-md-6">
                <div class="admin-card">
                    <div class="card-header">
                        <h2 class="h4 mb-0">
                            <i class="bi bi-bar-chart"></i> Revenus par Parc
                        </h2>
                    </div>
                    <div class="card-body">
                        <div class="chart-container">
                            <canvas id="revenuePerParcChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="admin-card">
                    <div class="card-header">
                        <h2 class="h4 mb-0">
                            <i class="bi bi-pie-chart"></i> Voitures les Plus Réservées
                        </h2>
                    </div>
                    <div class="card-body">
                        <div class="chart-container">
                            <canvas id="mostReservedCarChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="admin-card">
                    <div class="card-header">
                        <h2 class="h4 mb-0">
                            <i class="bi bi-graph-up"></i> Évolution des Revenus
                        </h2>
                    </div>
                    <div class="card-body">
                        <div class="chart-container">
                            <canvas id="revenueEvolutionChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Données simulées (à remplacer par les données réelles du servlet)
        const revenuePerParc = {
            labels: <%= request.getAttribute("parcNames") != null ? request.getAttribute("parcNames") : "['Parc A', 'Parc B', 'Parc C']" %>,
            data: <%= request.getAttribute("revenuePerParc") != null ? request.getAttribute("revenuePerParc") : "[15000, 22000, 9000]" %>
        };

        const mostReservedCars = {
            labels: <%= request.getAttribute("mostReservedCarLabels") != null ? request.getAttribute("mostReservedCarLabels") : "['Volkswagen Golf 8', 'Hyundai i20', 'Peugeot 208']" %>,
            data: <%= request.getAttribute("mostReservedCarCounts") != null ? request.getAttribute("mostReservedCarCounts") : "[10, 8, 5]" %>
        };

        const revenueEvolution = {
            labels: <%= request.getAttribute("revenueMonths") != null ? request.getAttribute("revenueMonths") : "['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Juin']" %>,
            data: <%= request.getAttribute("revenueEvolutionData") != null ? request.getAttribute("revenueEvolutionData") : "[5000, 7000, 10000, 12000, 15000, 18000]" %>
        };

        // Graphique en barres : Revenus par Parc
        const revenuePerParcChart = new Chart(document.getElementById('revenuePerParcChart'), {
            type: 'bar',
            data: {
                labels: revenuePerParc.labels,
                datasets: [{
                    label: 'Revenus (DT)',
                    data: revenuePerParc.data,
                    backgroundColor: 'rgba(52, 152, 219, 0.6)',
                    borderColor: 'rgba(52, 152, 219, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: { display: true, text: 'Revenus (DT)' }
                    }
                },
                plugins: {
                    legend: { display: false },
                    title: { display: true, text: 'Revenus par Parc' }
                }
            }
        });

        // Graphique circulaire : Voitures les Plus Réservées
        const mostReservedCarChart = new Chart(document.getElementById('mostReservedCarChart'), {
            type: 'pie',
            data: {
                labels: mostReservedCars.labels,
                datasets: [{
                    data: mostReservedCars.data,
                    backgroundColor: [
                        'rgba(52, 152, 219, 0.6)',
                        'rgba(46, 204, 113, 0.6)',
                        'rgba(241, 196, 15, 0.6)',
                        'rgba(231, 76, 60, 0.6)',
                        'rgba(155, 89, 182, 0.6)'
                    ],
                    borderColor: [
                        'rgba(52, 152, 219, 1)',
                        'rgba(46, 204, 113, 1)',
                        'rgba(241, 196, 15, 1)',
                        'rgba(231, 76, 60, 1)',
                        'rgba(155, 89, 182, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                plugins: {
                    title: { display: true, text: 'Voitures les Plus Réservées' }
                }
            }
        });

        // Graphique en ligne : Évolution des Revenus
        const revenueEvolutionChart = new Chart(document.getElementById('revenueEvolutionChart'), {
            type: 'line',
            data: {
                labels: revenueEvolution.labels,
                datasets: [{
                    label: 'Revenus (DT)',
                    data: revenueEvolution.data,
                    backgroundColor: 'rgba(52, 152, 219, 0.2)',
                    borderColor: 'rgba(52, 152, 219, 1)',
                    borderWidth: 2,
                    fill: true
                }]
            },
            options: {
                scales: {
                    y: { 
                        beginAtZero: true, 
                        title: { display: true, text: 'Revenus (DT)' }
                    }
                },
                plugins: {
                    title: { display: true, text: 'Évolution des Revenus Mensuels' }
                }
            }
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>