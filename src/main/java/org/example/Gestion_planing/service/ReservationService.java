package org.example.Gestion_planing.service;

import org.example.Gestion_planing.entities.*;
import org.example.Gestion_user.entities.Role;
import org.example.Gestion_user.entities.User;
import org.example.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ReservationService implements IntService<Reservation> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public ReservationService() {conn= DataSource.getInstance().getCnx();
    }
    public void add(Reservation r ) {
        if (r.getUser().getRole() != null && r.getUser().getRole().equals(Role.Adherent)){
        String query = "INSERT INTO reservation (id_reservation,id_planing, id_user ) VALUES (?, ?,?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, r.getId_reservation());
            pst.setInt(2, r.getPlaning().getId());
            pst.setInt(2, r.getUser().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while adding reservation: " + e.getMessage());
        }
    }
}}
