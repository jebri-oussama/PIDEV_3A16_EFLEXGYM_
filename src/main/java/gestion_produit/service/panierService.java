package gestion_produit.service;

import gestion_produit.entities.categorie;
import gestion_produit.entities.panier;
import gestion_produit.entities.type;
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
        String requete = "INSERT INTO panier (id, nom, prix) VALUES (?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.setString(2, c.getNom());
            pst.setFloat(3, c.getPrix());
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
        String requete = "UPDATE categorie SET type = ?, description = ?  WHERE id =?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom());
            pst.setFloat(2, c.getPrix());
            pst.setInt(3, ID);
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
                list.add(new panier(rs.getInt(1), rs.getString(2), rs.getFloat(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return list;
    }

    @Override
    public panier readById(int id) {
        return null;
    }
}




