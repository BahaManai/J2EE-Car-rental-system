package entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_location")
    private int codeLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_voiture")
    private Voiture voiture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_client")
    private Client client;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDeb;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @Column(name = "statut")
    private String statut;

    public Location() {
        this.statut = "en attente"; // Valeur par défaut
    }

    public Location(int codeLocation, Voiture voiture, Client client, Date dateDeb, Date dateFin, String statut) {
        this.codeLocation = codeLocation;
        this.voiture = voiture;
        this.client = client;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.statut = (statut != null) ? statut : "en attente";
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
