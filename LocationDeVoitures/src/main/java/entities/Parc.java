package entities;

import java.util.ArrayList;

public class Parc {
    private int codeParc;
    private String nomParc;
    private String libelle;
    private int capacite;
    private int nbVoitures;
    private ArrayList<Voiture> listVoitures;

    public Parc() {
        this.listVoitures = new ArrayList<Voiture>();
    }

    public Parc(int codeParc, String nomParc, String libelle, int capacite) {
        this.codeParc = codeParc;
        this.nomParc = nomParc;
        this.libelle = libelle;
        this.capacite = capacite;
        this.nbVoitures = 0;
        this.listVoitures = new ArrayList<Voiture>();
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

    public ArrayList<Voiture> getListVoitures() {
        return listVoitures;
    }

    public void setListVoitures(ArrayList<Voiture> listVoitures) {
        this.listVoitures = listVoitures;
    }
}