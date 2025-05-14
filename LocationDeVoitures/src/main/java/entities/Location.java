package entities;
import java.util.Date;
public class Location {
	private int codeLocation;
	private Voiture voiture;
	private Client client;
	private Date dateDeb;
	private Date dateFin;
	public Location() {}
	public Location(int codeLocation, Voiture voiture, Client client, Date dateDeb, Date dateFin) {
		super();
		this.codeLocation = codeLocation;
		this.voiture = voiture;
		this.client = client;
		this.dateDeb = dateDeb;
		this.dateFin = dateFin;
	}
	public int getCodeLocation() {
		return codeLocation;
	}
	public void setCodeLocation(int codeClient) {
		this.codeLocation = codeClient;
	}
	
	public Voiture getVoiture() {
		return voiture;
	}
	public void setVoiture(Voiture voiture) {
		this.voiture = voiture;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Date getDateDeb() {
		return dateDeb;
	}
	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
}
