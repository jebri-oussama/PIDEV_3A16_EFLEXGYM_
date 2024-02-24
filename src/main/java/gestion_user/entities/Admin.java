package gestion_user.entities;

public class Admin {
    private String username;
    private String email;
    private String mot_de_passe;


    public Admin(String username, String prenom, String mot_de_passe, String email) {
        this.username = username;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
    }

}
