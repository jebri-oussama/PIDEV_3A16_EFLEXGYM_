package GestionFinance.test;

import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import utils.DataSource;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DataSource ds1 = DataSource.getInstance();
        System.out.println(ds1);

        BilanFinancierService bs = new BilanFinancierService();
        BilanFinancier b2 = new BilanFinancier(2,
                LocalDate.of(2024,2,9), LocalDate.of(2024,3,9),1200.0, 100.0);
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
        bs.readAll().forEach(System.out::println);
    }
}
