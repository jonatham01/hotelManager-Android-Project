package hotel.manager.retrofitInterfaces;

import java.util.List;

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
    @Headers({"Content-type: application.json"})
    @GET()
    Call<List<HotelPhoneResponse>> getAllHotelPhones(@Header("Authentication") String token);

    @Headers({"Content-type: application.json"})
    @POST()
    Call<HotelPhoneResponse> createNewHotelPhoneResponse(@Header("Authorization") String token, @Body HotelPhoneRequest request);

    @Headers({"Content-type: application.json"})
    @PUT("{id}")
    Call<HotelPhoneResponse> updateHotelPhoneResponse(@Header("Authorization") String token, @Body HotelPhoneRequest request, @Path("id") Short id);

    @Headers({"Content-type: application.json"})
    @DELETE("{id}")
    Call<Boolean> deleteHotelPhoneResponse(@Header("Authorization") String token,  @Path("id") Short id);


}
