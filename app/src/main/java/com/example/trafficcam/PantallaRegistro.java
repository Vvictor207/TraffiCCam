package com.example.trafficcam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PantallaRegistro extends AppCompatActivity {

    private EditText inicionSesion;
    private EditText contraseña;
    private  EditText contraseña2;

    private Button botonRegistro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.PantallaRegistro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicionSesion = (EditText)  findViewById(R.id.Usuarioregistro);

        contraseña = (EditText) findViewById(R.id.Contraseña);
        contraseña2 = (EditText)  findViewById(R.id.ContraseñaVerificacion);

        botonRegistro =  ( Button) findViewById(R.id.btnRegister);


        botonRegistro.setOnClickListener(v -> {

            String username = inicionSesion.getText().toString().trim();
            String password = contraseña.getText().toString().trim();
            String password2 = contraseña2.getText().toString().trim();
            if(password.equals(password2)){


                Intent intent = new Intent(PantallaRegistro.this, PantallaInicioSesion.class);
                startActivity(intent);



            }
            else {

                Toast.makeText(getApplicationContext(), "La contraseña no coinciden", Toast.LENGTH_SHORT).show();
            }


        });



    }
}