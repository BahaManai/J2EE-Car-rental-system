package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Parc;
import entities.Voiture;
import utilitaire.SingletonConnexion;

public class ImpDaoVoiture implements IDaoVoiture {
    private Connection con = SingletonConnexion.getConnection();

    @Override
    public void ajouterVoiture(Voiture v) {
        String sql = "INSERT INTO voiture (matricule, model, kilometrage, code_parc, prix_par_jour, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getMatricule());
            ps.setString(2, v.getModel());
            ps.setFloat(3, v.getKilometrage());
            ps.setInt(4, v.getParc().getCodeParc());
            ps.setFloat(5, v.getPrixParJour());
            ps.setString(6, v.getImage());
            ps.executeUpdate();
            System.out.println("Voiture ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierVoiture(Voiture v) {
        String sql = "UPDATE voiture SET matricule = ?, model = ?, kilometrage = ?, code_parc = ?, prix_par_jour = ?, image = ? WHERE code_voiture = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getMatricule());
            ps.setString(2, v.getModel());
            ps.setFloat(3, v.getKilometrage());
            ps.setInt(4, v.getParc().getCodeParc());
            ps.setFloat(5, v.getPrixParJour());
            ps.setString(6, v.getImage());
            ps.setInt(7, v.getCodeVoiture());
            ps.executeUpdate();
            System.out.println("Voiture modifiée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerVoiture(int codeVoiture) {
        String sql = "DELETE FROM voiture WHERE code_voiture = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeVoiture);
            ps.executeUpdate();
            System.out.println("Voiture supprimée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Voiture> listeVoitures() {
        ArrayList<Voiture> list = new ArrayList<>();
        String sql = "SELECT v.code_voiture, v.matricule, v.model, v.kilometrage, v.prix_par_jour, v.image,\r\n"
        		+ "       p.code_parc, p.nom_parc\r\n"
        		+ "FROM voiture v LEFT JOIN parc p ON v.code_parc = p.code_parc";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Voiture v = new Voiture();
                v.setCodeVoiture(rs.getInt("code_voiture"));
                v.setMatricule(rs.getString("matricule"));
                v.setModel(rs.getString("model"));
                v.setKilometrage(rs.getFloat("kilometrage"));
                v.setPrixParJour(rs.getFloat("prix_par_jour"));
                v.setImage(rs.getString("image"));

                Parc p = new Parc();
                p.setCodeParc(rs.getInt("code_parc"));
                p.setNomParc(rs.getString("nom_parc"));
                v.setParc(p);

                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Voiture getVoitureById(int codeVoiture) {
        String sql = "SELECT v.code_voiture, v.matricule, v.model, v.kilometrage, v.prix_par_jour, v.image, " +
                    "p.code_parc, p.nom_parc " +
                    "FROM voiture v LEFT JOIN parc p ON v.code_parc = p.code_parc " +
                    "WHERE v.code_voiture = ?";
        System.out.println("Executing getVoitureById query for code_voiture: " + codeVoiture);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeVoiture);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Voiture v = new Voiture();
                v.setCodeVoiture(rs.getInt("code_voiture"));
                v.setMatricule(rs.getString("matricule"));
                v.setModel(rs.getString("model"));
                v.setKilometrage(rs.getFloat("kilometrage"));
                v.setPrixParJour(rs.getFloat("prix_par_jour"));
                v.setImage(rs.getString("image"));

                Parc p = new Parc();
                p.setCodeParc(rs.getInt("code_parc"));
                p.setNomParc(rs.getString("nom_parc"));
                v.setParc(p);

                System.out.println("Found voiture: code_voiture=" + v.getCodeVoiture() + ", matricule=" + v.getMatricule());
                return v;
            } else {
                System.out.println("No voiture found with code_voiture: " + codeVoiture);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching voiture by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public double calculateOccupancyRate() {
        String sql = "SELECT (COUNT(DISTINCT l.code_voiture) / (SELECT COUNT(*) FROM voiture) * 100) as rate " +
                    "FROM location l " +
                    "WHERE l.statut = 'accepté' AND l.date_fin > CURDATE()";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("rate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<String> getCarTypeLabels() {
        List<String> labels = new ArrayList<>();
        String sql = "SELECT DISTINCT model FROM voiture";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                labels.add(rs.getString("model"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    public List<Integer> getCarTypeCounts() {
        List<Integer> counts = new ArrayList<>();
        String sql = "SELECT model, COUNT(*) as count FROM voiture GROUP BY model";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                counts.add(rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counts;
    }

    public int countVoitures() {
        String sql = "SELECT COUNT(*) as count FROM voiture";
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