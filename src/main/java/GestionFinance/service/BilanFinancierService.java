package GestionFinance.service;


import GestionFinance.entites.BilanFinancier;
import utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BilanFinancierService implements IService<BilanFinancier> {

    private static  Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public BilanFinancierService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(BilanFinancier bf) {
        String requete = "INSERT INTO bilan_financier (id, date_debut, date_fin, salaires_coachs, prix_location, revenus_abonnements, revenus_produits, depenses, profit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, bf.getId());
            pst.setDate(2, Date.valueOf(bf.getDateDebut()));
            pst.setDate(3, Date.valueOf(bf.getDateFin()));
            pst.setDouble(4, bf.getSalaires_coachs());
            pst.setDouble(5, bf.getPrix_location());
            pst.setDouble(6, bf.getRevenus_abonnements());
            pst.setDouble(7, bf.getRevenus_produits());
            pst.setDouble(8, bf.getDepenses());
            pst.setDouble(9, bf.getProfit());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(BilanFinancier bf) {
        String requete = "DELETE FROM bilan_financier WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setInt(1 , bf.getId());
            pst.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(BilanFinancier bf) {
        String requete = "UPDATE bilan_financier SET date_debut = ?, date_fin = ?, salaires_coachs = ?, prix_location = ?, profit = ?, revenus_abonnements = ?, revenus_produits = ?, depenses = ? WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setDate(1, Date.valueOf(bf.getDateDebut()));
            pst.setDate(2, Date.valueOf(bf.getDateFin()));
            pst.setDouble(3, bf.getSalaires_coachs());
            pst.setDouble(4, bf.getPrix_location());
            pst.setDouble(5, bf.getRevenus_abonnements());
            pst.setDouble(6, bf.getRevenus_produits());
            pst.setDouble(7, bf.getDepenses());
            pst.setDouble(8, bf.getProfit());
            pst.setInt(9, bf.getId());

            pst.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BilanFinancier> readAll() {
        String requete = "SELECT * FROM bilan_financier";
        List<BilanFinancier> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate dateDebut = rs.getDate("date_debut").toLocalDate();
                LocalDate dateFin = rs.getDate("date_fin").toLocalDate();
                double salaires_coachs = rs.getDouble("salaires_coachs");
                double prix_location = rs.getDouble("prix_location");
                double revenus_abonnements = rs.getDouble("revenus_abonnements");
                double revenus_produits = rs.getDouble("revenus_produits");
                double depenses = rs.getDouble("depenses");
                double profit = rs.getDouble("profit");
                BilanFinancier bilan = new BilanFinancier(id, dateDebut, dateFin, salaires_coachs, prix_location, revenus_abonnements, revenus_produits, depenses, profit);
                list.add(bilan);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public BilanFinancier readById(int id) {
        String requete = "SELECT * FROM bilan_financier WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                LocalDate dateDebut = rs.getDate("date_debut").toLocalDate();
                LocalDate dateFin = rs.getDate("date_fin").toLocalDate();
                double salaires_coachs = rs.getDouble("salaires_coachs");
                double prix_location = rs.getDouble("prix_location");
                double revenus_abonnements = rs.getDouble("revenus_abonnements");
                double revenus_produits = rs.getDouble("revenus_produits");
                double depenses = rs.getDouble("depenses");
                double profit = rs.getDouble("profit");
                return new BilanFinancier(id, dateDebut, dateFin, salaires_coachs, prix_location, revenus_abonnements, revenus_produits, depenses, profit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public int getIdBilanFinancier() {
        List<BilanFinancier> bilanFinanciers = readAll();
        if (!bilanFinanciers.isEmpty()) {
            return bilanFinanciers.get(0).getId();
        } else {
            throw new RuntimeException("Aucun Bilan Financier");
        }
    }
    public static List<Integer> getAllIdBilanFinancier() {
        String query = "SELECT id FROM bilan_financier";
        List<Integer> idList = new ArrayList<>();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                idList.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idList;
    }
}