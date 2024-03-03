package Gestion_planing.service;

import Gestion_planing.entities.Reservation;
import Gestion_planing.entities.planning;
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
        if (r.getId_user() == null || r.getId_planing()==null) {
            throw new IllegalArgumentException("User and planing can not be null");}
        else {
            String query = "INSERT INTO reservation (id_user, id_planing, num_tell) VALUES (?, ?, ?)";
            try {
                pst = conn.prepareStatement(query);
                pst.setInt(1, r.getId_user().getId());
                pst.setInt(2, r.getId_planing().getId());
                pst.setString(3, r.getNum_tell());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error occurred while adding reservation: " + e.getMessage());
            }
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
        String requete = "UPDATE reservation SET  id_user = ?,id_planing = ?,num_tell = ? WHERE id_reservation = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(2, r.getId_planing().getId());
            pst.setInt(1, r.getId_user().getId());
            pst.setString(3, r.getNum_tell());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> readAll() {
        List<Reservation> ReservationList = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        try {
            Statement ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);
            while (rs.next()) {
                int id_adherent = rs.getInt(2);
                int id_planing = rs.getInt(3);
                PlanningService planningService = new PlanningService();
                UserService adherentService = new UserService();
                User adherent = adherentService.readById(id_planing);
                planning plan = planningService.readById(id_adherent);
                ReservationList.add(new Reservation(rs.getInt(1),adherent,plan,rs.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ReservationList;
    }

    @Override
    public Reservation readById(int id_reservation) {
        String requete = "SELECT * FROM reservation WHERE id_reservation = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id_reservation);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id_adherent = rs.getInt(2);
                int id_planing = rs.getInt(3);
                PlanningService planningService = new PlanningService();
                UserService adherentService = new UserService();
                User adherent = adherentService.readById(id_planing);
                planning plan = planningService.readById(id_adherent);
                return new Reservation(rs.getInt(1),adherent , plan,rs.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
