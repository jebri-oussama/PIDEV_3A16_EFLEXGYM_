package gestion_produit.service;



import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import gestion_produit.entities.categorie;
import gestion_produit.entities.produit;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class produitService implements gestion_produit.service.IService<produit> {


    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public produitService() {
        conn = DataSource.getInstance().getCnx();
    }

    public void add(produit p) {
        String requete = "INSERT INTO produit (id, nom, image, prix, quantite, description, categorie, id_admin, id_bilan_financier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.setString(2, p.getNom().toString());
            pst.setString(3, p.getImage().toString());
            pst.setFloat(4, p.getPrix());
            pst.setInt(5, p.getQuantite());
            pst.setString(6, p.getDescription().toString());
            pst.setInt(7,p.getCategorie().getId());
            pst.setInt(9, p.getId_bilan_financier().getId());
            pst.setInt(8, p.getId_admin());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    public void delete(int id) {
        String requete = "DELETE FROM produit WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setInt(1 , id);
            int test= pst.executeUpdate();
            if (test == 0) {
                System.out.println("N'y a aucun produit avec ce id: " + id);
            } else {
                System.out.println("produit trouvé " + id + " a été supprimé.");
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int ID,produit produit) {
        String requete = "UPDATE produit SET nom = ?, image = ?, prix= ?, quantite = ?, description= ?, categorie= ?,id_admin = ?, id_bilan_financier = ? WHERE id =?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, produit.getNom().toString());
            pst.setString(2, produit.getImage());
            pst.setFloat(3, produit.getPrix());
            pst.setInt(4, produit.getQuantite());
            pst.setString(5, produit.getDescription().toString());
            pst.setInt(6,produit.getCategorie().getId());
            pst.setInt(8, produit.getId_bilan_financier().getId());
            pst.setInt(7, produit.getId_admin());

            pst.setInt(9, ID);



            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<produit> readAll() {
        String requete = "SELECT * FROM produit";
        List<produit> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                int categoryId = rs.getInt("categorie");
                categorieService cs = new categorieService();
                categorie c = cs.readById(categoryId);
                int bilanfinancierid = rs.getInt("id_bilan_financier");
                BilanFinancierService bc = new BilanFinancierService();
                BilanFinancier B = bc.readById(bilanfinancierid);
                produit produit = new produit(
                        rs.getString(2),   // nom
                        rs.getString(3),   // image
                        rs.getFloat(4),    // prix
                        rs.getInt(5),      // quantite
                        rs.getString(6),   // description
                        c,                 // categorie
                        B,      // id_bilan_financier
                        rs.getInt(9)       // id_admin
                );
                produit.setId(rs.getInt("id")); // Set the produit ID
                list.add(produit); // Add produit to the list
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }



    @Override
    public produit readById(int id) {
        String requete = "SELECT * FROM produit WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new produit( rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getString(6), (categorie) rs.getObject(7),(BilanFinancier) rs.getObject(8), rs.getInt(9));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}