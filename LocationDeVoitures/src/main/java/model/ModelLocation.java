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

    public Location getLocationById(int codeLocation) {
        return daoLocation.getLocationById(codeLocation);
    }

    public boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin) {
        return daoLocation.estVoitureDisponible(codeVoiture, debut, fin);
    }
}