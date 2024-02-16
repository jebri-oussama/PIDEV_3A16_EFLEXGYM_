package service;
import entities.produit;
import entities.type;
import utils.DataSource;
import entities.categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class categorieService implements IService<categorie>{


    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;


    public categorieService() {
        conn = DataSource.getInstance().getCnx();
    }


    @Override
    public void add(categorie c) {
        String requete = "INSERT INTO categorie (id, type, description) VALUES (?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.setString(2, c.getType().toString());
            pst.setString(3, c.getDescription().toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM categorie WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setInt(1 , id);
            pst.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(int ID, categorie c) {
        String requete = "UPDATE categorie SET type = ?, description = ?  WHERE id =?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getType().toString());
            pst.setString(2, c.getDescription());
            pst.setInt(3, ID);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<categorie> readAll() {
        String requete = "select * from categorie";
        List<categorie> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                type Type = type.valueOf(rs.getString(2));
                list.add(new categorie(rs.getInt(1), Type, rs.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

        return list;
    }

    @Override
    public categorie readById(int id) {
        String requete = "SELECT * FROM categorie WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                //System.out.println("Retrieved ID: " + rs.getInt(1));
                // System.out.println("Retrieved Name: " + rs.getString(2));
                //
                type Type = type.valueOf(rs.getString(2));
                return new categorie(rs.getInt("id"), Type, rs.getString("description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}



