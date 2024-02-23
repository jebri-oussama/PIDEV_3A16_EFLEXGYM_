package gestion_produit.entities;

public class categorie {
    private  int id;
    private type type;
    private String description;



    public categorie(int id, type type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public categorie setId(int id) {
        this.id = id;
        return null;

    }

    public type getType() {
        return type;
    }

    public categorie setType(type type) {
        this.type = type;
        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "categorie{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                '}';
    }
}
