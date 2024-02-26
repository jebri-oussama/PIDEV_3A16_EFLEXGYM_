package gestion_suivi.service;

import gestion_suivi.entitis.Exercice;
import gestion_suivi.entitis.Programme_personnalise;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;
public class Programme_personnalise_service {
    private Connection conn;
    private PreparedStatement pst;

    public Programme_personnalise_service() {
        conn = DataSource.getInstance().getCnx();
    }

    public void add(Programme_personnalise p) {
        String requete = "INSERT INTO programme_personnalise (id, objectif) VALUES (?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.setString(2, p.getObjectif());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addExercice(Programme_personnalise p, Exercice e) {
        String requete = "INSERT INTO programme_exercice (id_programme, id_exercice) VALUES (?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.setInt(2, e.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean delete(Programme_personnalise p) {
        String requete = "DELETE FROM programme_personnalise WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public void update(Programme_personnalise p) {
        String requete = "UPDATE programme_personnalise SET objectif = ? WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, p.getObjectif());
            pst.setInt(2, p.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Programme_personnalise> readAll() {
        String requete = "SELECT * FROM programme_personnalise";
        List<Programme_personnalise> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Programme_personnalise(
                        rs.getInt("id"),
                        rs.getString("objectif")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Programme_personnalise readById(int id) {
        String requete = "SELECT * FROM programme_personnalise WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Programme_personnalise(
                        rs.getInt("id"),
                        rs.getString("objectif")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public Programme_personnalise readById(String o) {
        String requete = "SELECT * FROM programme_personnalise WHERE objectif = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, o);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Programme_personnalise(
                        rs.getInt("id"),
                        rs.getString("objectif")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public List<Exercice> readIdExerciceByIdProgramm(int id) {
        List<Exercice> ex = new ArrayList<>();
        String requete = "SELECT * FROM programme_personnalise p " +
                "JOIN programme_exercice pe ON p.id=pe.id_programme " +
                "JOIN exercice e on pe.id_exercice=e.id where p.id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ex.add(new Exercice(rs.getInt(5),rs.getString(6),rs.getString(7),rs.getInt(8),
                        rs.getString(9),rs.getInt(10),rs.getString(11),rs.getString(12)));
            }
            return ex;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
