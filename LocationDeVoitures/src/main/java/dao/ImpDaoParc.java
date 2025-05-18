package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Parc;
import entities.Voiture;
import utilitaire.SingletonConnexion;

public class ImpDaoParc implements IDaoParc {
    private Connection con = SingletonConnexion.getConnection();

    @Override
    public void ajouterParc(Parc p) {
        String sql = "INSERT INTO parc (nom_parc, libelle, capacite) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNomParc());
            ps.setString(2, p.getLibelle());
            ps.setInt(3, p.getCapacite());
            ps.executeUpdate();
            System.out.println("Parc ajouté avec succès.");
        } catch (SQLException e) {
            System.err.println("Error adding parc: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void modifierParc(Parc p) {
        String sql = "UPDATE parc SET nom_parc = ?, libelle = ?, capacite = ? WHERE code_parc = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNomParc());
            ps.setString(2, p.getLibelle());
            ps.setInt(3, p.getCapacite());
            ps.setInt(4, p.getCodeParc());
            ps.executeUpdate();
            System.out.println("Parc modifié avec succès.");
        } catch (SQLException e) {
            System.err.println("Error modifying parc: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerParc(int codeParc) {
        String sql = "DELETE FROM parc WHERE code_parc = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeParc);
            ps.executeUpdate();
            System.out.println("Parc supprimé avec succès.");
        } catch (SQLException e) {
            System.err.println("Error deleting parc: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Parc> listeParcs() {
        ArrayList<Parc> liste = new ArrayList<>();
        String sqlParc = "SELECT code_parc, nom_parc, libelle, capacite FROM parc";
        System.out.println("Executing parc query: " + sqlParc);
        try (PreparedStatement ps = con.prepareStatement(sqlParc);
             ResultSet rs = ps.executeQuery()) {
            int parcCount = 0;
            while (rs.next()) {
                Parc p = new Parc();
                p.setCodeParc(rs.getInt("code_parc"));
                p.setNomParc(rs.getString("nom_parc"));
                p.setLibelle(rs.getString("libelle"));
                p.setCapacite(rs.getInt("capacite"));
                System.out.println("Found parc: " + p.getCodeParc() + ", " + p.getNomParc());

                // Get car count
                String sqlCount = "SELECT COUNT(*) AS nb_voitures FROM voiture WHERE code_parc = ?";
                try (PreparedStatement psCount = con.prepareStatement(sqlCount)) {
                    psCount.setInt(1, p.getCodeParc());
                    ResultSet rsCount = psCount.executeQuery();
                    if (rsCount.next()) {
                        p.setNbVoitures(rsCount.getInt("nb_voitures"));
                        System.out.println("Parc " + p.getCodeParc() + " has " + p.getNbVoitures() + " voitures");
                    } else {
                        p.setNbVoitures(0);
                        System.out.println("Parc " + p.getCodeParc() + " has 0 voitures (no count)");
                    }
                } catch (SQLException e) {
                    System.err.println("Error fetching car count for parc " + p.getCodeParc() + ": " + e.getMessage());
                    e.printStackTrace();
                    p.setNbVoitures(0);
                }

                // Set empty listVoitures to avoid querying problematic voiture table
                p.setListVoitures(new ArrayList<>());
                liste.add(p);
                parcCount++;
            }
            System.out.println("Total parcs found: " + parcCount);
        } catch (SQLException e) {
            System.err.println("Error fetching parc list: " + e.getMessage());
            e.printStackTrace();
        }
        if (liste.isEmpty()) {
            System.out.println("No parcs found in database");
        }
        return liste;
    }

    @Override
    public Parc getParcById(int codeParc) {
        String sql = "SELECT code_parc, nom_parc, libelle, capacite FROM parc WHERE code_parc = ?";
        System.out.println("Executing getParcById query for code_parc: " + codeParc);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeParc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Parc p = new Parc();
                p.setCodeParc(rs.getInt("code_parc"));
                p.setNomParc(rs.getString("nom_parc"));
                p.setLibelle(rs.getString("libelle"));
                p.setCapacite(rs.getInt("capacite"));
                System.out.println("Found parc: " + p.getCodeParc() + ", " + p.getNomParc());

                // Get car count
                String sqlCount = "SELECT COUNT(*) AS nb_voitures FROM voiture WHERE code_parc = ?";
                try (PreparedStatement psCount = con.prepareStatement(sqlCount)) {
                    psCount.setInt(1, p.getCodeParc());
                    ResultSet rsCount = psCount.executeQuery();
                    if (rsCount.next()) {
                        p.setNbVoitures(rsCount.getInt("nb_voitures"));
                        System.out.println("Parc " + p.getCodeParc() + " has " + p.getNbVoitures() + " voitures");
                    } else {
                        p.setNbVoitures(0);
                        System.out.println("Parc " + p.getCodeParc() + " has 0 voitures (no count)");
                    }
                } catch (SQLException e) {
                    System.err.println("Error fetching car count for parc " + p.getCodeParc() + ": " + e.getMessage());
                    e.printStackTrace();
                    p.setNbVoitures(0);
                }

                // Set empty listVoitures to avoid querying problematic voiture table
                p.setListVoitures(new ArrayList<>());
                return p;
            } else {
                System.out.println("No parc found with code_parc: " + codeParc);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching parc by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getParcNames() {
        List<String> names = new ArrayList<>();
        String sql = "SELECT nom_parc FROM parc ORDER BY code_parc";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("nom_parc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public int countParcs() {
        String sql = "SELECT COUNT(*) as count FROM parc";
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