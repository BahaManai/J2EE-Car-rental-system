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
    public void ajouterClient(Client client) {
        String sql = "INSERT INTO client (code_client, CIN, nom, prenom, adresse, email, tel, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, client.getCodeClient());
            ps.setString(2, client.getCIN());
            ps.setString(3, client.getNom());
            ps.setString(4, client.getPrenom());
            ps.setString(5, client.getAdresse());
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getTel());
            ps.setInt(8, client.getAge());

            ps.executeUpdate();
            System.out.println("Client ajouté avec succès.");
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierClient(Client client) {
        String sql = "UPDATE client SET CIN = ?, nom = ?, prenom = ?, adresse = ?, email = ?, tel = ?, age = ? WHERE code_client = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, client.getCIN());
            ps.setString(2, client.getNom());
            ps.setString(3, client.getPrenom());
            ps.setString(4, client.getAdresse());
            ps.setString(5, client.getEmail());
            ps.setString(6, client.getTel());
            ps.setInt(7, client.getAge());
            ps.setInt(8, client.getCodeClient());

            ps.executeUpdate();
            System.out.println("Client modifié avec succès.");
        }  catch (SQLException e) {
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
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Client> listeClients() {
		ArrayList<Client> l = new ArrayList<>();

		try {
			if (con == null) {
				System.err.println("Connection is null.");
				return l;
			}
			PreparedStatement ps = con.prepareStatement("SELECT * FROM client");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				l.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8)));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
    
    
    public Client getClientById(int codeClient) {
        System.out.println("DEBUG: Searching for client ID: " + codeClient);
        String sql = "SELECT * FROM client WHERE code_client = ?";
        
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
        String sql = "SELECT * FROM client WHERE CIN = ?";
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
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> findByNom(String nom) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE nom LIKE ?";
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
            e.printStackTrace();
        }
        return clients;
    }
    public Client findByEmailAndPassword(String email, String motDePasse) {
        String sql = "SELECT * FROM client WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, motDePasse);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Client c = new Client();
                c.setCodeClient(rs.getInt("code_client"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setCIN(rs.getString("CIN"));
                c.setEmail(rs.getString("email"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
                c.setRole(rs.getString("role"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
