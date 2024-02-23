package gestion_suivi.service;

import gestion_suivi.entitis.Role;
import gestion_suivi.entitis.Sexe;
import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.entitis.User;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Suivi_Progre_Service implements IService<Suivi_Progre> {
    private Connection conn;
    private PreparedStatement pst;

    public Suivi_Progre_Service() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Suivi_Progre s) {
        if (s.getIdUser().getRole().equals(Role.adherant)) {
            String requete = "INSERT INTO suivi_progre ( nom, prenom, age, taille, poids, tour_de_taille, sexe, idUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                pst = conn.prepareStatement(requete);
                pst.setString(1, s.getNom());
                pst.setString(2, s.getPrenom());
                pst.setInt(3, s.getAge());
                pst.setDouble(4, s.getTaille());
                pst.setDouble(5, s.getPoids());
                pst.setDouble(6, s.getTour_de_taille());
                pst.setString(7, s.getSexe().toString());
                pst.setInt(8, s.getIdUser().getId());

                pst.executeUpdate();
                System.out.println(s.getIdUser().getId());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("L'utilisateur n'a pas le droit d'ajouter un suivi.");
        }
    }



    @Override
    public void delete(Suivi_Progre s) {
        String requete = "DELETE FROM suivi_progre WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, s.getId());
            pst.executeUpdate();
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
    public  List<Suivi_Progre> readAll() {
        String requete = "SELECT * FROM suivi_progre";
        List<Suivi_Progre> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Sexe s = Sexe.valueOf(rs.getString(8));
                User u = new User();
                u.setId(rs.getInt(9));
                list.add(new Suivi_Progre(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        s,
                        u
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
                Sexe s = Sexe.valueOf(rs.getString(8));
                User u = new User();
                u.setId(rs.getInt(9));
                return new Suivi_Progre(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        s,
                        u
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
