package org.example;
import gestion_communaute.entities.Evenement;
import gestion_communaute.entities.Reclamation;
 import gestion_communaute.service.IService;
import gestion_communaute.service.EvenementService;

import java.sql.Timestamp;

public class MainC {

    public static void main(String[] args) {


        /*DataSource ds1 = DataSource.getInstance();
        System.out.println(ds1);*/
        // ReclamationService R = new ReclamationService();
       /* List<Reclamation> filteredReclamations = R.getReclamationsByStatusAndType(Reclamation.Status.pending, "gggg");

        for (Reclamation reclamation : filteredReclamations) {
            System.out.println(reclamation); // Assuming you have overridden the toString method in Reclamation class
        }*/



   /*    Reclamation r1 = new Reclamation(8 ,
                "kkk", "ppppp", Reclamation.Status.resolved,2
        );*/
        //R.add(r1);
        //R.update(r1);
        //R.readAll().forEach(System.out::println);
        //R.delete(7);




        // ID of the Reclamation you want to retrieve
      /*  int reclamationIdToRetrieve = 5; // Change this to the desired ID

        // Retrieve the Reclamation by ID and print it
        Reclamation retrievedReclamation = R.readById(reclamationIdToRetrieve);
        if (retrievedReclamation != null) {
            System.out.println("Retrieved Reclamation: " + retrievedReclamation);
        } else {
            System.out.println("No Reclamation found with ID: " + reclamationIdToRetrieve);
        }*/





        //READ BY ID
       /* EvenementService evenementService = new EvenementService();

        // ID of the Evenement you want to retrieve
        int eventIdToRetrieve = 6; // Change this to the desired ID

        // Retrieve the Evenement by ID and print it
        Evenement retrievedEvenement = evenementService.readById(eventIdToRetrieve);
        if (retrievedEvenement != null) {
            System.out.println("Retrieved Evenement: " + retrievedEvenement);
        } else {
            System.out.println("No Evenement found with ID: " + eventIdToRetrieve);
        }
*/
        EvenementService E= new EvenementService();
        Evenement e1 = new Evenement(
                "2123123123123", Timestamp.valueOf("2024-03-09 12:30:00"), Timestamp.valueOf("2024-02-09 14:00:00"),"20"

        );

        //dynamic delete
        // ID of the Evenement you want to delete
      /*  int eventIdToDelete = 5; // Change this to the desired ID
        //E.delete(eventIdToDelete);
        System.out.println("Evenement with ID " + eventIdToDelete + " deleted successfully.");
*/
        //E.add(e1);
        // E.update(e1);

       E.readAll().forEach(System.out::println);




        // Database connection test



    }}
