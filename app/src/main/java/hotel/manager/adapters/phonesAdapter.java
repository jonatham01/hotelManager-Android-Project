package hotel.manager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import hotel.manager.R;
import hotel.manager.calls.HotelPhoneCall;
import hotel.manager.entities.HotelPhone;
import hotel.manager.entities.HotelPhoneRequest;
import hotel.manager.entities.HotelPhoneResponse;

public class phonesAdapter  extends ArrayAdapter<HotelPhoneResponse> {

    private final HotelPhoneCall phoneCall;
    private final Context context;

    public phonesAdapter(Context context, List<HotelPhoneResponse> phones, HotelPhoneCall phoneCall) {
        super(context, 0, phones);
        this.phoneCall = phoneCall;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        HotelPhoneResponse phone = getItem(position);

        if(convertView ==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_phone, parent, false);
        }
        TextView name = convertView.findViewById(R.id.phone);
        Button editarBtn = convertView.findViewById(R.id.modificarButton);
        Button borrarBtn = convertView.findViewById(R.id.eliminarButton);

        assert phone != null;
        name.setText(phone.getHotelNumber());

        editarBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Editar número");

            final EditText input = new EditText(context);
            input.setText(phone.getHotelNumber());
            builder.setView(input);

            builder.setPositiveButton("Guardar", (dialog, which) -> {
                String newPhone = input.getText().toString();
                HotelPhone request = new HotelPhone();
                request.setNumber(newPhone);
                request.setId(phone.getId());
                phoneCall.update(request, phone.getHotelNumber(), () -> {
                    phone.setHotelNumber(newPhone);
                    notifyDataSetChanged();
                });
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        borrarBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Deseas borrar este número?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        phoneCall.delete(phone.getHotelNumber(), () -> {
                            remove(phone);
                            notifyDataSetChanged();
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });





        return convertView;
    }


}
