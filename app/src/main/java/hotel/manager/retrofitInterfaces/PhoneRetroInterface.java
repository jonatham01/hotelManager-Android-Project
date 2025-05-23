package hotel.manager.retrofitInterfaces;

import java.util.List;

import hotel.manager.entities.HotelPhone;
import hotel.manager.entities.HotelPhoneRequest;
import hotel.manager.entities.HotelPhoneResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PhoneRetroInterface {

    @Headers("Content-Type: application/json")
    @GET("hotelphones")
    Call<List<HotelPhoneResponse>> getAllHotelPhones(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("hotelphones")
    Call<HotelPhoneResponse> createNewHotelPhoneResponse(@Header("Authorization") String token, @Body HotelPhoneRequest request);

    @Headers("Content-Type: application/json")
    @PUT("hotelphones/{oldNumber}")
    Call<HotelPhoneResponse> updateHotelPhoneResponse(@Header("Authorization") String token, @Body HotelPhone request, @Path("oldNumber") String oldNumber);

    @Headers("Content-Type: application/json")
    @DELETE("hotelphones/{number}")
    Call<Boolean> deleteHotelPhoneResponse(@Header("Authorization") String token,  @Path("number") String number);


}
