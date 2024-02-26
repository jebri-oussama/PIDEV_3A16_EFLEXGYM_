package Gestion_planing.entities;

public enum TypeCours {
    Presentiel,
    En_Ligne;

    @Override
    public String toString() {
        return name(); // You can change this to customize the display value if needed
    }
}
