package gestion_produit.entities;

public class produit {
   private int id;
   private String nom;
   private String image;
   private float prix;
   private int quantite;
   private String description;
  private  categorie categorie;
  private   int id_bilan_financier;
   private int id_admin;





    public produit() {
    }


    public produit( String nom, String image, float prix, int quantite, String description, categorie categorie, int id_bilan_financier, int id_admin) {
        this.nom = nom;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
        this.description = description;
        this.categorie = categorie;
        this.id_bilan_financier = id_bilan_financier;
        this.id_admin = id_admin;
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    public float getPrix() {
        return prix;
    }

    public int getQuantite() {
        return quantite;
    }
    public String getDescription() {
        return description;
    }

    public int getId_bilan_financier() {
        return id_bilan_financier;
    }
    public int getId_admin() {
        return id_admin;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setId_bilan_financier(int id_bilan_financier) {
        this.id_bilan_financier = id_bilan_financier;
    }
    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public  categorie getCategorie() {
        return categorie;
    }


    public void setCategorie(categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                ", prix=" + prix +
                ", quantite=" + quantite +
                ", description='" + description + '\'' +
                  categorie +
                ", id_bilan_financier=" + id_bilan_financier +
                ", id_admin=" + id_admin +
                '}';
    }
}
