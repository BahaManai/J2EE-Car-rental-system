package entities;

public class Voiture {
	private int codevoiture;
    private String matricule;
    private String model;
    private float kilometrage;
    private Parc parc;
    public Voiture() {}
	public Voiture(int codevoiture, String matricule, String model, float kilometrage, Parc parc) {
		super();
		this.codevoiture = codevoiture;
		this.matricule = matricule;
		this.model = model;
		this.kilometrage = kilometrage;
		this.parc = parc;
	}
	public int getCodeVoiture() {
		return codevoiture;
	}
	public void setCodeVoiture(int codeVoiture) {
		this.codevoiture = codeVoiture;
	}
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public float getKilometrage() {
		return kilometrage;
	}
	public void setKilometrage(float kilometrage) {
		this.kilometrage = kilometrage;
	}
	public Parc getParc() {
		return parc;
	}
	public void setParc(Parc parc) {
		this.parc = parc;
	}
    
}
