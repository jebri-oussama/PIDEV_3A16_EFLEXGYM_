package org.example;


import gestion_produit.entities.categorie;
import gestion_produit.entities.service.categorieService;
import utils.DataSource;
//import static gestion_produit.entities.type.alimentaire;
//import static gestion_produit.entities.type.vestimentaire;
import gestion_produit.entities.produit;
import gestion_produit.entities.service.produitService;

import static gestion_produit.entities.type.vestimentaire;

public class Main {
    public static void main(String[] args) {
     DataSource ds1 = DataSource.getInstance();
        System.out.println(ds1);
       produitService ps= new produitService();
       categorieService pc= new categorieService();
        categorie c1 = new categorie(5,vestimentaire,"ffff");
       // categorie c2= new categorie(7,vestimentaire,"ffff");
        produit p2 = new produit("prot","image.png",50.3F,5,"555555",c1,1,1);
     //  ps.add(p2);
      //  pc.add(c2);
       // ps.delete(2);




      //  categorie p1=ps.readById(1);
       //System.out.println(p2);

     //   pc.delete(6);
//ps.add(p2);

//ps.delete(2);
      //  pc.readAll().forEach(System.out::println);
        ps.readAll().forEach(System.out::println);
      /*  p1.setDescription("lyaass");
        ps.update(1,p1);*/
        }
    }
