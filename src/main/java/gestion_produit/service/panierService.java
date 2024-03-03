package gestion_produit.service;

import gestion_produit.entities.panier;
import gestion_produit.entities.produit;
import gestion_user.entities.User;
import gestion_user.entities.UserSession;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class panierService implements IService<panier> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;


    public panierService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(panier c) {
        String requete = "INSERT INTO panier ( nom, prix,id_user) VALUES (?, ?,?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom());
            pst.setFloat(2, c.getPrix());
            pst.setInt(3, c.getId_user().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM panier WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int ID, panier c) {
        String requete = "UPDATE categorie SET type = ?, description = ?, id_user = ? WHERE id =?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom());
            pst.setFloat(2, c.getPrix());
            pst.setInt(3, ID);
            pst.setInt(4, c.getId_user().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<panier> readAll() {

        String requete = "select * from panier";
        List<panier> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                User loggedInUser = UserSession.getLoggedInUser();
                list.add(new panier(rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        loggedInUser
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return list;
    }

    @Override
    public panier readById(int id) {
        String query = "SELECT * FROM panier WHERE id = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User loggedInUser = UserSession.getLoggedInUser();
                produit p = new produitService().readById(rs.getInt("id_produit"));
                return new panier(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        loggedInUser
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public panier getParticipationByUserId(int userID) {
        return null;
    }

    @Override
    public panier getPanierByUserId(int userID) {
        String query = "SELECT * FROM panier WHERE id_user = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User loggedInUser = UserSession.getLoggedInUser();
                panier p = new panier(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getFloat("prix"),
                        loggedInUser
                );
                p.setId(rs.getInt("id"));
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}




