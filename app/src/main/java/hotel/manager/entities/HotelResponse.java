package hotel.manager.entities;

import java.util.List;

public class HotelResponse {
    private Integer id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private List<HotelPhoneResponse> hotelPhones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<HotelPhoneResponse> getHotelPhones() {
        return hotelPhones;
    }

    public void setHotelPhones(List<HotelPhoneResponse> hotelPhones) {
        this.hotelPhones = hotelPhones;
    }
}
