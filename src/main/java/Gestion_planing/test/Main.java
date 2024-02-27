package Gestion_planing.test;

import Gestion_planing.entities.TypeCours;
import Gestion_planing.entities.cours;
import Gestion_planing.entities.planning;
import Gestion_planing.service.CoursService;
import Gestion_planing.service.PlanningService;
import gestion_user.entities.Role;
import gestion_user.entities.Sexe;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import utils.DataSource;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        DataSource ds1 = DataSource.getInstance();
        System.out.println(ds1);

        /*cours c = new cours( 1,"pilate", TypeCours.En_Ligne ,"3");
        User u = new User(10,"AAAA","VVVVVV","HHHHHHH","AAAAAA",Date.valueOf("2024-02-07"), Sexe.femelle, Role.Coach);
        planning p = new planning(
                3, "s2" ,3,
                LocalDate.parse("2024-02-10"),
                "12:00",c,
                u);
        PlanningService PS = new PlanningService() ;
        PS.readAll().forEach(System.out::println);*/

        User user=new User();
        cours cour=new cours();
        CoursService cs= new CoursService();
        UserService us =new UserService();
        user=us.readById(10);
        cour=cs.readById(2);
        planning p = new planning(
                "s2" ,3,
                LocalDate.parse("2024-02-10"),
                "12:00",cour,
                user);

        planning p1=new planning(2,"s2",4,LocalDate.parse("2024-02-10"),"44",cour,user);
PlanningService pla=new PlanningService();
pla.update(p1);



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
