package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_client")
    private int codeClient;

    @Column(name = "CIN", nullable = false, unique = true)
    private String CIN;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "age")
    private int age;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "role")
    private String role;

    public Client() {}

    public Client(int codeClient, String cIN, String nom, String prenom, String adresse, String email, String tel, int age) {
        this.codeClient = codeClient;
        this.CIN = cIN;
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

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
