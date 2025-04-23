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
        String sql = "INSERT INTO voiture (matricule, model, kilometrage, code_parc) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getMatricule());
            ps.setString(2, v.getModel());
            ps.setFloat(3, v.getKilometrage());
            ps.setInt(4, v.getParc().getCodeParc());
            ps.executeUpdate();
            System.out.println("Voiture ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierVoiture(Voiture v) {
        String sql = "UPDATE voiture SET matricule = ?, model = ?, kilometrage = ?, code_parc = ? WHERE code_voiture = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getMatricule());
            ps.setString(2, v.getModel());
            ps.setFloat(3, v.getKilometrage());
            ps.setInt(4, v.getParc().getCodeParc());
            ps.setInt(5, v.getCodeVoiture());
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
        String sql = "SELECT * FROM voiture";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Voiture v = new Voiture();
                v.setCodeVoiture(rs.getInt("code_voiture"));
                v.setMatricule(rs.getString("matricule"));
                v.setModel(rs.getString("model"));
                v.setKilometrage(rs.getFloat("kilometrage"));

                Parc p = new Parc();
                p.setCodeParc(rs.getInt("code_parc"));
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
        String sql = "SELECT * FROM voiture WHERE code_voiture = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeVoiture);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Voiture v = new Voiture();
                v.setCodeVoiture(rs.getInt("code_voiture"));
                v.setMatricule(rs.getString("matricule"));
                v.setModel(rs.getString("model"));
                v.setKilometrage(rs.getFloat("kilometrage"));
                Parc p = new Parc();
                p.setCodeParc(rs.getInt("code_parc"));
                v.setParc(p);
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
