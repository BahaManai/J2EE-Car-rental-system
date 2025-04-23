package dao;
import java.util.List;
import entities.Client;
public interface IDaoClient
{
	public void ajouterClient(Client c);
	public void modifierClient(Client c);
	public void supprimerClient(int code);
	public List<Client> listeClients();
}