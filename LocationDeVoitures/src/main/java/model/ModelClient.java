package model;

import java.util.ArrayList;
import java.util.List;

import dao.IDaoClient;
import dao.ImpDaoClient;
import entities.Client;

public class ModelClient {
    private Client clt;
    private IDaoClient daoClient;

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
        return new ArrayList<>(daoClient.listeClients());
    }

    public Client getClientById(int codeClient) {
        return daoClient.getClientById(codeClient);
    }

    public int countClients() {
        return listeClients().size();
    }
}
