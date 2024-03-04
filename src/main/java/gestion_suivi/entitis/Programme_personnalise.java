package gestion_suivi.entitis;

public class Programme_personnalise {
    private int id;
    private String objectif;

    public Programme_personnalise(int id, String objectif) {
        this.id = id;
        this.objectif = objectif;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    @Override
    public String toString() {
        return "Programme_personnalise{" +
                "id=" + id +
                ", objectif='" + objectif + '\'' +
                '}';
    }

    public void addExercice(Exercice e1) {
    }
}
