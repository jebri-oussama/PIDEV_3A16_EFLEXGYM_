package gestion_suivi.entitis;

public class Exercice {
    private  int id;
    private String nom;
    private String description;
    private int nbrDeSet;
    private String groupeMusculaire;
    private int nbrDeRepetitions;
    private String categorieExercice;
    private String typeEquipement;

    public Exercice(String nom, String description, int nbrDeSet, String groupeMusculaire, int nbrDeRepetitions, String categorieExercice, String typeEquipement) {
        this.nom = nom;
        this.description = description;
        this.nbrDeSet = nbrDeSet;
        this.groupeMusculaire = groupeMusculaire;
        this.nbrDeRepetitions = nbrDeRepetitions;
        this.categorieExercice = categorieExercice;
        this.typeEquipement = typeEquipement;
    }

    public Exercice(int id, String nom, String description, int nbrDeSet, String groupeMusculaire, int nbrDeRepetitions, String categorieExercice, String typeEquipement) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.nbrDeSet = nbrDeSet;
        this.groupeMusculaire = groupeMusculaire;
        this.nbrDeRepetitions = nbrDeRepetitions;
        this.categorieExercice = categorieExercice;
        this.typeEquipement = typeEquipement;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbrDeSet() {
        return nbrDeSet;
    }

    public void setNbrDeSet(int nbrDeSet) {
        this.nbrDeSet = nbrDeSet;
    }

    public String getGroupeMusculaire() {
        return groupeMusculaire;
    }

    public void setGroupeMusculaire(String groupeMusculaire) {
        this.groupeMusculaire = groupeMusculaire;
    }

    public int getNbrDeRepetitions() {
        return nbrDeRepetitions;
    }

    public void setNbrDeRepetitions(int nbrDeRepetitions) {
        this.nbrDeRepetitions = nbrDeRepetitions;
    }

    public String getCategorieExercice() {
        return categorieExercice;
    }

    public void setCategorieExercice(String categorieExercice) {
        this.categorieExercice = categorieExercice;
    }

    public String getTypeEquipement() {
        return typeEquipement;
    }

    public void setTypeEquipement(String typeEquipement) {
        this.typeEquipement = typeEquipement;
    }







    @Override
    public String toString() {
        return "Exercice{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", nbrDeSet=" + nbrDeSet +
                ", groupeMusculaire='" + groupeMusculaire + '\'' +
                ", nbrDeRepetitions=" + nbrDeRepetitions +
                ", categorieExercice='" + categorieExercice + '\'' +
                ", typeEquipement='" + typeEquipement + '\'' +
                '}';
    }
}
