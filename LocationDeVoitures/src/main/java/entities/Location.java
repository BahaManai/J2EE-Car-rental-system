package entities;
import java.util.Date;
public class Location {
	private Voiture voiture;
	private Client client;
	private Date dateDeb;
	private Date dateFin;
	public Location() {}
	public Location(Voiture voiture, Client client, Date dateDeb, Date dateFin) {
		super();
		this.voiture = voiture;
		this.client = client;
		this.dateDeb = dateDeb;
		this.dateFin = dateFin;
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
