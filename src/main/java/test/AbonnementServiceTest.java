package test;

import GestionFinance.entites.Abonnement;
import GestionFinance.service.AbonnementService;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;




public class AbonnementServiceTest {

    @Test
    public void testCalculerTempsRestant() {
        AbonnementService service = new AbonnementService();
        service.testCalculerTempsRestant();
    }

    @Test
    public void testNotifierAdherent() {
        AbonnementService service = new AbonnementService();
        service.testNotifierAdherent();
    }

    @Test
    public void testNotifierTousAdherents() {
        AbonnementService service = new AbonnementService();
        service.notifierTousAdherents();
    }
}
