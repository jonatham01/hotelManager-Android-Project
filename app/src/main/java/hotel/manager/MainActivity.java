package hotel.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import hotel.manager.callbacks.LoginCallback;
import hotel.manager.calls.UserCall;
import hotel.manager.entities.UserLogin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText user,password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = findViewById(R.id.user_edit);
        password = findViewById(R.id.password_edit);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(MainActivity.this, "intento", Toast.LENGTH_SHORT).show();
        int id = view.getId();
        if(id == R.id.button){
            UserLogin userLogin = new UserLogin(
                    user.getText().toString(),
                    password.getText().toString()
            );
            UserCall call = new UserCall();
            call.loginUser(userLogin, new LoginCallback() {
                @Override
                public void onSuccess(String token) {
                    runOnUiThread(()->{
                        Intent intent = new Intent(MainActivity.this,HotelActivity.class);
                        intent.putExtra("tokenResult",token);
                        startActivity(intent);
                    });
                }

                @Override
                public void onError(String message) {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    });
                }
            });

        }//id button condition is ended
    }
}