package gestion_user;

import GestionFinance.service.BilanFinancierService;
import gestion_user.entities.User;
import gestion_user.service.UserService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


        UserService bs = new UserService();
        System.out.println(
                bs.readAllAdherent()
        );


        }
    }
