package org.example.Gestion_planing.entities;

import org.example.Gestion_user.entities.User;

import java.sql.Time;
import java.util.Date;

public class planning {
    private int id;
    private String salle;
    private int nb_place_max;
    private Date date;
    private Time heure;
    private cours cours;
    private User user;

    public planning() {
    }

    public planning(int id, String salle, int nb_place_max, Date date, Time heure, org.example.Gestion_planing.entities.cours cours, User user) {
        this.id = id;
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

    public java.sql.Date getDate() {
        return (java.sql.Date) date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public int getNb_place_max() {
        return nb_place_max;
    }

    public void setNb_place_max(int nb_place_max) {
        this.nb_place_max = nb_place_max;
    }

    public org.example.Gestion_planing.entities.cours getCours() {
        return cours;
    }

    public void setCours(org.example.Gestion_planing.entities.cours cours) {
        this.cours = cours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
