package Gestion_planing.entities;


import gestion_user.entities.User;

public class Reservation {
    private int id_reservation;
    private User id_user	;

    private planning id_planing;
    private String num_tell;

    public Reservation(int id_reservation, User id_user, planning id_planing, String num_tell) {
        this.id_reservation = id_reservation;
        this.id_user = id_user;
        this.id_planing = id_planing;
        this.num_tell = num_tell;
    }


    public Reservation(User id_user, planning id_planing, String num_tell) {
        this.id_user = id_user;
        this.id_planing = id_planing;
        this.num_tell = num_tell;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public planning getId_planing() {
        return id_planing;
    }

    public void setId_planing(planning id_planing) {
        this.id_planing = id_planing;
    }

    public String getNum_tell() {
        return num_tell;
    }

    public void setNum_tell(String num_tell) {
        this.num_tell = num_tell;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", id_user=" + id_user +
                ", id_planing=" + id_planing +
                ", num_tell='" + num_tell + '\'' +
                '}';
    }
}



