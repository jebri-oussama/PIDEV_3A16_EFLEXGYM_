package Gestion_planing.service;
import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import GestionFinance.service.BilanFinancierService;
import Gestion_planing.entities.TypeCours;
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

public  class PlanningService implements IntService<planning>{
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public PlanningService() {
        conn= DataSource.getInstance().getCnx();
    }

    @Override
    public void add(planning p) {
        if (p.getUser().getRole() != null && p.getUser().getRole().equals(Role.Coach)) { // Check if the user's role is 'coach'
            String requete = "INSERT INTO planning (id_planing, salle, nb_place_max, date, heure, id_cour, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                pst = conn.prepareStatement(requete);
                pst.setInt(1, p.getId());
                pst.setString(2, p.getSalle());
                pst.setInt(3, p.getNb_place_max());
                pst.setDate(4, p.getDate());
                pst.setTime(5, p.getHeure());
                pst.setInt(6, p.getCours().getId());
                pst.setInt(7, p.getUser().getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Only users with a role as 'coach' are allowed to be added to the planning.");
        }

}

    public void delete(planning p) {
        String requete = "DELETE FROM planning WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setInt(1 , p.getId());
            pst.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void update(planning p) {
        if (p.getUser().getRole() != null && p.getUser().getRole().equals(Role.Coach)) { // Check if the user's role is 'coach'
            String requete = "UPDATE planning SET salle = ?, nb_place_max = ?, date = ?, heure = ?, id_cour = ?, id_coach = ? WHERE id = ?";
            try {
                pst = conn.prepareStatement(requete);
                pst.setString(1, p.getSalle());
                pst.setInt(2, p.getNb_place_max());
                pst.setDate(3, p.getDate());
                pst.setTime(4, p.getHeure());
                pst.setInt(5, p.getCours().getId());
                pst.setInt(6, p.getUser().getId());
                pst.setInt(7, p.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }}}

    @Override
    public List<planning> readAll() {
        List<planning> planningList = new ArrayList<>();
        String query = "SELECT * FROM planning";
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);
            while (rs.next()) {
                int id_coach = rs.getInt(6);
                UserService coachService = new UserService();
                User coach = coachService.readById(id_coach);
                int id_cour = rs.getInt(5);
                CoursService coursService = new CoursService();
                cours cour = coursService.readById(id_cour);
                planningList.add(new planning(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDate(4),rs.getTime(5),cour,coach));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return planningList;
    }

    @Override
    public planning readById(int id) {
            String requete = "SELECT * FROM planning WHERE id_planing = ?";
            try{
                pst = conn.prepareStatement(requete);
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()){
                    int id_coach = rs.getInt(6);
                    UserService coachService = new UserService();
                    User coach = coachService.readById(id_coach);
                    int id_cour = rs.getInt(5);
                    CoursService coursService = new CoursService();
                    cours cour = coursService.readById(id_cour);
                    return new planning(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDate(4),rs.getTime(5),cour,coach);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
    }}
   /* public List<planning> readAll() {
        String query = "SELECT * FROM planning";
        List<planning> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String salle = rs.getString("salle");
                int nb_place_max = rs.getInt("nb_place_max");
                Date date = rs.getDate("date");
                Time heure = rs.getTime("heure");
                cours  cours = rs.getInt.("id_cour");
                int User= rs.getInt("id_user");
            }
    }
    public planning readById(int id) {

        return null;
    }
}*/

   /* public List<planning> readAll() {
        String query = "SELECT * FROM planning";
        List<planning> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String salle = rs.getString("salle");
                int nb_place_max = rs.getInt("nb_place_max");
                Date date = rs.getDate("date");
                Time heure = rs.getTime("heure");
                int  cours = rs.getInt("id_cour");
                int User= rs.getInt("id_user");

                planning planning = new planning(id, salle ,nb_place_max , date , heure , cours ,User );
                list.add(planning);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public planning readById(int id) {
        String requete = "SELECT * FROM planning WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String salle = rs.getString("salle");
                int nb_place_max = rs.getInt("nb_place_max");
                Date date = rs.getDate("date");
                Time heure = rs.getTime("heure");
                int id_cour = rs.getInt("id_cour");
                int id_coach = rs.getInt("id_coach");

                return new planning(id, salle ,nb_place_max , date , heure , id_cour , id_coach);
            } else {
                return null; // Course with the given ID not found//
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}*/

