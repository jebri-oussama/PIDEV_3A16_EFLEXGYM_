package org.example.Gestion_planing.entities;

import org.example.Gestion_user.entities.User;

public class Reservation {
    private int id_reservation;
    private planning planing;
    private User user;

    public Reservation(int id_reservation, planning planing, User user) {
        this.id_reservation = id_reservation;
        this.planing = planing;
        this.user = user;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public planning getPlaning() {
        return planing;
    }

    public void setPlaning(planning planing) {
        this.planing = planing;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", planing=" + planing +
                ", user=" + user +
                '}';
    }
}



