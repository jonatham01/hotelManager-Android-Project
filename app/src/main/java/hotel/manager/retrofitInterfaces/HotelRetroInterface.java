package hotel.manager.retrofitInterfaces;

import java.util.List;

import hotel.manager.entities.HotelPhoneRequest;
import hotel.manager.entities.HotelResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HotelRetroInterface {
    @GET("hotels")
    Call<List<HotelResponse>> getAllHotelCall();

    @GET("hotels/{id}")
    Call<HotelResponse> getOneHotelCall();

    @POST("hotels")
    Call<HotelResponse> createOneHotel(@Body HotelPhoneRequest request);
}
