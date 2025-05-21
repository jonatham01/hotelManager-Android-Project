package hotel.manager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import hotel.manager.R;
import hotel.manager.entities.HotelPhoneResponse;

public class phonesAdapter  extends ArrayAdapter<HotelPhoneResponse> {

    public phonesAdapter(Context context, List<HotelPhoneResponse> phones) {
        super(context, 0, phones);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent){

        HotelPhoneResponse phone = getItem(position);

        if(convertView ==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_phone, parent, false);
        }
        TextView name = convertView.findViewById(R.id.phone);

        assert phone != null;
        name.setText(phone.getHotelNumber());
        return convertView;
    }


}
