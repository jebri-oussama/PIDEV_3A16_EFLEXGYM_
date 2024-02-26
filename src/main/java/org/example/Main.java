package org.example;

import gestion_suivi.entitis.Exercice;
import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.entitis.Sexe;
import gestion_suivi.service.Exercice_Service;
import gestion_suivi.service.Suivi_Progre_Service;
import gestion_suivi.entitis.Programme_personnalise;
import gestion_suivi.service.Programme_personnalise_service;


public class Main {
    public static void main(String[] args) {

       /* Suivi_Progre_Service ps = new Suivi_Progre_Service();
        /*ps.add(s1);
       ps.delete(s1);*/
       /* ps.readAll().forEach(System.out::println);
      /*  ps.update(s1);
        System.out.println(ps.readById(1));*/
   /* }
}


// Programme de musculation*/
Exercice e1 = new Exercice(1, "Squats", "Travail des jambes", 3, "Quadriceps", 15, "Force", "Barre");
    Exercice e2 = new Exercice(2, "Bench Press", "Travail du haut du corps", 3, "Pectoraux", 10, "Force", "Barre");
    Exercice e3 = new Exercice(3, "Deadlift", "Travail du dos", 3, "Dos", 10, "Force", "Barre");
    Exercice e4 = new Exercice(4, "Shoulder Press", "Travail des épaules", 3, "Epaules", 12, "Force", "Haltères");

    // Programme de perte de poids*/
    Exercice e5 = new Exercice(5, "Burpees", "Entraînement complet", 4, "Corps entier", 10, "Cardio", "Poids corporel");
    Exercice e6 = new Exercice(6, "Jumping Jacks", "Entraînement cardio", 3, "Corps entier", 20, "Cardio", "Poids corporel");
    Exercice e7 = new Exercice(7, "Mountain Climbers", "Entraînement cardio", 3, "Corps entier", 15, "Cardio", "Poids corporel");
    Exercice e8 = new Exercice(8, "High Knees", "Entraînement cardio", 3, "Corps entier", 20, "Cardio", "Poids corporel");

    // Programme de mise en forme*/
    Exercice e9 = new Exercice(9, "Planche", "Gainage", 2, "Abdominaux", 30, "Stabilité", "Sol");
    Exercice e10 = new Exercice(10, "Yoga", "Etirement et relaxation", 1, "Corps entier", 60, "Flexibilité", "Tapis de yoga");
    Exercice e11 = new Exercice(11, "Marche rapide", "Cardio doux", 1, "Corps entier", 30, "Endurance", "En extérieur");
    Exercice e12 = new Exercice(12, "Natation", "Entraînement complet", 1, "Corps entier", 30, "Endurance", "Piscine");

    Exercice_Service es = new Exercice_Service();
/*es.add(e1);
/*
es.add(e2);
es.add(e3);
es.add(e4);
es.add(e5);
es.add(e6);
es.add(e7);
es.add(e8);
es.add(e9);
es.add(e10);
es.add(e11);
es.add(e12);*/

        es.readAll().forEach(System.out::println);
      /*  es.update(e);
    es.delete(e);//
     es.readById();*/


// Création des programmes*/
        Programme_personnalise p1 = new Programme_personnalise(1, "Musculation");
        Programme_personnalise p2 = new Programme_personnalise(2, "Perte de poids");
        Programme_personnalise p3 = new Programme_personnalise(3, "Mise en forme");

        Programme_personnalise_service p = new Programme_personnalise_service();
p.add(p1);
p.add(p2);
p.add(p3);
        p.readAll().forEach(System.out::println);
       /* p.update(e);
    p.delete(e);//
     p.readById();*/
    }}
