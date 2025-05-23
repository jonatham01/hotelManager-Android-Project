package hotel.manager.calls;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import hotel.manager.entities.HotelPhone;
import hotel.manager.entities.HotelPhoneRequest;
import hotel.manager.entities.HotelPhoneResponse;
import hotel.manager.retrofitInterfaces.PhoneRetroInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelPhoneCall {
    private String URL ="http://10.0.2.2:8090/";
    public List<HotelPhoneResponse> phoneList;
    public String res;
    PhoneRetroInterface api;
    public String token;
    String error;

    public HotelPhoneCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(PhoneRetroInterface.class);
    }

    public void findAll(Runnable onComplete){
        Call<List<HotelPhoneResponse>> call = api.getAllHotelPhones("Bearer " + token);
        call.enqueue(new Callback<List<HotelPhoneResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<HotelPhoneResponse>> call, @NonNull Response<List<HotelPhoneResponse>> response) {
                try {
                    if(response.isSuccessful()) {
                        phoneList = response.body();
                        onComplete.run();
                    }
                } catch (Exception e) {
                    error = "System could not find any phone";
                    Log.e("error:" , Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HotelPhoneResponse>> call, @NonNull Throwable t) {
                Log.e("error:" , "Onfailure", t);
            }


        });
    }


    public void create(HotelPhoneRequest request){
        Call<HotelPhoneResponse> call = api.createNewHotelPhoneResponse("Bearer " + token, request);
        call.enqueue(new Callback<HotelPhoneResponse>() {
            @Override
            public void onResponse(Call<HotelPhoneResponse> call, Response<HotelPhoneResponse> response) {
                try{
                    if(response.isSuccessful())phoneList.add(response.body());
                } catch (Exception e) {
                    error = "Error: System could not create new Phone";
                }
            }

            @Override
            public void onFailure(Call<HotelPhoneResponse> call, Throwable t) {

            }
        });
    }

    public void update(HotelPhone request, String number,Runnable onComplete){
        Call<HotelPhoneResponse> call = api.updateHotelPhoneResponse("Bearer " + token,request,number);
        call.enqueue(new Callback<HotelPhoneResponse>() {
            @Override
            public void onResponse(Call<HotelPhoneResponse> call, Response<HotelPhoneResponse> response) {
                try{
                    if(response.isSuccessful()){
                        phoneList = phoneList.stream().map(phone->{
                            if(phone.getHotelNumber() ==number){
                                assert response.body() != null;
                                phone = response.body();
                            }
                             return phone;
                        }).collect(Collectors.toList());
                        onComplete.run();
                    }
                }catch (Exception e){
                    error = "Error: System could not update hotel phone";
                }
            }

            @Override
            public void onFailure(@NonNull Call<HotelPhoneResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void delete(String number,Runnable onComplete){
        Call<Boolean> call = api.deleteHotelPhoneResponse("Bearer " + token,number);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                try{
                    if(response.isSuccessful())  phoneList = phoneList.stream()
                            .filter(data -> !data.getHotelNumber().equals(number))
                            .collect(Collectors.toList());
                    onComplete.run();
                }catch (Exception e){
                    error = "Error: System couldn't delete phone.";
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

}
