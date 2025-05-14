package entities;

public class Client {
	private int codeClient;
    private String CIN;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String tel;
    private int age;
    private String motDePasse;
    private String role;
    
    public Client() {}
	public Client(int codeClient, String cIN, String nom, String prenom, String adresse, String email, String tel,
			int age) {
		super();
		this.codeClient = codeClient;
		CIN = cIN;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
		this.age = age;
	}
	
	public int getCodeClient() {
		return codeClient;
	}
	public void setCodeClient(int codeClient) {
		this.codeClient = codeClient;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
