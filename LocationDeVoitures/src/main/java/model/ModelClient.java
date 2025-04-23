package model;

import java.util.ArrayList;
import java.util.List;

import dao.ImpDaoClient;
import entities.Client;

public class ModelClient {
    private Client clt;
    private ImpDaoClient daoClient;

    public ModelClient() {
        this.daoClient = new ImpDaoClient();
    }

    public void setClient(Client client) {
        this.clt = client;
    }

    public void ajouterClient() {
        if (this.clt != null) {
            daoClient.ajouterClient(this.clt);
        } else {
            System.err.println("Aucun client à ajouter.");
        }
    }

    public void modifierClient() {
        if (this.clt != null) {
            daoClient.modifierClient(this.clt);
        } else {
            System.err.println("Aucun client à modifier.");
        }
    }

    public void supprimerClient(int codeClient) {
        if (codeClient > 0) {
            daoClient.supprimerClient(codeClient);
        } else {
            System.err.println("Code client invalide.");
        }
    }
    
    public ArrayList<Client> listeClients() {
    	 return daoClient.listeClients();
    }
    
    public Client getClientById(int codeClient) {
        return daoClient.getClientById(codeClient);
    }
    
    public Client rechercherParCIN(String cin) {
        return daoClient.findByNCIN(cin);
    }

    public List<Client> rechercherParNom(String nom) {
        return daoClient.findByNom(nom);
    }

}
