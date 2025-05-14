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
import java.util.Date;

public class ImpDaoLocation implements IDaoLocation {
    private Connection con = SingletonConnexion.getConnection();

    @Override
    public void ajouterLocation(Location l) {
        String sql = "INSERT INTO location (code_location, code_client, code_voiture, date_debut, date_fin) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, l.getCodeLocation());
            ps.setInt(2, l.getClient().getCodeClient());
            ps.setInt(3, l.getVoiture().getCodeVoiture());
            ps.setDate(4, new java.sql.Date(l.getDateDeb().getTime()));
            ps.setDate(5, new java.sql.Date(l.getDateFin().getTime()));

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
        String sql = "UPDATE location SET code_client = ?, code_voiture = ?, date_debut = ?, date_fin = ? WHERE code_location = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, l.getClient().getCodeClient());
            ps.setInt(2, l.getVoiture().getCodeVoiture());
            ps.setDate(3, new java.sql.Date(l.getDateDeb().getTime()));
            ps.setDate(4, new java.sql.Date(l.getDateFin().getTime()));
            ps.setInt(5, l.getCodeLocation());

            ps.executeUpdate();
            System.out.println("Location modifiée avec succès.");
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

                // Fetch Client and Voiture objects (assuming DAO classes exist)
                IDaoClient clientDao = new ImpDaoClient();
                IDaoVoiture voitureDao = new ImpDaoVoiture(); // Adjust based on actual Voiture DAO implementation
                l.setClient(clientDao.getClientById(rs.getInt("code_client")));
                l.setVoiture(voitureDao.getVoitureById(rs.getInt("code_voiture")));

                l.setDateDeb(rs.getDate("date_debut"));
                l.setDateFin(rs.getDate("date_fin"));

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
                IDaoVoiture voitureDao = new ImpDaoVoiture(); // Adjust based on actual Voiture DAO implementation
                l.setClient(clientDao.getClientById(rs.getInt("code_client")));
                l.setVoiture(voitureDao.getVoitureById(rs.getInt("code_voiture")));

                l.setDateDeb(rs.getDate("date_debut"));
                l.setDateFin(rs.getDate("date_fin"));

                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin) {
        String sql = "SELECT COUNT(*) FROM location WHERE code_voiture = ? AND (" +
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
                return rs.getInt(1) == 0; // Return true if no overlapping rentals found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}