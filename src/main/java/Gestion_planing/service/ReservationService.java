package Gestion_planing.service;

import Gestion_planing.entities.Reservation;
import Gestion_planing.entities.planning;
import gestion_user.entities.Role;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IntService<Reservation> {
    private Connection conn;
    private PreparedStatement pst;

    public ReservationService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Reservation r) {
        if (r.getUser().getRole() != null && r.getUser().getRole().equals(Role.Adherent)) {
            String query = "INSERT INTO reservation (id_reservation, id_planing, id_user) VALUES (?, ?, ?)";
            try {
                pst = conn.prepareStatement(query);
                pst.setInt(1, r.getId_reservation());
                pst.setInt(2, r.getPlaning().getId());
                pst.setInt(3, r.getUser().getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error occurred while adding reservation: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("User associated with the reservation must be an Adherent.");
        }
    }

    @Override
    public void delete(Reservation r) {
        String requete = "DELETE FROM reservation WHERE id_reservation = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, r.getId_reservation());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Reservation r) {

    }

    @Override
    public List<Reservation> readAll() {
        List<Reservation> reservationList = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        try {
            Statement ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);
            while (rs.next()) {
                int id_user = rs.getInt(2);
                UserService AderantService = new UserService();
                User aderant = AderantService.readById(id_user);
                int id_planing = rs.getInt(3);
                PlanningService planningService = new PlanningService();
                planning planing = planningService.readById(id_planing);
                reservationList.add(new Reservation(rs.getInt(1), aderant, planing));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }

    @Override
    public Reservation readById(int id) {
        String requete = "SELECT * FROM planning WHERE id_planing = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id_user = rs.getInt(2);
                UserService AderantService = new UserService();
                User aderant = AderantService.readById(id_user);
                int id_planing = rs.getInt(3);
                PlanningService planningService = new PlanningService();
                planning planing = planningService.readById(id_planing);
                return new Reservation(rs.getInt(1),aderant, planing);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
