package gestion_suivi.entitis;

public class Suivi_Progre {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private double taille;
    private double poids;
    private double tour_de_taille;
    private Sexe sexe;
    private User idUser; // Remplacement de l'attribut id_user par l'objet User

    public Suivi_Progre(int id, String nom, String prenom, int age, double taille, double poids, double tour_de_taille, Sexe sexe, User idUser) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.taille = taille;
        this.poids = poids;
        this.tour_de_taille = tour_de_taille;
        this.sexe = sexe;
        this.idUser = idUser;
    }
    public Suivi_Progre( String nom, String prenom, int age, double taille, double poids, double tour_de_taille, Sexe sexe, User idUser) {

        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.taille = taille;
        this.poids = poids;
        this.tour_de_taille = tour_de_taille;
        this.sexe = sexe;
        this.idUser = idUser;
    }

    public double getIMC() {
        return poids / Math.pow(taille / 100, 2);
    }

    public double getIMG() {
        return (1.2 * getIMC()) + (0.23 * age) - (10.8 * (sexe == Sexe.homme ? 1 : 0)) - 5.4;
    }

    public String getEvaluation() {
        double imc = getIMC();
        double img = getIMG();
        String message = "Votre IMC est " + imc + " et votre IMG est " + img + ". ";
        if (sexe == Sexe.homme) {
            if (imc < 20) {
                message += "Votre IMC est faible, vous devez prendre de la masse. Voir le programme 3. Courage!";
            } else if (imc < 25) {
                message += "Votre IMC est normal, vous devez vous mettre en forme. Voir le programme 2. Courage!";
            } else {
                message += "Votre IMC est élevé, vous devez perdre du poids. Voir le programme 1. Courage!";
            }
            if (img < 15) {
                message += " Votre IMG est faible, vous devez prendre de la masse. Voir le programme 3. Courage!";
            } else if (img < 20) {
                message += " Votre IMG est normal, vous devez vous mettre en forme. Voir le programme 2. Courage!";
            } else {
                message += " Votre IMG est élevé, vous devez perdre du poids. Voir le programme 1. Courage!";
            }
        } else { // femme
            if (imc < 19) {
                message += "Votre IMC est faible, vous devez prendre de la masse. Voir le programme 3. Courage!";
            } else if (imc < 24) {
                message += "Votre IMC est normal, vous devez vous mettre en forme. Voir le programme 2. Courage!";
            } else {
                message += "Votre IMC est élevé, vous devez perdre du poids. Voir le programme 1. Courage!";
            }
            if (img < 25) {
                message += " Votre IMG est faible, vous devez prendre de la masse. Voir le programme 3. Courage!";
            } else if (img < 30) {
                message += " Votre IMG est normal, vous devez vous mettre en forme. Voir le programme 2. Courage!";
            } else {
                message += " Votre IMG est élevé, vous devez perdre du poids. Voir le programme 1. Courage!";
            }
        }
        return message;
    }

    @Override
    public String toString() {
        return "Suivi_Progre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", taille=" + taille +
                ", poids=" + poids +
                ", tour_de_taille=" + tour_de_taille +
                ", sexe=" + sexe +
                ", user=" + idUser +
                ", IMC=" + getIMC() +
                ", IMG=" + getIMG() +
                ", evaluation='" + getEvaluation() + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getTour_de_taille() {
        return tour_de_taille;
    }

    public void setTour_de_taille(double tour_de_taille) {
        this.tour_de_taille = tour_de_taille;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
}

