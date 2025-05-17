package dao;

import entities.Location;
import java.util.ArrayList;
import java.util.Date;

public interface IDaoLocation {
    void ajouterLocation(Location l);
    void supprimerLocation(int code);
    void modifierLocation(Location l);
    void updateLocationStatus(int codeLocation, String statut);
    ArrayList<Location> listeLocations();
    ArrayList<Location> getLocationsByClientId(int clientId);
    ArrayList<Location> getLocationsByStatus(String statut);
    Location getLocationById(int id);
    boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin);
}