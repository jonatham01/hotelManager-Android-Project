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
import retrofit2.http.Query;

public  interface LoginRetroInterface {


    @POST("authenticate")
    Call<UserLoginResponse> login(@Body UserLogin request);

    @Headers({"Content-Type: application/json"})
    @POST("profile")
    Call<User> getUserProfile(@Header("Authorization") String token, @Query("jwt") String jwt);

    @Headers({"Content-Type: application/json"})
    @GET("logout")
    Call<String> logout(@Header("Authorization") String token);

}
