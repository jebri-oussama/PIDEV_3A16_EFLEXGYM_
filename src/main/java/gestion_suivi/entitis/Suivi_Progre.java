package gestion_suivi.entitis;

import gestion_user.entities.User;
import  gestion_user.entities.Sexe;

public class Suivi_Progre {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private double taille;
    private double poids;
    private double tour_de_taille;
    private String sexe;
    private User idUser; // Remplacement de l'attribut id_user par l'objet User

    public Suivi_Progre(int id, String nom, String prenom, int age, double taille, double poids, double tour_de_taille, String sexe, User idUser) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.taille = taille;
        this.poids = poids;
        this.tour_de_taille = tour_de_taille;
        this.sexe = sexe;
        this.idUser = idUser;
    }

    public Suivi_Progre(String nom, String prenom, int age, double taille, double poids, double tour_de_taille, String sexe, User idUser) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.taille = taille;
        this.poids = poids;
        this.tour_de_taille = tour_de_taille;
        this.sexe = sexe;
        this.idUser = idUser;
    }




    public double getIMC() {
        return poids / Math.pow(taille / 100, 2);
    }

    public double getIMG() {
        return (1.2 * getIMC()) + (0.23 * age) - (10.8 * (sexe.equals("homme") ? 1 : 0)) - 5.4;
    }

    public String getEvaluation() {
        double imc = getIMC();
        double img = getIMG();
        String imcEvaluation = "";
        String imgEvaluation = "";

        // Évaluation de l'IMC
        if (imc < 20) {
            imcEvaluation = "faible";
        } else if (imc < 25) {
            imcEvaluation = "normal";
        } else {
            imcEvaluation = "élevé";
        }

        // Évaluation de l'IMG
        if (img < 15) {
            imgEvaluation = "faible";
        } else if (img < 20) {
            imgEvaluation = "normal";
        } else {
            imgEvaluation = "élevé";
        }

        // Construction du message d'évaluation
        String message = "Votre IMC est " + imc + " (" + imcEvaluation + ") \n votre IMG est " + img + " (" + imgEvaluation + ").\n ";

        // Déterminer le programme en fonction de l'évaluation combinée de l'IMC et de l'IMG
        String programme = "";
        if (imcEvaluation.equals("faible") && imgEvaluation.equals("faible")) {
            programme = "3";
        } else if (imcEvaluation.equals("normal") && imgEvaluation.equals("normal")) {
            programme = "2";
        } else {
            programme = "1";
        }

        // Ajouter le message final
        message += "Vous devez ";
        if (imcEvaluation.equals("faible") || imgEvaluation.equals("faible")) {
            message += "prendre de la masse";
        } else if (imcEvaluation.equals("normal") || imgEvaluation.equals("normal")) {
            message += "vous mettre en forme";
        } else {
            message += "perdre du poids";
        }
        message += ". Voir le programme " + programme + ".\n Courage!";

        return message;
    }

    @Override
    public String toString() {
        return "Suivi_Progre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", taille=" + taille +
                ", poids=" + poids +
                ", tour_de_taille=" + tour_de_taille +
                ", sexe=" + sexe +
                ", user=" + idUser +
                ", IMC=" + getIMC() +
                ", IMG=" + getIMG() +
                ", evaluation='" + getEvaluation() + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getTour_de_taille() {
        return tour_de_taille;
    }

    public void setTour_de_taille(double tour_de_taille) {
        this.tour_de_taille = tour_de_taille;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
}

