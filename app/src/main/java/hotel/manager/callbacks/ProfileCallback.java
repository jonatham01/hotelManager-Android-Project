package hotel.manager.callbacks;

import hotel.manager.entities.User;

public interface ProfileCallback {
    void onProfileLoaded(User user);
    void onError(String error);
}