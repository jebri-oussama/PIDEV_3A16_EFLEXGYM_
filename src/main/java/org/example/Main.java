package org.example;

import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.entitis.Sexe;
import gestion_suivi.service.Suivi_Progre_Service;

public class Main {
    public static void main(String[] args) {

        Suivi_Progre_Service ps = new Suivi_Progre_Service();
        /*ps.add(s1);
       ps.delete(s1);*/
        ps.readAll().forEach(System.out::println);
      /*  ps.update(s1);
        System.out.println(ps.readById(1));*/
    }
}


