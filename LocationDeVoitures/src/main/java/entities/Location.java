package entities;

import java.util.Date;

public class Location {
    private int codeLocation;
    private Voiture voiture;
    private Client client;
    private Date dateDeb;
    private Date dateFin;
    private String statut; // "en attente", "accepté", "refusé"

    public Location() {
        this.statut = "en attente"; // Default status
    }

    public Location(int codeLocation, Voiture voiture, Client client, Date dateDeb, Date dateFin, String statut) {
        this.codeLocation = codeLocation;
        this.voiture = voiture;
        this.client = client;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.statut = statut != null ? statut : "en attente"; // Default to "en attente" if null
    }

    public int getCodeLocation() {
        return codeLocation;
    }

    public void setCodeLocation(int codeLocation) {
        this.codeLocation = codeLocation;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}