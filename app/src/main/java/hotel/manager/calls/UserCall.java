package hotel.manager.calls;

import android.util.Log;

import androidx.annotation.NonNull;

import hotel.manager.callbacks.LoginCallback;
import hotel.manager.entities.User;
import hotel.manager.entities.UserLogin;
import hotel.manager.entities.UserLoginResponse;
import hotel.manager.retrofitInterfaces.LoginRetroInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCall {

    public User user;
    public String error;
    public String token;
    private String URL = "http://10.0.2.2:8090/auth/";
    private LoginRetroInterface api;

    public UserCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.api = retrofit.create(LoginRetroInterface.class);
    }

    public void loginUser(UserLogin request, LoginCallback callback){
        Call<UserLoginResponse> call = api.login(request);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserLoginResponse> call, @NonNull Response<UserLoginResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    token = response.body().getToken();
                    callback.onSuccess(token);
                } else {
                    callback.onError("Login failed: invalid credentials");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserLoginResponse> call, @NonNull Throwable t) {
                Log.e("RETROFIT_ERROR", "Login failed: " + t.getMessage(), t);
                callback.onError("Login failed: " + t.getMessage());
            }
        });
    }

    public void getProfile(Runnable onComplete){
        Call<User> call = api.getUserProfile("Bearer " + token , token) ;
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        user = response.body();
                        onComplete.run();
                    }
                    else {
                        error = "Could not retrieve user: " + response.message();
                    }
                } catch (Exception e) {
                    error= "Wait, system is finding user profile";
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                error = "Connection error while retrieving user.";
            }
        });
    }

    public void logout(){
        Call<String> call = api.logout(this.token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try{
                    if(response.isSuccessful()){
                        user = null;
                    }
                } catch (Exception e) {
                    error= "System couldn't logout profile";
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }
}
