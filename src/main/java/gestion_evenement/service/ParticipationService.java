package gestion_evenement.service;

import gestion_evenement.entities.Evenement;
import gestion_evenement.entities.Participation;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipationService implements IServiceP<Participation> {

    private Connection conn;
    private PreparedStatement pst;

    public ParticipationService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Participation p) {
        String query = "INSERT INTO participation (id_evenement, nbr_de_participant) VALUES (?, ?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, p.getId_evenement().getId());
            pst.setInt(2, p.getNbr_de_participant());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error adding participation: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM participation WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Participation participation) {
        String query = "UPDATE participation SET id_evenement = ?, nbr_de_participant = ? WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, participation.getId_evenement().getId());
            pst.setInt(2, participation.getNbr_de_participant());
            pst.setInt(3, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Participation> readAll() {
        String query = "SELECT * FROM participation";
        List<Participation> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Evenement evenement = new EvenementService().readById(rs.getInt("id_evenement"));
                Participation participation = new Participation(
                        evenement,
                        rs.getInt("nbr_de_participant")
                );
                participation.setId(rs.getInt("id"));
                list.add(participation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Participation readById(int id) {
        String query = "SELECT * FROM participation WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Evenement evenement = new EvenementService().readById(rs.getInt("id_evenement"));
                return new Participation(
                        evenement,
                        rs.getInt("nbr_de_participant")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public int getNumberOfParticipants(int eventId) {
        String query = "SELECT nbr_de_participant FROM participation WHERE id_evenement = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, eventId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("nbr_de_participant");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public Participation getParticipationByEvenementId(int evenementId) {
        String query = "SELECT * FROM participation WHERE id_evenement = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, evenementId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                EvenementService evenementService = new EvenementService();
                Evenement evenement = evenementService.readById(rs.getInt("id_evenement"));
                Participation participation = new Participation(
                        evenement,
                        rs.getInt("nbr_de_participant")
                );
                participation.setId(rs.getInt("id"));
                return participation;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addParticipation(int evenementId) {
        String queryCheck = "SELECT * FROM participation WHERE id_evenement = ?";
        String queryInsert = "INSERT INTO participation (id_evenement, nbr_de_participant) VALUES (?, 1)";
        String queryUpdate = "UPDATE participation SET nbr_de_participant = nbr_de_participant + 1 WHERE id_evenement = ?";

        try {
            // Check if participation entry exists for the given evenementId
            pst = conn.prepareStatement(queryCheck);
            pst.setInt(1, evenementId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // If entry exists, update the nbr_de_participant
                pst = conn.prepareStatement(queryUpdate);
                pst.setInt(1, evenementId);
                pst.executeUpdate();
            } else {
                // If entry doesn't exist, insert a new entry
                pst = conn.prepareStatement(queryInsert);
                pst.setInt(1, evenementId);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}





