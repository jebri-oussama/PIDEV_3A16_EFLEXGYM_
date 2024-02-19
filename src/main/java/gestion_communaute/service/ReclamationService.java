package gestion_communaute.service;


import gestion_communaute.entities.Reclamation;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//
public class ReclamationService implements IService<Reclamation> {

    private Connection conn;
    private PreparedStatement pst;

    public ReclamationService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Reclamation r) {
        String query = "INSERT INTO Reclamation (type, description, status, id_adherent) VALUES (?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, r.getType());
            pst.setString(2, r.getDescription());
            pst.setString(3, r.getStatus().name());
            pst.setInt(4, r.getId_adherent());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Reclamation WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Reclamation reclamation) {
        // First, check if the ID exists in the database
        if (readById(reclamation.getId()) != null) {
            // Proceed with the update operation
            String query = "UPDATE Reclamation SET type = ?, description = ?, status = ?, id_adherent = ? WHERE id = ?";
            try {
                pst = conn.prepareStatement(query);
                pst.setString(1, reclamation.getType());
                pst.setString(2, reclamation.getDescription());
                pst.setString(3, reclamation.getStatus().name());
                pst.setInt(4, reclamation.getId_adherent());
                pst.setInt(5, reclamation.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Handle the case where the ID does not exist in the database
            System.out.println("Error: The reclamation with ID " + reclamation.getId() + " does not exist in the database.");
        }
    }


    @Override
    public List<Reclamation> readAll() {
        String query = "SELECT * FROM Reclamation";
        List<Reclamation> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Reclamation(
                        rs.getString("type"),
                        rs.getString("description"),
                        Reclamation.Status.valueOf(rs.getString("status")),
                        rs.getInt("id_adherent")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    @Override
    public Reclamation readById(int id) {
        String query = "SELECT * FROM Reclamation WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Reclamation(
                        rs.getString("type"),
                        rs.getString("description"),
                        Reclamation.Status.valueOf(rs.getString("status")),
                        rs.getInt("id_adherent")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<Reclamation> getReclamationsByStatusAndType(Reclamation.Status status, String type) {
        String query = "SELECT * FROM Reclamation WHERE status = ? AND type = ?";
        List<Reclamation> reclamations = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, status.name());
            pst.setString(2, type);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reclamation reclamation = new Reclamation(
                        rs.getString("type"),
                        rs.getString("description"),
                        Reclamation.Status.valueOf(rs.getString("status")),
                        rs.getInt("id_adherent")
                );
                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reclamations;
    }


}
