package Gestion_planing.entities;


public class cours {
    private int id;
    private String nom;
    private TypeCours type; // Variable de type enum pour le type de cours
    private String duree;

    public cours(int id, String nom, TypeCours type, String duree) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.duree = duree;
    }

    public cours(String nom, TypeCours type, String duree) {
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

    public String getDuree() {
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

    public void setDuree(String duree) {
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



