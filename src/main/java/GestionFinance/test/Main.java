package GestionFinance.test;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.AbonnementService;
import GestionFinance.service.BilanFinancierService;
import gestion_user.entities.User;

import utils.DataSource;

import java.time.LocalDate;

import static GestionFinance.entites.Etat.actif;
import static GestionFinance.entites.Etat.non_actif;
import static GestionFinance.entites.Type.mensuel;

public class Main {
    public static void main(String[] args) {
        DataSource ds1 = DataSource.getInstance();
        System.out.println(ds1);

        BilanFinancierService bs = new BilanFinancierService();
        BilanFinancier b2 = new BilanFinancier(2,
                LocalDate.of(2024, 2, 9), LocalDate.of(2024, 3, 9), 1200.0, 100.0);
        //bs.add(b2);

        /*System.out.println(bs.readById(2));

        System.out.println(b2.recupererRevenuAbonnements());
        System.out.println(b2.recupererRevenusProduits());
        System.out.println(b2.recupererSalairesCoachs());
        b2.setRevenus_abonnements(b2.recupererRevenuAbonnements());
        b2.setRevenus_produits(b2.recupererRevenusProduits());
        b2.setSalaires_coachs(b2.recupererSalairesCoachs());
        System.out.println(b2.calculerProfit());
        b2.setProfit(b2.calculerProfit());
        bs.update(b2);*/
        //    bs.readAll().forEach(System.out::println);

/*
        AdherentService ads = new AdherentService();
       User adherent = ads.readById(7);
       BilanFinancier b1 = bs.readById(1);
     System.out.println(adherent);
       //System.out.println(b1);

        AbonnementService abs = new AbonnementService();
        Abonnement a1 = new Abonnement(7,mensuel,120,LocalDate.of(2024,2,9), LocalDate.of(2024,3,9),actif,adherent,b1);
        //abs.add(a1);
       System.out.println(abs.readById(2).getId_adherent().getId());
      //  abs.readAll().forEach(System.out::println);

     //   System.out.println(abs.readById(7));
        //Abonnement a2 = new Abonnement();
        //abs.add(a2);
        //a1.setEtat(non_actif);
        //abs.update(a1);
       // System.out.println(abs.readById(7));
        //abs.readAll().forEach(System.out::println);
         Abonnement a = abs.readById(2);
         a.setEtat(non_actif);
         abs.update(a);

    }*/
    }
}
