package gestion_user.service;


import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import gestion_user.entities.Role;
import gestion_user.entities.Sexe;
import gestion_user.entities.User;
import utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UserService implements IService<User> {

    private static Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    private BilanFinancierService bilanFinancierService;

    public UserService() {
        conn = DataSource.getInstance().getCnx();
        bilanFinancierService = new BilanFinancierService();
    }

    @Override
    public void addAdherent(User c) {
        String requete = "INSERT INTO user (nom, prenom, mot_de_passe, email, date_de_naissance, sexe, role,salaire, id_bilan_financier) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
        try {

            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
            pst.setString(3, c.getMot_de_passe());
            pst.setString(4, c.getEmail());
            pst.setDate(5, c.getDate_de_naissance());
            pst.setString(6, c.getSexe().toString());
            pst.setString(7, c.getRole().toString());
            pst.setDouble(8, c.getSalaire());
            int idBilanFinancier = bilanFinancierService.getIdBilanFinancier();
            pst.setInt(9, idBilanFinancier);

            pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void updateAdherent(int id, User c) {
        String requete = "UPDATE user SET nom = ?, prenom = ?, mot_de_passe = ?, email = ?, date_de_naissance = ?, sexe = ?, role= ?, salaire = ?,id_bilan_financier = ?  WHERE id = ?";

        try (PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
            pst.setString(3, c.getMot_de_passe());
            pst.setString(4, c.getEmail());
            pst.setDate(5, c.getDate_de_naissance());
            pst.setString(6, c.getSexe().toString());
            pst.setString(7, c.getRole().toString());
            pst.setDouble(8, c.getSalaire());

            pst.setInt(9, c.getBilanFinancier());
            pst.setInt(10, id);
            int test = pst.executeUpdate();
            if (test == 0) {
                System.out.println("Colone n'est pas mettre à jour " + id);
            } else {
                System.out.println("Colone trouvé " + id + " est updaté.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> readAllAdherent() {
        String requete = "SELECT * FROM user";
        List<User> list = new ArrayList<>();

        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {
                Sexe sexe = Sexe.valueOf(rs.getString(7));
                Role role = Role.valueOf(rs.getString(8));

                User ad = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), sexe, role);
                ad.setId(rs.getInt(1));
                ad.setSalaire(rs.getDouble(9));

                int idBilanFinancier = rs.getInt("id_bilan_financier");
                ad.setBilanFinancier(idBilanFinancier);

                list.add(ad);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public void deleteAdherent(int id) {
        String requete = "DELETE FROM user WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            int test = pst.executeUpdate();
            if (test == 0) {
                System.out.println("N'y a aucun Adherent avec ce id: " + id);
            } else {
                System.out.println("Adherent trouvé " + id + " a été supprimé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



   /* @Override
    public Adherent readAdherentById(int id) {
        String requete = "SELECT * FROM user WHERE id = ? and role='Adherent'";
        User user = null;
        try (PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Sexe sexe = Sexe.valueOf(rs.getString(7));
                    Role role = Role.valueOf(rs.getString(8));
                    user = new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDate(6),
                            sexe,
                            role

                    );
                    adherent.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return user;
    }*/

    public boolean connexion(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Connexion échoué", e);
        }
    }

    public String getUserRole(String email) {
        String role = null;
        String query = "SELECT role FROM user WHERE email = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    role = rs.getString("role");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Connexion échoué", e);
        }
        return role;
    }


    public boolean isEmailExists(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // Returns true if the email is already taken
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Sexe sexe = Sexe.valueOf(rs.getString(7));
                Role role = Role.valueOf(rs.getString(8));

                User user = new User(rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getDate(6), sexe, role);
                user.setId(rs.getInt(1));
                user.setSalaire(rs.getDouble(9));
                user.setBilanFinancier(rs.getInt(10));

                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if no user is found with the given email
    }

    public void storeResetToken(String userEmail, String resetToken, LocalDateTime expirationTime) {
        String query = "INSERT INTO tokens (user_email, reset_token, expiration_time) VALUES (?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, userEmail);
            pst.setString(2, resetToken);
            pst.setTimestamp(3, Timestamp.valueOf(expirationTime));

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error storing reset token in the database", e);
        }
    }


}



