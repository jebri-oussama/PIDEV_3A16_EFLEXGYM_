package gestion_user.entities;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashing {

    // Hash a password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify a password against its hash
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static void main(String[] args) {
        // Example usage
        String plainPassword = "user123";
        String hashedPassword = hashPassword(plainPassword);

        System.out.println("Plain Password: " + plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        // Verify password
        String inputPassword = "user123";
        if (verifyPassword(inputPassword, hashedPassword)) {
            System.out.println("Password Matched!");
        } else {
            System.out.println("Password Mismatch!");
        }
    }
}

