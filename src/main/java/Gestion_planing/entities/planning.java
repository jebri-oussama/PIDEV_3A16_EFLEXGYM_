package Gestion_planing.entities;

import gestion_user.entities.User;

import java.time.LocalDate;
import java.util.Date;

public class planning {
    private int id;
    private String salle;
    private int nb_place_max;
    private LocalDate date;
    private String heure;
    private cours id_cour;
    private User id_coach;

    public planning() {
    }

    public planning(int id, String salle, int nb_place_max, LocalDate date, String heure, cours id_cour, User id_coach) {
        this.id = id;
        this.salle = salle;
        this.nb_place_max = nb_place_max;
        this.date = date;
        this.heure = heure;
        this.id_cour = id_cour;
        this.id_coach = id_coach;
    }

    public planning(String salle, int nb_place_max, LocalDate date, String heure, cours id_cour, User id_coach) {
        this.salle = salle;
        this.nb_place_max = nb_place_max;
        this.date = date;
        this.heure = heure;
        this.id_cour = id_cour;
        this.id_coach = id_coach;
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

    public cours getId_cour() {
        return id_cour;
    }

    public void setId_cour(cours id_cour) {
        this.id_cour = id_cour;
    }

    public User getId_coach() {
        return id_coach;
    }

    public void setId_coach(User id_coach) {
        this.id_coach = id_coach;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
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
                ", heure='" + heure + '\'' +
                ", cours=" + id_cour +
                ", user=" + id_coach +
                '}';
    }
}
