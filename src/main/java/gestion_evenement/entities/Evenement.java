package gestion_evenement.entities;

import java.sql.Date;

public class Evenement {

    private int id;
    private Type type;
    private Date date_debut;
    private Date date_fin;
    private String imagePath;
    private String event_name;
    private String duration;
    private String place;

    public Evenement(Type type, String event_name, Date date_debut, Date date_fin,String duration, String imagePath,  String place) {
        this.type = type;
        this.event_name = event_name;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.duration = duration;
        this.imagePath = imagePath;
        this.place = place;
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

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", type=" + type +
                ", event_name='" + event_name + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", duration='" + duration + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
