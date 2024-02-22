package gestion_user.entities;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class User {

    private  int id;
    private String nom;
    private String prenom;
    private String mot_de_passe;
    private String email;
    private Date date_de_naissance;
    private Sexe sexe;
    private Role role;

    private double salaire;

    private int id_bilan_financier;


    public User(String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role, double aDouble, int anInt) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.role=role;
    }

    public User(int anInt,String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe) {

        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
    }
    public User(String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.role=role;
    }

    public User(int anInt, String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.role=role;
    }

    public User(String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role, int id_bilan_financier, double salaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.role=role;
        this.id_bilan_financier=id_bilan_financier;
        this.salaire=salaire;
    }

    public User(int anInt, String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role, double salaire, int id_bilan_financier) {

        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.role=role;
        this.id_bilan_financier=id_bilan_financier;
        this.salaire=salaire;

    }


    public  int getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
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

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }
    public String getFormattedDateNaissance() {
        // Format the date using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date_de_naissance);
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public int getId_bilan_financier() {
        return id_bilan_financier;
    }

    public void setId_bilan_financier(int id_bilan_financier) {
        this.id_bilan_financier = id_bilan_financier;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", email='" + email + '\'' +
                ", date_de_naissance=" + date_de_naissance +
                ", sexe=" + sexe +
                ", role=" + role +
                ", salaire=" + salaire +
                ", id_bilan_financier=" + id_bilan_financier +
                '}';
    }
}