package hotel.manager.callbacks;

public interface LoginCallback {
    void onSuccess(String token);
    void onError(String message);
}