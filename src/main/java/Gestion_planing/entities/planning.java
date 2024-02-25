package Gestion_planing.entities;

import gestion_user.entities.User;

import java.util.Date;

public class planning {
    private int id;
    private String salle;
    private int nb_place_max;
    private Date date;
    private String heure;
    private cours cours;
    private User user;

    public planning() {
    }

    public planning(int id, String salle, int nb_place_max, Date date, String heure, cours cours, User user) {
        this.id = id;
        this.salle = salle;
        this.nb_place_max = nb_place_max;
        this.date = date;
        this.heure = heure;
        this.cours = cours;
        this.user = user;
    }

    public planning(String salle, int nb_place_max, Date date, String heure, Gestion_planing.entities.cours cours, User user) {
        this.salle = salle;
        this.nb_place_max = nb_place_max;
        this.date = date;
        this.heure = heure;
        this.cours = cours;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getSalle() {
        return salle;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public int getNb_place_max() {
        return nb_place_max;
    }

    public void setNb_place_max(int nb_place_max) {
        this.nb_place_max = nb_place_max;
    }

    public cours getCours() {
        return cours;
    }

    public void setCours(cours cours) {
        this.cours = cours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    @Override
    public String toString() {
        return "planning{" +
                "id=" + id +
                ", salle='" + salle + '\'' +
                ", nb_place_max=" + nb_place_max +
                ", date=" + date +
                ", heure=" + heure +
                ", cours=" + cours +
                ", user=" + user +
                '}';
    }
}
