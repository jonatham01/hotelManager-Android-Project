package hotel.manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

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


        userImageView =findViewById(R.id.userImage);
        userRoleView = findViewById(R.id.userRole);
        userNameView = findViewById(R.id.userName);
        //intent building
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //user data building
        UserCall userCall = new UserCall();
        if(bundle !=null) userCall.token = (String) bundle.get("tokenResult");

        userCall.getProfile(() -> {
            // Este bloque se ejecuta solo después de que la API devuelve la info del usuario
            runOnUiThread(() -> {

                userNameView.setText(userCall.user.getName());
                userRoleView.setText(userCall.user.getRole());
                // continuar con llamadas dependientes (hoteles, teléfonos, etc.)
            });
        });

        //hotel data building
        nameEdit = findViewById(R.id.editNombre);
        countryEdit = findViewById(R.id.paisEdit);
        stateEdit =findViewById(R.id.stateEdit);
        cityEdit =findViewById(R.id.cityEdit);
        addressEdit =findViewById(R.id.addressEdit);

        HotelCall hotelCall = new HotelCall(this);
        assert bundle != null;
        hotelCall.token = (String) bundle.get("tokenResult");
        hotelCall.findAll(() -> runOnUiThread(() -> {

            try{
                nameEdit.setText(hotelCall.hotels.get(0).getName());
                countryEdit.setText(hotelCall.hotels.get(0).getCountry());
                stateEdit.setText(hotelCall.hotels.get(0).getState());
                cityEdit.setText(hotelCall.hotels.get(0).getCity());
                addressEdit.setText(hotelCall.hotels.get(0).getAddress());
            }catch (Exception e){
                nameEdit.setText("No hotel found");
            }

        }));

        // hotel phone data building
        phoneListView = findViewById(R.id.phoneListView);
        HotelPhoneCall hotelPhoneCall = new HotelPhoneCall();
        hotelPhoneCall.token = (String) bundle.get("tokenResult");
        hotelPhoneCall.findAll(() -> {
            phonesAdapter adapter = new phonesAdapter(HotelActivity.this, hotelPhoneCall.phoneList, hotelPhoneCall);
            phoneListView.setAdapter(adapter);
        });



    }
}