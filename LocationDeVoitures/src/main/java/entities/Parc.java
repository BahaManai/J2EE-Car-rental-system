package entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parc")
public class Parc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codeParc;

    @Column(name = "nom_parc", nullable = false)
    private String nomParc;

    @Column
    private String libelle;

    @Column
    private int capacite;

    @Column(name = "nb_voitures")
    private int nbVoitures;

    @OneToMany(mappedBy = "parc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Voiture> listVoitures = new ArrayList<>();

    public Parc() {}

    public Parc(int codeParc, String nomParc, String libelle, int capacite) {
        this.codeParc = codeParc;
        this.nomParc = nomParc;
        this.libelle = libelle;
        this.capacite = capacite;
        this.nbVoitures = 0;
    }

    public int getCodeParc() {
        return codeParc;
    }

    public void setCodeParc(int codeParc) {
        this.codeParc = codeParc;
    }

    public String getNomParc() {
        return nomParc;
    }

    public void setNomParc(String nomParc) {
        this.nomParc = nomParc;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getNbVoitures() {
        return nbVoitures;
    }

    public void setNbVoitures(int nbVoitures) {
        this.nbVoitures = nbVoitures;
    }

    public List<Voiture> getListVoitures() {
        return listVoitures;
    }

    public void setListVoitures(List<Voiture> listVoitures) {
        this.listVoitures = listVoitures;
    }
}
