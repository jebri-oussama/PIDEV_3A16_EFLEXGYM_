package gestion_user.entities;

import java.sql.Date;

public class Coach extends User {
  private static int id;
    private double salaire;
    //private BilanFinancier bf;



   public Coach(String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role coach, double salaire) {
        super(nom,prenom,mot_de_passe,email,date_de_naissance, sexe, Role.Coach);
        this.salaire = salaire;


    }

   public Coach(int id,String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, double salaire) {
       super(nom,prenom,mot_de_passe,email,date_de_naissance, sexe, Role.Coach);
       this.salaire = salaire;

    }

    public Coach(int id,String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role, double salaire) {
        super(nom,prenom,mot_de_passe,email,date_de_naissance, sexe, Role.Coach);
        this.salaire=salaire;
    }


    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }



    @Override
    public String toString() {
        return "Coach{" + super.toString() +
                ", salaire=" + salaire +
                '}';
    }
}
