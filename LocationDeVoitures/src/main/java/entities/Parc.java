package entities;

import java.util.ArrayList;
public class Parc {
	private int codeParc;
    private String nomParc;
    private String libelle;
    private int capacite;
    private ArrayList<Voiture> listVoitures;
    public Parc() {}
    
	public Parc(int codeParc, String nomParc, String libelle, int capacite) {
		super();
		this.codeParc = codeParc;
		this.nomParc = nomParc;
		this.libelle = libelle;
		this.capacite = capacite;
		this.listVoitures = new ArrayList<Voiture> ();
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
	public ArrayList<Voiture> getListVoitures() {
		return listVoitures;
	}
	public void setListVoitures(ArrayList<Voiture> listVoitures) {
		this.listVoitures = listVoitures;
	}
    
}
