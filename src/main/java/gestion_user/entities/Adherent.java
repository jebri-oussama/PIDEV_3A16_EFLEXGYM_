package gestion_user.entities;


import java.sql.Date;

public class Adherent extends User{

    public Adherent(int anInt, String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe, Role role) {
        super(nom,prenom,mot_de_passe,email,date_de_naissance, sexe, Role.Adherent);
    }

    public Adherent(String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe) {
        super(nom,prenom,mot_de_passe,email,date_de_naissance, sexe,Role.Adherent);
    }

    public Adherent(String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe,Role role) {
        super(nom,prenom,mot_de_passe,email,date_de_naissance, sexe,Role.Adherent);
    }

    public Adherent(int id, String nom, String prenom, String mot_de_passe, String email, Date date_de_naissance, Sexe sexe) {
        super(id,nom,prenom,mot_de_passe,email,date_de_naissance, sexe);
    }



    @Override
    public String toString() {
        return "Adherent{ " + super.toString()+"}";
    }
}
