package hotel.manager.entities;

public class HotelPhoneResponse {
    private Short id;
    private String hotelNumber;
    private String hotelName;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getHotelNumber() {
        return hotelNumber;
    }

    public void setHotelNumber(String hotelNumber) {
        this.hotelNumber = hotelNumber;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}
