package org.example;

import org.example.Gestion_planing.entities.TypeCours;
import org.example.Gestion_planing.entities.cours;
import org.example.Gestion_planing.entities.planning;
import org.example.Gestion_planing.service.PlanningService;
import org.example.Gestion_user.entities.Role;
import org.example.Gestion_user.entities.Sexe;
import org.example.Gestion_user.entities.User;
import org.example.utils.DataSource;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        DataSource ds1 = DataSource.getInstance();
        System.out.println(ds1);

        cours c = new cours( 1,"pilate", TypeCours.En_Ligne ,3);
        User u = new User(9,"AAAA","VVVVVV","HHHHHHH","AAAAAA",Date.valueOf("2024-02-07"), Sexe.femelle, Role.Coach);
        planning p = new planning(
                3, "s2" ,3,
                Date.valueOf("2024-02-10"),
                Time.valueOf("14:00:00") ,c,
                u);
        PlanningService PS = new PlanningService() ;
        PS.add(p);


            //@Override
            //public void update(planning planning) {

            //}

            //@Override
            //public List<planning> readAll() {
            //    return null;
            //}

            //@Override
           // public planning readById(int id) {
             //   return null;
           // }
        };

    }
