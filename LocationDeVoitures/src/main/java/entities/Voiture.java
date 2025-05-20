package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "voiture")
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codevoiture;

    @Column(nullable = false)
    private String matricule;

    @Column
    private String model;

    @Column
    private float kilometrage;

    @Column(name = "prix_par_jour", nullable = false)
    private float prixParJour;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "code_parc")
    private Parc parc;

    public Voiture() {}

    public Voiture(int codevoiture, String matricule, String model, float kilometrage, float prixParJour, String image, Parc parc) {
        this.codevoiture = codevoiture;
        this.matricule = matricule;
        this.model = model;
        this.kilometrage = kilometrage;
        this.prixParJour = prixParJour;
        this.image = image;
        this.parc = parc;
    }

    public int getCodeVoiture() {
        return codevoiture;
    }

    public void setCodeVoiture(int codeVoiture) {
        this.codevoiture = codeVoiture;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(float kilometrage) {
        this.kilometrage = kilometrage;
    }

    public float getPrixParJour() {
        return prixParJour;
    }

    public void setPrixParJour(float prixParJour) {
        this.prixParJour = prixParJour;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Parc getParc() {
        return parc;
    }

    public void setParc(Parc parc) {
        this.parc = parc;
    }
}
