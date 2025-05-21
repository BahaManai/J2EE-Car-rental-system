package model;

import java.util.ArrayList;
import java.util.List;
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

    public int countVoitures() {
        return daoVoiture.countVoitures(); // Utiliser une requête SQL directe
    }

    public double calculateOccupancyRate() {
        return daoVoiture.calculateOccupancyRate(); // Taux d'occupation des voitures
    }

    public List<String> getCarTypeLabels() {
        return daoVoiture.getCarTypeLabels(); // Catégories de voitures
    }

    public List<Integer> getCarTypeCounts() {
        return daoVoiture.getCarTypeCounts(); // Nombre de voitures par catégorie
    }
    
    public List<Object[]> getMostReservedCars() {
        return daoVoiture.getMostReservedCars();
    }
}