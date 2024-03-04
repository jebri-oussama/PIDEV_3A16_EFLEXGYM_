package gestion_suivi.service;

import gestion_suivi.entitis.Exercice;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Exercice_Service implements IExerciceService<Exercice> {
    private Connection conn;
    private PreparedStatement pst;

    public Exercice_Service() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public boolean add(Exercice e) {
        String requete = "INSERT INTO exercice (nom, description, nbrDeSet, groupeMusculaire, nbrDeRepetitions, categorieExercice, typeEquipement) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, e.getNom());
            pst.setString(2, e.getDescription());
            pst.setInt(3, e.getNbrDeSet());
            pst.setString(4, e.getGroupeMusculaire());
            pst.setInt(5, e.getNbrDeRepetitions());
            pst.setString(6, e.getCategorieExercice());
            pst.setString(7, e.getTypeEquipement());
            int n = pst.executeUpdate();
            if (n==1) {
                System.out.println("The Exercice has been added");
                return true; // Return the generated club ID
            } else {
                System.out.println("Failed to retrieve club ID");
                return false; // Return -1 if club ID retrieval fails
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean delete(Exercice ex) {
        if (ex == null)
            return false;

        PreparedStatement pstmt = null;
        int n = 0;
        try {
            pstmt = conn.prepareStatement("delete from exercice where id =?");
            pstmt.setInt(1, ex.getId());
            n = pstmt.executeUpdate();
            pstmt.close();
            if (n == 1) {
                System.out.println("user removale was succeded");
                return true;
            } else {
                System.out.println("no user was deleted");
                return false;
            }

        } catch (Exception e) {
            System.out.println("wrong compilation of the reqeute");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(Exercice e) {
        String requete = "UPDATE exercice SET nom = ?, description = ?, nbrDeSet = ?, groupeMusculaire = ?, nbrDeRepetitions = ?, categorieExercice = ?, typeEquipement = ? WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, e.getNom());
            pst.setString(2, e.getDescription());
            pst.setInt(3, e.getNbrDeSet());
            pst.setString(4, e.getGroupeMusculaire());
            pst.setInt(5, e.getNbrDeRepetitions());
            pst.setString(6, e.getCategorieExercice());
            pst.setString(7, e.getTypeEquipement());
            pst.setInt(8, e.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override

    public List<Exercice> readAll() {
        String requete = "SELECT * FROM exercice";
        List<Exercice> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                list.add(new Exercice(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("nbrDeSet"),
                        rs.getString("groupeMusculaire"),
                        rs.getInt("nbrDeRepetitions"),
                        rs.getString("categorieExercice"),
                        rs.getString("typeEquipement")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Exercice readById(int id) {
        String requete = "SELECT * FROM exercice WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Exercice(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("nbrDeSet"),
                        rs.getString("groupeMusculaire"),
                        rs.getInt("nbrDeRepetitions"),
                        rs.getString("categorieExercice"),
                        rs.getString("typeEquipement")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

}
