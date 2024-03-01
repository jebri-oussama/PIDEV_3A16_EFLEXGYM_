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
        String query = "INSERT INTO Evenement (type,event_name , date_debut, date_fin,duration, image,place) VALUES (?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, e.getType().getId());
            pst.setString(2, e.getEvent_name());
            pst.setDate(3, e.getDate_debut());
            pst.setDate(4, e.getDate_fin());
            pst.setString(5, e.getDuration());
            pst.setString(6, e.getImagePath());

            pst.setString(7, e.getPlace());
            pst.executeUpdate();


            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
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
        String query = "UPDATE Evenement SET type = ?,event_name = ?, date_debut = ?, date_fin = ?, duration = ?, image = ?,place = ? WHERE id = ?";
        try {
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, evenement.getType().getId());
            pst.setString(2,evenement.getEvent_name());
            pst.setDate(3, evenement.getDate_debut());
            pst.setDate(4, evenement.getDate_fin());
            pst.setString(5, evenement.getDuration());
            pst.setString(6, evenement.getImagePath());
            pst.setString(7, evenement.getPlace());
            pst.setInt(8, id);
            pst.executeUpdate();
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
                        rs.getString("event_name"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("duration"),
                        rs.getString("image"),
                        rs.getString("place")
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
                        rs.getString("event_name"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("duration"),
                        rs.getString("image"),
                        rs.getString("place")
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
