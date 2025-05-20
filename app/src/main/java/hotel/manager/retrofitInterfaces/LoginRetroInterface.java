package hotel.manager.retrofitInterfaces;

import hotel.manager.entities.User;
import hotel.manager.entities.UserLogin;
import hotel.manager.entities.UserLoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public  interface LoginRetroInterface {

    @Headers({"Content-Type: application/json"})
    @POST("authenticate")
    Call<UserLoginResponse> login(
            @Header("Authorization") String token,
            @Body UserLogin request);

    @Headers({"Content-Type: application/json"})
    @GET("profile")
    Call<User> getUserProfile(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @GET("logout")
    Call<String> logout(@Header("Authorization") String token);

}
