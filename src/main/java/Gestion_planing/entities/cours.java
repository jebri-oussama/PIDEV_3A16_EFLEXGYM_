package org.example.Gestion_planing.entities;


public class cours {
    private int id;
    private String nom;
    private TypeCours type; // Variable de type enum pour le type de cours
    private int duree;

    public cours(int id, String nom, TypeCours type, int duree) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.duree = duree;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public TypeCours getType() {
        return type;
    }

    public int getDuree() {
        return duree;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(TypeCours type) {
        this.type = type;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type=" + type +
                ", duree=" + duree +
                '}';
    }
}



