package utilitaire;
import java.sql.Connection;

import dao.ImpDaoClient;
import entities.Client;
public class Test {

	public static void main(String[] args) {
		
		Connection con = SingletonConnexion.getConnection();
		if (con == null) {
			System.out.println("Échec de la connexion.");
		}
		ImpDaoClient daoClient= new ImpDaoClient();
		Client c1=new Client(2, "12345679", "Manai", "Baha Eddine", "Rades", "bahaeddinmanai7@gmail.com", "27162912", 20);
		daoClient.ajouterClient(c1);
		//daoClient.supprimerClient(1);
	}

}
