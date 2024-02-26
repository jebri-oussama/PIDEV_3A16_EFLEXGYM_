package Gestion_planing.service;

import Gestion_planing.entities.cours;
import Gestion_planing.entities.planning;
import gestion_user.entities.Role;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanningService implements IntService<planning> {
    private final Connection conn;
    private PreparedStatement pst;

    public PlanningService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(planning p) {
        // Vérifier si l'utilisateur associé au planning est null
        if (p.getUser() == null) {
            throw new IllegalArgumentException("User associated with the planning cannot be null");
        }

        // Maintenant, vous pouvez appeler la méthode getRole() en toute sécurité
        String role = String.valueOf(p.getUser().getRole());

        String query = "INSERT INTO planning (salle, nbplace, date, heur, cours_id, coach_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, p.getSalle());
            pst.setInt(2, p.getNb_place_max());
            pst.setDate(3, p.getDate());
            pst.setString(4, p.getHeure());
            pst.setInt(5, p.getCours().getId());
            pst.setInt(6, p.getUser().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(planning p) {
        String requete = "DELETE FROM planning WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(planning p) {
        if (p.getUser().getRole() != null && p.getUser().getRole().equals(Role.Coach)) {
            String requete = "UPDATE planning SET salle = ?, nb_place_max = ?, date = ?, heure = ?, id_cour = ?, id_coach = ? WHERE id = ?";
            try {
                pst = conn.prepareStatement(requete);
                pst.setString(1, p.getSalle());
                pst.setInt(2, p.getNb_place_max());
                pst.setDate(3, new java.sql.Date(p.getDate().getTime()));
                pst.setString(4, p.getHeure());
                pst.setInt(5, p.getCours().getId());
                pst.setInt(6, p.getUser().getId());
                pst.setInt(7, p.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<planning> readAll() {
        List<planning> planningList = new ArrayList<>();
        String query = "SELECT * FROM planning";
        try {
            Statement ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);
            while (rs.next()) {
                int id_coach = rs.getInt(6);
                UserService coachService = new UserService();
                User coach = coachService.readById(id_coach);
                int id_cour = rs.getInt(5);
                CoursService coursService = new CoursService();
                cours cour = coursService.readById(id_cour);
                planningList.add(new planning(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDate(4), rs.getString(5), cour, coach));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return planningList;
    }

    @Override
    public planning readById(int id) {
        String requete = "SELECT * FROM planning WHERE id_planing = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id_coach = rs.getInt(6);
                UserService coachService = new UserService();
                User coach = coachService.readById(id_coach);
                int id_cour = rs.getInt(5);
                CoursService coursService = new CoursService();
                cours cour = coursService.readById(id_cour);
                return new planning(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDate(4), rs.getString(5), cour, coach);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
