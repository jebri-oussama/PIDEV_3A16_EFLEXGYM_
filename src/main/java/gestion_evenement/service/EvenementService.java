package gestion_evenement.service;

import gestion_evenement.entities.Evenement;
import gestion_evenement.entities.Type;
import utils.DataSource;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements IServiceE<Evenement> {

    private Connection conn;
    private PreparedStatement pst;

    public EvenementService() {
        conn = DataSource.getInstance().getCnx();
    }


    @Override
    public void add(Evenement e) {
        String query = "INSERT INTO Evenement (type, date_debut, date_fin, duree) VALUES (?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, e.getType().getId());
            pst.setTimestamp(2, new java.sql.Timestamp(e.getDate_debut().getTime()));
            pst.setTimestamp(3, new java.sql.Timestamp(e.getDate_fin().getTime()));
            pst.setString(4, e.getDuree());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                System.out.println("Generated ID: " + generatedId);
            }
        } catch (SQLException ex) {
            System.err.println("Error adding event: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }





    @Override
    public void delete(int id) {
        String query = "DELETE FROM Evenement WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Evenement evenement) {
        String query = "UPDATE Evenement SET type = ?, date_debut = ?, date_fin = ?, duree = ? WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, evenement.getType().getId());
            pst.setTimestamp(2, new java.sql.Timestamp(evenement.getDate_debut().getTime()));
            pst.setTimestamp(3, new java.sql.Timestamp(evenement.getDate_fin().getTime()));
            pst.setString(4, evenement.getDuree());
            pst.setInt(5, id);
            pst.executeUpdate();System.out.println("Type ID: " + id); // Add this line for debugging;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<Evenement> readAll() {
        String query = "SELECT * FROM Evenement";
        List<Evenement> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int typeId = rs.getInt("type");
                Type type = readTypeById(typeId);
                Evenement evenement = new Evenement(
                        type,
                        rs.getTimestamp("date_debut"),
                        rs.getTimestamp("date_fin"),
                        rs.getString("duree")
                );
                evenement.setId(rs.getInt("id"));
                list.add(evenement);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Evenement readById(int id) {
        String query = "SELECT * FROM Evenement WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int typeId = rs.getInt("type");
                Type type = readTypeById(typeId);
                Evenement evenement = new Evenement(
                        type,
                        rs.getTimestamp("date_debut"),
                        rs.getTimestamp("date_fin"),
                        rs.getString("duree")
                );
                evenement.setId(rs.getInt("id")); // Set the ID here
                return evenement;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }





    private Type readTypeById(int id) {
        String query = "SELECT * FROM Type WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Type(rs.getString("typeName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
