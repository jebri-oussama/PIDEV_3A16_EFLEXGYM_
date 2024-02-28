package org.example;
import gestion_evenement.entities.Evenement;
import gestion_evenement.entities.Type;
import gestion_evenement.service.EvenementService;
import gestion_evenement.service.TypeService;

import java.sql.Timestamp;

public class MainC {

    public static void main(String[] args) {


        /*DataSource ds1 = DataSource.getInstance();
      System.out.println(ds1);*/
   /*    TypeService R = new TypeService();
       Type type = new Type("aa");
       int id = 1; // Replace 1 with the actual ID of the type you want to update

        //R.readAll().forEach(System.out::println);
       // R.add(type); // Assuming you have overridden the toString method in Reclamation class
*/


   /*    Reclamation r1 = new Reclamation(8 ,
                "kkk", "ppppp", Reclamation.Status.resolved,2
        );*/
        //R.add(r1);
        //R.update(r1);

        //R.delete(7);

/*


        // ID of the Reclamation you want to retrieve
      int reclamationIdToRetrieve = 1; // Change this to the desired ID

        // Retrieve the Reclamation by ID and print it
        Type retrievedReclamation = R.readById(reclamationIdToRetrieve);
        if (retrievedReclamation != null) {
            System.out.println("Retrieved Reclamation: " + retrievedReclamation);
        } else {
            System.out.println("No Reclamation found with ID: " + reclamationIdToRetrieve);
        }

*/



        //READ BY ID
       EvenementService evenementService = new EvenementService();

        // ID of the Evenement you want to retrieve
    /*    int eventIdToRetrieve = 15; // Change this to the desired ID

        // Retrieve the Evenement by ID and print it
        Evenement retrievedEvenement = evenementService.readById(eventIdToRetrieve);
        if (retrievedEvenement != null) {
            System.out.println("Retrieved Evenement: " + retrievedEvenement);
        } else {
            System.out.println("No Evenement found with ID: " + eventIdToRetrieve);
        }*/
   //EvenementService evenementService = new EvenementService();

   TypeService typeService = new TypeService();

    int typeId = 1; // Replace your_type_id_here with the actual type ID
    Type type = typeService.readById(typeId);

    Evenement e1 = new Evenement(type,"a", Timestamp.valueOf("2924-03-09 12:30:00"), Timestamp.valueOf("2024-02-09 14:00:00"), "hhh.png");

//evenementService.add(e1);


    //dynamic delete
        // ID of the Evenement you want to delete
    /*   int eventIdToDelete = 5; // Change this to the desired ID
        //E.delete(eventIdToDelete);
        System.out.println("Evenement with ID " + eventIdToDelete + " deleted successfully.");
*/
        //E.add(e1);
        // E.update(e1);

      evenementService.readAll().forEach(System.out::println);




        // Database connection test



    }}
