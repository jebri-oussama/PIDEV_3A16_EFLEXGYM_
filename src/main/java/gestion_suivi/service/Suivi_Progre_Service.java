package gestion_suivi.service;

import gestion_suivi.entitis.Suivi_Progre;
import gestion_user.entities.User;
import gestion_user.entities.Role;
import gestion_user.entities.Sexe;
import gestion_user.service.UserService;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Suivi_Progre_Service implements ISuiviService<Suivi_Progre> {
    private Connection conn;
    private PreparedStatement pst;

    public Suivi_Progre_Service() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public int add(Suivi_Progre s) {
        if (s == null)
            return -1; // Return -1 to indicate failure

        PreparedStatement pst = null;
        ResultSet generatedKeys = null;

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO suivi_progre ( nom, prenom, age, taille, poids, tour_de_taille, sexe, idUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, s.getNom());
            pst.setString(2, s.getPrenom());
            pst.setInt(3, s.getAge());
            pst.setDouble(4, s.getTaille());
            pst.setDouble(5, s.getPoids());
            pst.setDouble(6, s.getTour_de_taille());
            pst.setString(7, s.getSexe().toString());
            pst.setInt(8, s.getIdUser().getId());

            int n = pst.executeUpdate();

            if (n == 1) {
                // Retrieve the auto-generated keys
                generatedKeys = pst.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int suiviId = generatedKeys.getInt(1); // Retrieve the auto-generated club ID
                    System.out.println("The Club has been added with ID: " + suiviId);
                    return suiviId; // Return the generated club ID
                } else {
                    System.out.println("Failed to retrieve club ID");
                    return -1; // Return -1 if club ID retrieval fails
                }
            } else {
                System.out.println("No club has been added");
                return -1; // Return -1 to indicate failure
            }

        } catch (SQLException e1) {
            System.out.println("The  club addition failed: " + e1.getMessage());
            return -1; // Return -1 to indicate failure
        } finally {
            // Close resources
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean delete(Suivi_Progre s) {
        String requete = "DELETE FROM suivi_progre WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, s.getId());
            pst.executeUpdate();
            return true ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Suivi_Progre s) {
        String requete = "UPDATE suivi_progre SET nom = ?, prenom = ?, age = ?, taille = ?, poids = ?, tour_de_taille = ?, sexe = ? WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, s.getNom());
            pst.setString(2, s.getPrenom());
            pst.setInt(3, s.getAge());
            pst.setDouble(4, s.getTaille());
            pst.setDouble(5, s.getPoids());
            pst.setDouble(6, s.getTour_de_taille());
            pst.setString(7, s.getSexe().toString());
            pst.setInt(8, s.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Suivi_Progre> readAll() {
        String requete = "SELECT * FROM suivi_progre";
        List<Suivi_Progre> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String s = rs.getString("Sexe");

                int idAdherent = rs.getInt("idUser");
                UserService userService = new UserService();
                User adherent = userService.readAdherentById(idAdherent);
                list.add(new Suivi_Progre(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        s,
                        adherent
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Suivi_Progre readById(int id) {
        String requete = "SELECT * FROM suivi_progre WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String s = rs.getString(8);
                int idAdherent = rs.getInt("idUser");
                UserService userService = new UserService();
                User adherent = userService.readAdherentById(idAdherent);
                return new Suivi_Progre(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        s,
                        adherent
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
