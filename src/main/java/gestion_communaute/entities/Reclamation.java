package gestion_communaute.entities;

public class Reclamation {

    private int id;
    private String type;
    private String description;
    private Status status;
    private int id_adherent;

    public Reclamation( String type, String description, Status status, int id_adherent) {

        this.type = type;
        this.description = description;
        this.status = status;
        this.id_adherent = id_adherent;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId_adherent() {
        return id_adherent;
    }

    public void setId_adherent(int id_adherent) {
        this.id_adherent = id_adherent;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id_adherent=" + id_adherent +
                '}';
    }
    //
    // Enumeration for status
    public enum Status {
        resolved,
        pending
    }
}
