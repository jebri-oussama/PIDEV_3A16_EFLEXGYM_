package gestion_evenement.entities;

public class Participation {

    private int id ;
    private Evenement id_evenement ;
    private int nbr_de_participant;

    public Participation(Evenement id_evenement, int nbr_de_participant) {
        this.id_evenement = id_evenement;
        this.nbr_de_participant = nbr_de_participant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Evenement getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(Evenement id_evenement) {
        this.id_evenement = id_evenement;
    }

    public int getNbr_de_participant() {
        return nbr_de_participant;
    }

    public void setNbr_de_participant(int nbr_de_participant) {
        this.nbr_de_participant = nbr_de_participant;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", id_evenement=" + id_evenement +
                ", nbr_de_participant=" + nbr_de_participant +
                '}';
    }
}
