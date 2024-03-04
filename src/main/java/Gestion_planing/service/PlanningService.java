package Gestion_planing.service;

import Gestion_planing.entities.cours;
import Gestion_planing.entities.planning;
import gestion_user.entities.Role;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
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
       /* if (p.getId_coach() == null) {
            throw new IllegalArgumentException("User associated with the planning cannot be null");
        }*/

        // Maintenant, vous pouvez appeler la méthode getRole() en toute sécurité
        String role = String.valueOf(p.getId_coach().getRole());

        String query = "INSERT INTO planning (salle, nb_place_max, date, heure, id_cour, id_coach) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, p.getSalle());
            pst.setInt(2, p.getNb_place_max());
            LocalDate localDate = p.getDate();
            Date sqlDate = Date.valueOf(localDate);
            pst.setDate(3, sqlDate);
            pst.setString(4, p.getHeure());
            pst.setInt(5, p.getId_cour().getId());
            pst.setInt(6, p.getId_coach().getId());
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
        {
            //String requete="update planning set salle='"+p.getSalle()+"',nb_place_max='"+p.getNb_place_max()+"',date='"+p.getDate()+"',heure="+p.getHeure()+",id_cour='"+p.getId_cour()+"',id_coach='"+p.getId_coach()+"' where id='"+p.getId()+"'";

            String requete = "UPDATE planning SET salle = ?, nb_place_max = ?, date = ?, heure = ?, id_cour = ?, id_coach = ? WHERE id = ?";
            try {
                pst = conn.prepareStatement(requete);
                pst.setString(1, p.getSalle());
                pst.setInt(2, p.getNb_place_max());
                LocalDate localDate = p.getDate();
                Date sqlDate = Date.valueOf(localDate);
                pst.setDate(3, sqlDate);
                pst.setString(4, p.getHeure());
                pst.setInt(5, p.getId_cour().getId());
                pst.setInt(6, p.getId_coach().getId());
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
                int id_cour = rs.getInt(6);
                int id_coach = rs.getInt(7);
                CoursService coursService = new CoursService();
                UserService coachService = new UserService();
                User coach = coachService.readById(id_coach);
                cours cour = coursService.readById(id_cour);
                LocalDate date = rs.getDate(4).toLocalDate();
                String heur = rs.getString(5);
                planningList.add(new planning(rs.getInt(1), rs.getString(2), rs.getInt(3),date, heur, cour, coach));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return planningList;
    }

    @Override
    public planning readById(int id) {
        String requete = "SELECT * FROM planning WHERE id = ?";
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
                LocalDate date = rs.getDate(4).toLocalDate();
                return new planning(rs.getInt(1), rs.getString(2), rs.getInt(3), date, rs.getString(5), cour, coach);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<planning> getPlanningByCoach(int coach_id) {
        List<planning> pl = new ArrayList<>();
        String query = "SELECT * FROM planning WHERE id_coach = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, coach_id);
            ResultSet result = statement.executeQuery();
            UserService us = new UserService();
            CoursService cs = new CoursService();
            while (result.next()) {
                planning p = new planning();
                p.setId(result.getInt("id"));
                p.setSalle(result.getString("salle"));
                p.setNb_place_max(result.getInt("nb_place_max"));
                p.setDate(result.getDate("date").toLocalDate());
                p.setId_coach(us.readById(result.getInt("id_coach")));
                p.setHeure(result.getString("heure"));
                p.setId_cour(cs.readById(result.getInt("id_cour")));

                pl.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pl;
    }
}
