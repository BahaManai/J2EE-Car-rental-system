package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Client;
import utilitaire.SingletonConnexion;

public class ImpDaoClient implements IDaoClient {
    private Connection con = SingletonConnexion.getConnection();

   

    @Override
    public void modifierClient(Client client) {
        String sql = "UPDATE client SET CIN = ?, nom = ?, prenom = ?, adresse = ?, email = ?, tel = ?, age = ?, mot_de_passe = ? WHERE code_client = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, client.getCIN());
            ps.setString(2, client.getNom());
            ps.setString(3, client.getPrenom());
            ps.setString(4, client.getAdresse());
            ps.setString(5, client.getEmail());
            ps.setString(6, client.getTel());
            ps.setInt(7, client.getAge());
            ps.setString(8, client.getMotDePasse() != null ? client.getMotDePasse() : null);
            ps.setInt(9, client.getCodeClient());

            ps.executeUpdate();
            System.out.println("Client modifié avec succès.");
        } catch (SQLException e) {
            System.err.println("Error modifying client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerClient(int codeClient) {
        String sql = "DELETE FROM client WHERE code_client = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeClient);
            
            ps.executeUpdate();
            System.out.println("Client supprimé avec succès.");
        } catch (SQLException e) {
            System.err.println("Error deleting client: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public ArrayList<Client> listeClients() {
        ArrayList<Client> l = new ArrayList<>();
        String sql = "SELECT code_client, CIN, nom, prenom, adresse, email, tel, age FROM client";
        
        try {
            if (con == null) {
                System.err.println("Connection is null.");
                return l;
            }
            System.out.println("Executing query: " + sql);
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Client c = new Client();
                    c.setCodeClient(rs.getInt("code_client"));
                    c.setCIN(rs.getString("CIN"));
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString("prenom"));
                    c.setAdresse(rs.getString("adresse"));
                    c.setEmail(rs.getString("email"));
                    c.setTel(rs.getString("tel"));
                    c.setAge(rs.getInt("age"));
                    l.add(c);
                    System.out.println("Found client: " + c.getCIN() + ", " + c.getNom() + " " + c.getPrenom());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching clients: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Total clients found: " + l.size());
        return l;
    }
    
    @Override
    public Client getClientById(int codeClient) {
        System.out.println("DEBUG: Searching for client ID: " + codeClient);
        String sql = "SELECT code_client, CIN, nom, prenom, adresse, email, tel, age FROM client WHERE code_client = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeClient);
            System.out.println("DEBUG: Executing query: " + ps.toString());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("DEBUG: Client found in database");
                Client c = new Client();
                c.setCodeClient(rs.getInt("code_client"));
                c.setCIN(rs.getString("CIN"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAdresse(rs.getString("adresse"));
                c.setEmail(rs.getString("email"));
                c.setTel(rs.getString("tel"));
                c.setAge(rs.getInt("age"));
                return c;
            } else {
                System.out.println("DEBUG: No client found with ID: " + codeClient);
            }
        } catch (SQLException e) {
            System.err.println("ERROR in getClientById: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public Client findByNCIN(String cin) {
        String sql = "SELECT code_client, CIN, nom, prenom, adresse, email, tel, age FROM client WHERE CIN = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Client c = new Client();
                c.setCodeClient(rs.getInt("code_client"));
                c.setCIN(rs.getString("CIN"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAdresse(rs.getString("adresse"));
                c.setEmail(rs.getString("email"));
                c.setTel(rs.getString("tel"));
                c.setAge(rs.getInt("age"));
                return c;
            }
        } catch (SQLException e) {
            System.err.println("Error in findByNCIN: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> findByNom(String nom) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT code_client, CIN, nom, prenom, adresse, email, tel, age FROM client WHERE nom LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Client c = new Client();
                c.setCodeClient(rs.getInt("code_client"));
                c.setCIN(rs.getString("CIN"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAdresse(rs.getString("adresse"));
                c.setEmail(rs.getString("email"));
                c.setTel(rs.getString("tel"));
                c.setAge(rs.getInt("age"));
                clients.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error in findByNom: " + e.getMessage());
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client findByEmailAndPassword(String email, String motDePasse) {
        String sql = "SELECT code_client, CIN, nom, prenom, adresse, email, tel, age, mot_de_passe, role FROM client WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, motDePasse);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Client c = new Client();
                c.setCodeClient(rs.getInt("code_client"));
                c.setCIN(rs.getString("CIN"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAdresse(rs.getString("adresse"));
                c.setEmail(rs.getString("email"));
                c.setTel(rs.getString("tel"));
                c.setAge(rs.getInt("age"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
                c.setRole(rs.getString("role"));
                return c;
            }
        } catch (SQLException e) {
            System.err.println("Error in findByEmailAndPassword: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void ajouterClient(Client client) {
        String sql = "INSERT INTO client (code_client, CIN, nom, prenom, adresse, email, tel, age, mot_de_passe, salt, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, client.getCodeClient());
            ps.setString(2, client.getCIN());
            ps.setString(3, client.getNom());
            ps.setString(4, client.getPrenom());
            ps.setString(5, client.getAdresse());
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getTel());
            ps.setInt(8, client.getAge());
            ps.setString(9, client.getMotDePasse());
            ps.setString(10, client.getSalt());
            ps.setString(11, "client");

            ps.executeUpdate();
        } catch (SQLException e) {
            // handle exception
        }
    }

    // Update findByEmailAndPassword to findByEmail only
    public Client findByEmail(String email) {
        String sql = "SELECT code_client, CIN, nom, prenom, adresse, email, tel, age, mot_de_passe, salt, role FROM client WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Client c = new Client();
                c.setCodeClient(rs.getInt("code_client"));
                c.setCIN(rs.getString("CIN"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAdresse(rs.getString("adresse"));
                c.setEmail(rs.getString("email"));
                c.setTel(rs.getString("tel"));
                c.setAge(rs.getInt("age"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
                c.setSalt(rs.getString("salt"));
                c.setRole(rs.getString("role"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}