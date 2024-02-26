package Gestion_planing.entities;


import gestion_user.entities.User;

public class Reservation {
    private int id_reservation;
    private User user;

    private planning planing;

    public Reservation(int id_reservation, User user , planning planing) {
        this.id_reservation = id_reservation;
        this.user = user;
        this.planing = planing;
    }

    public Reservation( User user ,  planning planing) {
        this.user = user;
        this.planing = planing;

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
                ", user=" + user +
                ", planing=" + planing +
                '}';
    }
}



