package dao;

import entities.Location;
import entities.Client;
import entities.Voiture;
import utilitaire.SingletonConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ImpDaoLocation implements IDaoLocation {
    private Connection con = SingletonConnexion.getConnection();

    @Override
    public void ajouterLocation(Location l) {
        String sql = "INSERT INTO location (code_location, code_client, code_voiture, date_debut, date_fin, statut) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, l.getCodeLocation());
            ps.setInt(2, l.getClient().getCodeClient());
            ps.setInt(3, l.getVoiture().getCodeVoiture());
            ps.setDate(4, new java.sql.Date(l.getDateDeb().getTime()));
            ps.setDate(5, new java.sql.Date(l.getDateFin().getTime()));
            ps.setString(6, l.getStatut());

            ps.executeUpdate();
            System.out.println("Location ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerLocation(int code) {
        String sql = "DELETE FROM location WHERE code_location = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, code);
            ps.executeUpdate();
            System.out.println("Location supprimée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierLocation(Location l) {
        String sql = "UPDATE location SET code_client = ?, code_voiture = ?, date_debut = ?, date_fin = ?, statut = ? WHERE code_location = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, l.getClient().getCodeClient());
            ps.setInt(2, l.getVoiture().getCodeVoiture());
            ps.setDate(3, new java.sql.Date(l.getDateDeb().getTime()));
            ps.setDate(4, new java.sql.Date(l.getDateFin().getTime()));
            ps.setString(5, l.getStatut());
            ps.setInt(6, l.getCodeLocation());

            ps.executeUpdate();
            System.out.println("Location modifiée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLocationStatus(int codeLocation, String statut) {
        String sql = "UPDATE location SET statut = ? WHERE code_location = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, statut);
            ps.setInt(2, codeLocation);
            ps.executeUpdate();
            System.out.println("Statut de la location mis à jour avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Location> listeLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM location";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Location l = new Location();
                l.setCodeLocation(rs.getInt("code_location"));

                // Fetch Client and Voiture objects
                IDaoClient clientDao = new ImpDaoClient();
                IDaoVoiture voitureDao = new ImpDaoVoiture();
                l.setClient(clientDao.getClientById(rs.getInt("code_client")));
                l.setVoiture(voitureDao.getVoitureById(rs.getInt("code_voiture")));

                l.setDateDeb(rs.getDate("date_debut"));
                l.setDateFin(rs.getDate("date_fin"));
                l.setStatut(rs.getString("statut"));

                locations.add(l);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    @Override
    public ArrayList<Location> getLocationsByClientId(int clientId) {
        ArrayList<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM location WHERE code_client = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Location l = new Location();
                l.setCodeLocation(rs.getInt("code_location"));

                // Fetch Client and Voiture objects
                IDaoClient clientDao = new ImpDaoClient();
                IDaoVoiture voitureDao = new ImpDaoVoiture();
                l.setClient(clientDao.getClientById(rs.getInt("code_client")));
                l.setVoiture(voitureDao.getVoitureById(rs.getInt("code_voiture")));

                l.setDateDeb(rs.getDate("date_debut"));
                l.setDateFin(rs.getDate("date_fin"));
                l.setStatut(rs.getString("statut"));

                locations.add(l);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    @Override
    public ArrayList<Location> getLocationsByStatus(String statut) {
        ArrayList<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM location WHERE statut = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, statut);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Location l = new Location();
                l.setCodeLocation(rs.getInt("code_location"));

                // Fetch Client and Voiture objects
                IDaoClient clientDao = new ImpDaoClient();
                IDaoVoiture voitureDao = new ImpDaoVoiture();
                l.setClient(clientDao.getClientById(rs.getInt("code_client")));
                l.setVoiture(voitureDao.getVoitureById(rs.getInt("code_voiture")));

                l.setDateDeb(rs.getDate("date_debut"));
                l.setDateFin(rs.getDate("date_fin"));
                l.setStatut(rs.getString("statut"));

                locations.add(l);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    @Override
    public Location getLocationById(int id) {
        String sql = "SELECT * FROM location WHERE code_location = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Location l = new Location();
                l.setCodeLocation(rs.getInt("code_location"));

                // Fetch Client and Voiture objects
                IDaoClient clientDao = new ImpDaoClient();
                IDaoVoiture voitureDao = new ImpDaoVoiture();
                l.setClient(clientDao.getClientById(rs.getInt("code_client")));
                l.setVoiture(voitureDao.getVoitureById(rs.getInt("code_voiture")));

                l.setDateDeb(rs.getDate("date_debut"));
                l.setDateFin(rs.getDate("date_fin"));
                l.setStatut(rs.getString("statut"));

                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin) {
        String sql = "SELECT COUNT(*) FROM location WHERE code_voiture = ? AND statut <> 'refusé' AND (" +
                     "(? BETWEEN date_debut AND date_fin) OR " +
                     "(? BETWEEN date_debut AND date_fin) OR " +
                     "(date_debut BETWEEN ? AND ?))";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeVoiture);
            ps.setDate(2, new java.sql.Date(debut.getTime()));
            ps.setDate(3, new java.sql.Date(fin.getTime()));
            ps.setDate(4, new java.sql.Date(debut.getTime()));
            ps.setDate(5, new java.sql.Date(fin.getTime()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Return true if no overlapping accepted rentals found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public double calculateTotalRevenue() {
        String sql = "SELECT SUM(v.prix_par_jour * DATEDIFF(l.date_fin, l.date_debut)) as total " +
                     "FROM location l JOIN voiture v ON l.code_voiture = v.code_voiture " +
                     "WHERE l.statut = 'accepté'";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<Double> getRevenuePerParc() {
        List<Double> revenues = new ArrayList<>();
        String sql = "SELECT p.code_parc, SUM(v.prix_par_jour * DATEDIFF(l.date_fin, l.date_debut)) as revenue " +
                     "FROM location l JOIN voiture v ON l.code_voiture = v.code_voiture " +
                     "JOIN parc p ON v.code_parc = p.code_parc " +
                     "WHERE l.statut = 'accepté' GROUP BY p.code_parc ORDER BY p.code_parc";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                revenues.add(rs.getDouble("revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenues;
    }

    public List<Double> getMonthlyRevenue() {
        List<Double> revenues = new ArrayList<>();
        String sql = "SELECT YEAR(l.date_debut) as year, MONTH(l.date_debut) as month, " +
                     "SUM(v.prix_par_jour * DATEDIFF(l.date_fin, l.date_debut)) as revenue " +
                     "FROM location l JOIN voiture v ON l.code_voiture = v.code_voiture " +
                     "WHERE l.statut = 'accepté' AND l.date_debut >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
                     "GROUP BY YEAR(l.date_debut), MONTH(l.date_debut) ORDER BY year, month";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Initialize the last 6 months with 0
            Calendar cal = Calendar.getInstance();
            for (int i = 5; i >= 0; i--) {
                cal.setTime(new Date());
                cal.add(Calendar.MONTH, -i);
                revenues.add(0.0);
            }
            // Fill with actual data
            while (rs.next()) {
                int month = rs.getInt("month");
                int year = rs.getInt("year");
                double revenue = rs.getDouble("revenue");
                // Calculate the index of the month relative to the last 6 months
                Calendar dataCal = Calendar.getInstance();
                dataCal.set(year, month - 1, 1);
                long monthsBetween = (cal.get(Calendar.YEAR) - dataCal.get(Calendar.YEAR)) * 12 +
                                    (cal.get(Calendar.MONTH) - dataCal.get(Calendar.MONTH));
                if (monthsBetween >= 0 && monthsBetween < 6) {
                    revenues.set((int) monthsBetween, revenue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenues;
    }

    public int countLocations() {
        String sql = "SELECT COUNT(*) as count FROM location";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countActiveLocations() {
        String sql = "SELECT COUNT(*) as count FROM location WHERE statut = 'accepté' AND date_fin > CURDATE()";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}