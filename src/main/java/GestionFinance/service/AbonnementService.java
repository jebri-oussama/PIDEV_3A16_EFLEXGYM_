package GestionFinance.service;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements IService<Abonnement> {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public AbonnementService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Abonnement a) {
        String requete = "INSERT INTO Abonnement (id, type, prix, date_debut, date_fin, etat, id_adherent, id_bilan_financier) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, a.getId());
            pst.setString(2, a.getType().toString());
            pst.setDouble(3,a.getPrix());
            pst.setDate(4, Date.valueOf(a.getDate_debut()));
            pst.setDate(5, Date.valueOf(a.getDate_fin()));
            pst.setString(6, a.getEtat().toString());
            pst.setInt(7, a.getA().getId());
            pst.setInt(8, a.getB().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Abonnement abonnement) {
        String requete = "DELETE FROM Abonnement WHERE id = ?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setInt(1 , abonnement.getId());
            pst.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Abonnement abonnement) {
        String requete = "UPDATE Abonnement SET type = ?, prix = ?, date_debut= ?, date_fin = ?, etat= ?, id_adherent = ?, id_bilan_financier = ? WHERE id =?";
        try{
            pst = conn.prepareStatement(requete);
            pst.setString(1, abonnement.getType().toString());
            pst.setDouble(2, abonnement.getPrix());
            pst.setDate(3, Date.valueOf(abonnement.getDate_debut()));
            pst.setDate(4, Date.valueOf(abonnement.getDate_fin()));
            pst.setString(5,abonnement.getEtat().toString());
            pst.setInt(6,abonnement.getA().getId());
            pst.setInt(7,abonnement.getB().getId());
            pst.setInt(8,abonnement.getId());
            pst.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<Abonnement> readAll() {
        String requete = "SELECT * FROM Abonnement";
        List<Abonnement> list = new ArrayList<>();
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Type type = Type.valueOf(rs.getString("type"));
                double prix = rs.getDouble("prix");
                LocalDate dateDebut = rs.getDate("date_debut").toLocalDate();
                LocalDate dateFin = rs.getDate("date_fin").toLocalDate();
                Etat etat = Etat.valueOf(rs.getString("etat"));


                int idAdherent = rs.getInt("id_adherent");
                Adherent adherent = AdherentService.readById(idAdherent);


                int idBilanFinancier = rs.getInt("id_bilan_financier");
                BilanFinancier bilanFinancier = BilanFinancierService.readById(idBilanFinancier);

                list.add(new Abonnement(id, type, prix, dateDebut, dateFin, etat, adherent, bilanFinancier));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }



    @Override
    public Abonnement readById(int id) {
        String requete = "SELECT * FROM Abonnement WHERE id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Type type = Type.valueOf(rs.getString("type"));
                double prix = rs.getDouble("prix");
                LocalDate dateDebut = rs.getDate("date_debut").toLocalDate();
                LocalDate dateFin = rs.getDate("date_fin").toLocalDate();
                Etat etat = Etat.valueOf(rs.getString("etat"));


                int idAdherent = rs.getInt("id_adherent");
                gestion_user.entities.Adherent adherent = adherentService.readById(idAdherent);

                int idBilanFinancier = rs.getInt("id_bilan_financier");
                BilanFinancier bilanFinancier = bilanFinancierService.readById(idBilanFinancier);

                return new Abonnement(id, type, prix, dateDebut, dateFin, etat, adherent, bilanFinancier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
