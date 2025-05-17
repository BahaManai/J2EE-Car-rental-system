package model;

import java.util.ArrayList;
import java.util.Date;

import dao.ImpDaoLocation;
import entities.Location;

public class ModelLocation {
    private Location location;
    private ImpDaoLocation daoLocation;

    public ModelLocation() {
        this.daoLocation = new ImpDaoLocation();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void ajouterLocation() {
        if (this.location != null) {
            daoLocation.ajouterLocation(this.location);
        } else {
            System.err.println("Aucune location à ajouter.");
        }
    }

    public void modifierLocation() {
        if (this.location != null) {
            daoLocation.modifierLocation(this.location);
        } else {
            System.err.println("Aucune location à modifier.");
        }
    }

    public void updateLocationStatus(int codeLocation, String statut) {
        if (codeLocation > 0 && statut != null && 
            (statut.equals("en attente") || statut.equals("accepté") || statut.equals("refusé"))) {
            daoLocation.updateLocationStatus(codeLocation, statut);
        } else {
            System.err.println("Code location ou statut invalide.");
        }
    }

    public void supprimerLocation(int codeLocation) {
        if (codeLocation > 0) {
            daoLocation.supprimerLocation(codeLocation);
        } else {
            System.err.println("Code location invalide.");
        }
    }

    public ArrayList<Location> listeLocations() {
        return daoLocation.listeLocations();
    }

    public ArrayList<Location> getLocationsByClientId(int clientId) {
        return daoLocation.getLocationsByClientId(clientId);
    }

    public ArrayList<Location> getLocationsByStatus(String statut) {
        if (statut != null && 
            (statut.equals("en attente") || statut.equals("accepté") || statut.equals("refusé"))) {
            return daoLocation.getLocationsByStatus(statut);
        }
        return new ArrayList<>();
    }

    public Location getLocationById(int codeLocation) {
        return daoLocation.getLocationById(codeLocation);
    }

    public boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin) {
        return daoLocation.estVoitureDisponible(codeVoiture, debut, fin);
    }

    public int countLocations() {
        return listeLocations().size(); // Or use a direct SQL query via DAO
    }

    public int countActiveLocations() {
        // Count locations with statut = "accepté" and end date in the future
        int count = 0;
        java.util.Date today = new java.util.Date();
        for (Location location : getLocationsByStatus("accepté")) {
            if (location.getDateFin().after(today)) {
                count++;
            }
        }
        return count; // Or use a direct SQL query
    }
}