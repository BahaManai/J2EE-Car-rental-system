package dao;
import java.util.List;
import entities.Client;
import entities.Voiture;
public interface IDaoClient
{
	public void ajouterClient(Client c);
	public void modifierClient(Client c);
	public void supprimerClient(int code);
	public List<Client> listeClients();
	Client getClientById(int codeClient);
	Client findByEmailAndPassword(String email, String motDePasse);
	Client findByEmail(String email);
}