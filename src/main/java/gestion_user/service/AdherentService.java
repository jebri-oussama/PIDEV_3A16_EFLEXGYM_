package gestion_user.service;

import gestion_user.entities.Adherent;
import gestion_user.entities.Role;
import gestion_user.entities.Sexe;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdherentService implements IService<Adherent> {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public AdherentService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Adherent c) {
        String requete = "INSERT INTO user (nom, prenom, mot_de_passe, email, date_de_naissance, sexe, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
            pst.setString(3, c.getMot_de_passe());
            pst.setString(4, c.getEmail());
            pst.setDate(5, c.getDate_de_naissance());
            pst.setString(6, c.getSexe().toString());
            pst.setString(7, Role.Adherent.toString());

            pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void update(int id,String role, Adherent c) {
        String requete = "UPDATE user SET nom = ?, prenom = ?, mot_de_passe = ?, email = ?, date_de_naissance = ?, sexe = ?  WHERE id = ? AND role= ?";

        try (PreparedStatement pst = conn.prepareStatement(requete)) {

            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
            pst.setString(3, c.getMot_de_passe());
            pst.setString(4, c.getEmail());
            pst.setDate(5, c.getDate_de_naissance());
            pst.setString(6, c.getSexe().toString());
            pst.setInt(7,id);
            pst.setString(8,role);
            int test= pst.executeUpdate();
            if (test == 0) {
                System.out.println("Colone n'est pas mettre à jour " + id);
            } else {
                System.out.println("Adherent trouvé " + id + " est updaté.");
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public  List<Adherent> readAll() {
        String requete = "select * from user where role='Adherent'";
        List<Adherent> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Sexe sexe = Sexe.valueOf(rs.getString(7));
                Role role = Role.valueOf(rs.getString(8));

                Adherent ad = new Adherent( rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), sexe,role);
                    ad.setId(rs.getInt(1));
                    list.add(ad);
                                }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } return list;
    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM user WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setInt(1 , id);
            int test= pst.executeUpdate();
            if (test == 0) {
                System.out.println("N'y a aucun Adherent avec ce id: " + id);
            } else {
                System.out.println("Adhrent trouvé " + id + " a été supprimé.");
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }    }



    @Override
    public Adherent readById(int id) {
        String requete = "SELECT * FROM user WHERE id = ?";
        Adherent adherent = null;
        try (PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Sexe sexe = Sexe.valueOf(rs.getString(7));
                    Role role = Role.valueOf(rs.getString(8));
                    adherent = new Adherent(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDate(6),
                            sexe,
                            role

                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return adherent;
    }

}