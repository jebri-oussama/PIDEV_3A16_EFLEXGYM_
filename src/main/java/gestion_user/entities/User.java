package gestion_user.entities;


import javafx.beans.property.IntegerProperty;

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

    private int idBilanFinancier;






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

    public User(String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role, int idBilanFinancier, double salaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.role=role;
        this.idBilanFinancier=idBilanFinancier;
        this.salaire=salaire;
    }

    public User(int anInt, String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role, double salaire, int idBilanFinancier) {

        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.role=role;
        this.idBilanFinancier=idBilanFinancier;
        this.salaire=salaire;

    }

    public User() {

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

    public int getBilanFinancier() {
        return idBilanFinancier;
    }

    public void setBilanFinancier(int idBilanFinancier) {
        this.idBilanFinancier = idBilanFinancier;
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
                ", idBilanFinancier=" + idBilanFinancier +
                '}';
    }
}