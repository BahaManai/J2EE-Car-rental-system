package model;

import java.util.ArrayList;
import dao.ImpDaoVoiture;
import entities.Voiture;

public class ModelVoiture {
    private Voiture voiture;
    private ImpDaoVoiture daoVoiture;

    public ModelVoiture() {
        this.daoVoiture = new ImpDaoVoiture();
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public void ajouterVoiture() {
        if (this.voiture != null) {
            daoVoiture.ajouterVoiture(this.voiture);
        } else {
            System.err.println("Aucune voiture à ajouter.");
        }
    }

    public void modifierVoiture() {
        if (this.voiture != null) {
            daoVoiture.modifierVoiture(this.voiture);
        } else {
            System.err.println("Aucune voiture à modifier.");
        }
    }

    public void supprimerVoiture(int codeVoiture) {
        if (codeVoiture > 0) {
            daoVoiture.supprimerVoiture(codeVoiture);
        } else {
            System.err.println("Code voiture invalide.");
        }
    }

    public ArrayList<Voiture> listeVoitures() {
        return daoVoiture.listeVoitures();
    }

    public Voiture getVoitureById(int codeVoiture) {
        return daoVoiture.getVoitureById(codeVoiture);
    }
}
