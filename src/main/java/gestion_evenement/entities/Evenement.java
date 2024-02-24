package gestion_evenement.entities;

import java.sql.Timestamp;

public class Evenement {

    private int id;
    private Type type;
    private Timestamp date_debut;
    private Timestamp date_fin;
    private String imagePath;

    public Evenement(Type type, Timestamp date_debut, Timestamp date_fin, String imagePath) {
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Timestamp getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Timestamp date_debut) {
        this.date_debut = date_debut;
    }

    public Timestamp getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Timestamp date_fin) {
        this.date_fin = date_fin;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDuration() {
        long durationInMillis = date_fin.getTime() - date_debut.getTime();
        long days = durationInMillis / (1000 * 60 * 60 * 24);
        long hours = (durationInMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (durationInMillis % (1000 * 60 * 60)) / (1000 * 60);

        return String.format("%d days, %d hours, %d minutes", days, hours, minutes);
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", type=" + type +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
