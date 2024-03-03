package gestion_produit.entities;

import gestion_user.entities.User;

public class panier {
    private int id;
    private String nom;
    private float prix;
    private User id_user ;

    public panier(int id, String nom, float prix,User id_user) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.id_user = id_user;
    }
    public panier( String nom, float prix,User id_user) {

        this.nom = nom;
        this.prix = prix;
        this.id_user = id_user;
    }




    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "panier{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", id_user=" + id_user +
                '}';
    }
}
