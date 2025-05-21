package dao;

import entities.Location;
import java.util.List;
import java.util.Date;

public interface IDaoLocation {
    void ajouterLocation(Location l);
    void supprimerLocation(int code);
    void modifierLocation(Location l);
    void updateLocationStatus(int codeLocation, String statut);
    List<Location> listeLocations();
    List<Location> getLocationsByClientId(int clientId);
    List<Location> getLocationsByStatus(String statut);
    Location getLocationById(int id);
    boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin);
}
