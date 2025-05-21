package hotel.manager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import hotel.manager.adapters.phonesAdapter;
import hotel.manager.calls.HotelCall;
import hotel.manager.calls.HotelPhoneCall;
import hotel.manager.calls.UserCall;
import hotel.manager.entities.HotelPhoneResponse;

public class HotelActivity extends AppCompatActivity {

    ListView phoneListView;
    ImageView userImageView;
    TextView userNameView, userRoleView;
    TextView nameEdit, countryEdit,stateEdit, cityEdit, addressEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //user data building
        UserCall userCall = new UserCall();
        userCall.token = "dodkjoa";
        userCall.getProfile();
        userImageView =findViewById(R.id.userImage);
        userRoleView = findViewById(R.id.userRole);
        userNameView = findViewById(R.id.userName);


        //hotel data building
        nameEdit = findViewById(R.id.editNombre);
        countryEdit = findViewById(R.id.paisEdit);

        HotelCall hotelCall = new HotelCall();
        hotelCall.token = userCall.token;
        hotelCall.findAll();
        nameEdit.setText(hotelCall.hotels.get(0).getName());
        countryEdit.setText(hotelCall.hotels.get(0).getCountry());
        stateEdit.setText(hotelCall.hotels.get(0).getState());
        cityEdit.setText(hotelCall.hotels.get(0).getCity());
        addressEdit.setText(hotelCall.hotels.get(0).getAddress());

        // hotel phone data building
        phoneListView = findViewById(R.id.phoneListView);
        HotelPhoneCall hotelPhoneCall = new HotelPhoneCall();
        hotelPhoneCall.token = userCall.token;
        hotelPhoneCall.findAll(() -> {
            phonesAdapter adapter = new phonesAdapter(this, hotelPhoneCall.phoneList, hotelPhoneCall);
            phoneListView.setAdapter(adapter);
        });

        phonesAdapter adapter = new phonesAdapter(this, hotelPhoneCall.phoneList, hotelPhoneCall);
        phoneListView.setAdapter(adapter);

    }
}