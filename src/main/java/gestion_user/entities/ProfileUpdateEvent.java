package gestion_user.entities;

public class ProfileUpdateEvent {
    private final User updatedUser;

    public ProfileUpdateEvent(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }
}
