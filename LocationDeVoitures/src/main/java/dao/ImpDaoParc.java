package dao;

import java.sql.*;
import java.util.ArrayList;

import entities.Parc;
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
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Parc> listeParcs() {
        ArrayList<Parc> liste = new ArrayList<>();
        String sql = "SELECT * FROM parc";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Parc p = new Parc();
                p.setCodeParc(rs.getInt("code_parc"));
                p.setNomParc(rs.getString("nom_parc"));
                p.setLibelle(rs.getString("libelle"));
                p.setCapacite(rs.getInt("capacite"));
                liste.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public Parc getParcById(int codeParc) {
        String sql = "SELECT * FROM parc WHERE code_parc = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeParc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Parc p = new Parc();
                p.setCodeParc(rs.getInt("code_parc"));
                p.setNomParc(rs.getString("nom_parc"));
                p.setLibelle(rs.getString("libelle"));
                p.setCapacite(rs.getInt("capacite"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
