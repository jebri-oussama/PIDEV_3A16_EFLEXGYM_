package gestion_user.entities;

import GestionFinance.entites.Abonnement;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class UserSession {
    private static User loggedInUser;
    private static Abonnement userAbonnement; // Store the user's abonnement details


    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public static void clearSession() {
        loggedInUser = null;
    }
    public static void setUserAbonnement(Abonnement abonnement) {
        userAbonnement = abonnement;
    }

    public static Abonnement getAbonnement() {
        return userAbonnement;
    }


    private static Map<Integer, Integer> userAbonnementMap = new HashMap<>();

    public static void setAbonnementIdForUser(int userId, int abonnementId) {
        userAbonnementMap.put(userId, abonnementId);
    }

    public static int getAbonnementIdForUser(int userId) {
        return userAbonnementMap.getOrDefault(userId, -1); // Return -1 if no abonnement found for the user
    }
}