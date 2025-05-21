package hotel.manager.calls;

import java.util.List;
import java.util.stream.Collectors;

import hotel.manager.entities.HotelPhoneRequest;
import hotel.manager.entities.HotelPhoneResponse;
import hotel.manager.retrofitInterfaces.PhoneRetroInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelPhoneCall {
    private String URL ="http://localhost:8090/hotelphones";
    public List<HotelPhoneResponse> phoneList;
    public String res;
    PhoneRetroInterface api;
    String token;
    String error;

    public HotelPhoneCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(PhoneRetroInterface.class);
    }

    public void findAll(){
        Call<List<HotelPhoneResponse>> call = api.getAllHotelPhones(token);
        call.enqueue(new Callback<List<HotelPhoneResponse>>() {
            @Override
            public void onResponse(Call<List<HotelPhoneResponse>> call, Response<List<HotelPhoneResponse>> response) {
                try{
                    if(response.isSuccessful()) phoneList = response.body();
                }catch (Exception e){
                    error = "System could find any phone";
                }
            }

            @Override
            public void onFailure(Call<List<HotelPhoneResponse>> call, Throwable t) {

            }
        });
    }

    public void create(HotelPhoneRequest request){
        Call<HotelPhoneResponse> call = api.createNewHotelPhoneResponse(this.token, request);
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

    public void update(HotelPhoneRequest request, Short id){
        Call<HotelPhoneResponse> call = api.updateHotelPhoneResponse(token,request,id);
        call.enqueue(new Callback<HotelPhoneResponse>() {
            @Override
            public void onResponse(Call<HotelPhoneResponse> call, Response<HotelPhoneResponse> response) {
                try{
                    if(response.isSuccessful()){
                        phoneList = phoneList.stream().map(phone->{
                            if(phone.getId() ==id){
                                assert response.body() != null;
                                phone = response.body();
                            }
                             return phone;
                        }).collect(Collectors.toList()); ;
                    }
                }catch (Exception e){
                    error = "Error: System could not update hotel phone";
                }
            }

            @Override
            public void onFailure(Call<HotelPhoneResponse> call, Throwable t) {

            }
        });
    }

    public void delete(Short id){
        Call<Boolean> call = api.deleteHotelPhoneResponse(token,id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                try{
                    if(response.isSuccessful()) phoneList.stream().filter(data->data.getId() ==id ).collect(Collectors.toList());
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
