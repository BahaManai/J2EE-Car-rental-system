package dao;

import java.util.List;
import entities.Voiture;

public interface IDaoVoiture {
    void ajouterVoiture(Voiture v);
    void modifierVoiture(Voiture v);
    void supprimerVoiture(int codeVoiture);
    List<Voiture> listeVoitures();
    Voiture getVoitureById(int codeVoiture);
    List<Object[]> getMostReservedCars();
}