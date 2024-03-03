package GestionFinance.service;

import GestionFinance.entites.*;
import gestion_user.entities.Role;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import org.testng.annotations.Test;
import utils.DataSource;


import javax.mail.MessagingException;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class AbonnementService implements IService<Abonnement> {

    private Connection conn;
    private PreparedStatement pst;

    public AbonnementService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Abonnement a) {
        String requete = "INSERT INTO Abonnement (id, type, prix, date_debut, date_fin, etat, id_adherent, id_bilan_financier) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
        if (a == null || a.getType() == null || a.getId_adherent() == null || a.getId_bilan_financier() == null) {
            throw new IllegalArgumentException("Les données d'entrée sont incomplètes.");
        }
        if (a.getPrix() <= 0 || a.getDate_debut() == null || a.getDate_fin() == null || a.getEtat() == null) {
            throw new IllegalArgumentException("Les données d'abonnement ne sont pas valides.");
        }
        if (a.getId_adherent().getRole() != Role.Adherent) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un adhérent.");
        }
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, a.getId());
            pst.setString(2, a.getType().toString());
            pst.setDouble(3, a.getPrix());
            pst.setDate(4, Date.valueOf(a.getDate_debut()));
            pst.setDate(5, Date.valueOf(a.getDate_fin()));
            pst.setString(6, a.getEtat().toString());
            pst.setInt(7, a.getId_adherent().getId());
            pst.setInt(8, a.getId_bilan_financier().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Abonnement abonnement) {
        String requete = "DELETE FROM Abonnement WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, abonnement.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Abonnement abonnement) {
        String requete = "UPDATE Abonnement SET type = ?, prix = ?, date_debut= ?, date_fin = ?, etat= ?, id_adherent = ?, id_bilan_financier = ? WHERE id =?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, abonnement.getType().toString());
            pst.setDouble(2, abonnement.getPrix());
            pst.setDate(3, Date.valueOf(abonnement.getDate_debut()));
            pst.setDate(4, Date.valueOf(abonnement.getDate_fin()));
            pst.setString(5, abonnement.getEtat().toString());
            pst.setInt(6, abonnement.getId_adherent().getId());
            pst.setInt(7, abonnement.getId_bilan_financier().getId());
            pst.setInt(8, abonnement.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
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
                UserService adherentService = new UserService();
                User adherent = adherentService.readById(idAdherent);
                int idBilanFinancier = rs.getInt("id_bilan_financier");
                BilanFinancierService bilanFinancierService = new BilanFinancierService();
                BilanFinancier bilanFinancier = bilanFinancierService.readById(idBilanFinancier);
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
                UserService adherentService = new UserService();
                User adherent = adherentService.readById(idAdherent);

                int idBilanFinancier = rs.getInt("id_bilan_financier");
                BilanFinancierService bilanFinancierService = new BilanFinancierService();
                BilanFinancier bilanFinancier = bilanFinancierService.readById(idBilanFinancier);

                return new Abonnement(id, type, prix, dateDebut, dateFin, etat, adherent, bilanFinancier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    public long calculerTempsRestant(Abonnement abonnement) {
        LocalDate dateFinAbonnement = abonnement.getDate_fin();
        LocalDate dateActuelle = LocalDate.now();
        return ChronoUnit.DAYS.between(dateActuelle, dateFinAbonnement);
    }

    // Méthode pour notifier un adhérent lorsque son abonnement expire bientôt
  /*  public void notifierAdherent(Abonnement abonnement) {
        long joursRestants = calculerTempsRestant(abonnement);
        if (joursRestants <= 2) {
            User adherent = abonnement.getId_adherent();
            if (adherent != null) {
                System.out.println("ID de l'adhérent associé à l'abonnement : " + adherent.getId());
                String sujet = "Notification d'abonnement";
                String message = "Bonjour " + adherent.getNom() + ",\n\nVotre abonnement expire dans " + joursRestants + " jours. Veuillez renouveler votre abonnement dès que possible.\n\nCordialement,\nVotre salle de sport";
                String destinataire = adherent.getEmail();
                try {
                    MailAPI.sendMail(destinataire, sujet, message);
                    System.out.println("Notification envoyée à l'adhérent : " + destinataire);
                } catch (MessagingException e) {
                    System.out.println("Erreur lors de l'envoi de la notification : " + e.getMessage());
                    e.printStackTrace(); // Imprimez la trace de la pile pour plus d'informations sur l'erreur
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Impossible d'envoyer la notification : adhérent non trouvé.");
            }
        }
    }



    public void notifierTousAdherents() {
        List<Abonnement> abonnements = readAll();
        for (Abonnement abonnement : abonnements) {
            notifierAdherent(abonnement);
        }
    }

    // Méthode de test pour calculer le temps restant d'un abonnement
    public void testCalculerTempsRestant() {
        Abonnement abonnement = new Abonnement();
        abonnement.setDate_fin(LocalDate.now().plusDays(2)); // Abonnement expirant dans 2 jours

        long tempsRestant = calculerTempsRestant(abonnement);

        System.out.println("Temps restant de l'abonnement : " + tempsRestant + " jours");
    }

    // Méthode de test pour envoyer un email à un adhérent
    public void testNotifierAdherent() {
        Abonnement abonnement = new Abonnement();
        abonnement.setId(1); // ID de l'abonnement
        abonnement.setDate_fin(LocalDate.now().plusDays(2)); // Abonnement expirant dans 2 jours
        User adherent = new User();
        adherent.setNom("Nom de l'adhérent");
        adherent.setEmail("email@domaine.com");

        notifierAdherent(abonnement);
    }*/
  /*  public List<Abonnement> searchByName(String searchTerm) {
        List<Abonnement> searchResults = new ArrayList<>();
        String query = "SELECT * FROM abonnement WHERE type LIKE ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Populate Abonnement objects from the result set
                Abonnement abonnement = new Abonnement();
                // Set properties of abonnement
                // abonnement.setType(resultSet.getString("type"));
                // Populate other properties similarly
                searchResults.add(abonnement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }*/

}


