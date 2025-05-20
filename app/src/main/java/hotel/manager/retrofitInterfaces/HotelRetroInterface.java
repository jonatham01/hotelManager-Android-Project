package hotel.manager.retrofitInterfaces;

import java.util.List;

import hotel.manager.entities.HotelPhoneRequest;
import hotel.manager.entities.HotelRequest;
import hotel.manager.entities.HotelResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HotelRetroInterface {
    @Headers({"Content-Type: application/json"})
    @GET("show/all")
    Call<List<HotelResponse>> getAllHotelCall(@Header("Authorization") String authToken);


    @Headers({"Content-Type: application/json"})
    @GET("find/attributes")
    Call<HotelResponse> getOneHotelCall(
            @Header("Authorization") String authToken,
            @Header("Authorization") String country,
            @Header("Authorization") String state,
            @Header("Authorization") String city,
            @Header("Authorization") String name
    );

    @Headers({"Content-Type: application/json"})
    @POST("new")
    Call<HotelResponse> createOneHotel(
            @Header("Authorization") String authToken,
            @Body HotelRequest request);

    @Headers({"Content-Type: application/json"})
    @PUT("update/{id}")
    Call<HotelResponse> updateOneHotel(
            @Header("Authorization") String authToken,
            @Path("id") Integer id, @Body HotelRequest request);

    @Headers({"Content-Type: application/json"})
    @DELETE("{id}")
    Call<?> deleteHotel(@Header("Authorization") String authToken, @Path("id") Integer id);

}
