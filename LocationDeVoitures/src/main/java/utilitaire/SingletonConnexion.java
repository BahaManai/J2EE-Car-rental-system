package utilitaire;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SingletonConnexion {
	 private static Connection conn;
	    static {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver"); //Charger le driver MySQL
	            conn = DriverManager.getConnection(
	            	    "jdbc:mysql://localhost:3306/location_de_voitures", "root", "root"); 
	            System.out.println("Connexion réussie !");
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Erreur de connexion à la base de données.");
	        }
	    }
	    public static Connection getConnection() {
	        return conn;
	    }
}
