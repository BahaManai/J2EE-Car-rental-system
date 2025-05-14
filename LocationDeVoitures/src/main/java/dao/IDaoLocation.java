package dao;

import entities.Location;
import java.util.ArrayList;
import java.util.Date;

public interface IDaoLocation {
    void ajouterLocation(Location l);
    void supprimerLocation(int code);
    void modifierLocation(Location l);
    ArrayList<Location> listeLocations();
    Location getLocationById(int id);
    boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin);
}