package hotel.manager.calls;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.logging.Handler;
import java.util.stream.Collectors;

import hotel.manager.HotelActivity;
import hotel.manager.entities.HotelRequest;
import hotel.manager.entities.HotelResponse;
import hotel.manager.retrofitInterfaces.HotelRetroInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelCall {
    private static String URL = "http://10.0.2.2:8090/hotel/";

    public List<HotelResponse> hotels;
    public String res;
    private HotelRetroInterface api;
    public String token;
    public String error;
    private Context context;

    public HotelCall(Context context){
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        this.api = retrofit.create(HotelRetroInterface.class);
    }

    public void findAll(Runnable onComplete){
        Log.d("HotelCall", "Usando token: " + token);

        Call<List<HotelResponse>> call = api.getAllHotelCall("Bearer " + token);
        call.enqueue(new Callback<List<HotelResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<HotelResponse>> call, @NonNull Response<List<HotelResponse>> response) {
                if (response.isSuccessful()) {
                    hotels = response.body();
                    onComplete.run();
                } else {
                    // Mostrar mensaje de error en respuesta
                    error = "Error HTTP: " + response.code();
                    try {
                        String errorBody = response.errorBody().string();
                        error += "\n" + errorBody;
                    } catch (Exception e) {
                        error += "\nNo se pudo leer el cuerpo del error";
                    }

                    new android.os.Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    );
                    Log.e("HotelCall", error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HotelResponse>> call, @NonNull Throwable t) {
                error = "Fallo en la conexión: " + t.getMessage();
                new android.os.Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                );
                Log.e("HotelCall", "onFailure: ", t);
            }
        });
    }


    public void createOne(HotelRequest request){
        Call<HotelResponse>call = api.createOneHotel("Bearer " + token, request);
        call.enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(@NonNull Call<HotelResponse> call, @NonNull Response<HotelResponse> response) {
                try{
                    if(response.isSuccessful()){
                        hotels.add(response.body());
                    }
                }catch (Exception e){
                    error ="System couldn't create new hotel,try again";
                }
            }

            @Override
            public void onFailure(@NonNull Call<HotelResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void update(HotelRequest request, Integer id){
        Call<HotelResponse> call = api.updateOneHotel( "Bearer " + token,id, request );
        call.enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(@NonNull Call<HotelResponse> call, @NonNull Response<HotelResponse> response) {
                try{
                    if(response.isSuccessful()){
                        hotels = hotels.stream()
                                .map(data ->{
                                    if(Objects.equals(data.getId(), id)) return response.body();
                                    return data;
                                }).collect(Collectors.toList());
                    }
                }catch (Exception e){
                    error ="System couldn't update hotel,try again";
                }
            }

            @Override
            public void onFailure(@NonNull Call<HotelResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
