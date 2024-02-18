package GestionFinance.entites;


import java.time.LocalDate;

public class Abonnement  {

    private int id;
    private Type type;
    private double prix;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private Etat etat;
    //Adherent a;
    BilanFinancier b;

    public Abonnement(int id , Type type, double prix, LocalDate date_debut, LocalDate date_fin, Etat etat, /*Adherent a*/ BilanFinancier b) {
        this.type = type;
        this.prix = prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.etat = etat;
        this.b=b;
       // this.a=a;
        this.id=id;

    }

    public Abonnement(Type type, double prix, LocalDate date_debut, LocalDate date_fin, Etat etat, /*Adherent a,*/ BilanFinancier b) {
        this.type = type;
        this.prix = prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.etat = etat;
       // this.a = a;
        this.b = b;
    }

    public Abonnement() {
    }



    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    /*public Adherent getA() {
        return a;
    }*/

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

  /*  public void setA(Adherent a) {
        this.a = a;
    }*/

    public BilanFinancier getB() {
        return b;
    }

    public void setB(BilanFinancier b) {
        this.b = b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        if (this.type.equals(Type.mensuel))
            this.prix = 100;
        else prix=1200;


    }



    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", type=" + type +
                ", prix=" + prix +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", etat=" + etat +
                //", a=" + a +
                ", b=" + b +
                '}';
    }
}
