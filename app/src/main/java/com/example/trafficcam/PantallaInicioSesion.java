package com.example.trafficcam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PantallaInicioSesion extends AppCompatActivity {

    private EditText NombreUsuario;
    private EditText Contraseña;
    private Button botonAceptar;

    private  Button botonRegistrarse;

    Repositorio rp = new Repositorio(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_inicio_sesion);

        NombreUsuario = findViewById(R.id.TextUsuarioSesion);
        Contraseña = findViewById(R.id.ContraseñaSesion);
        botonAceptar = findViewById(R.id.BtonLogin);
        botonRegistrarse = findViewById(R.id.btnRegister);

        botonAceptar.setOnClickListener(v -> {
            String username = NombreUsuario.getText().toString().trim();
            String password = Contraseña.getText().toString().trim();

            // Validar usuario en un hilo secundario
            new Thread(() -> {
                boolean usuarioValido = rp.comprobacionUsuario(username, password);

                // Actualizar la interfaz en el hilo principal
                runOnUiThread(() -> {
                    if (usuarioValido) {
                        // Iniciar nueva actividad
                        Intent intent = new Intent(PantallaInicioSesion.this, PantallaPrincipal.class);
                        startActivity(intent);
                    } else {
                        // Mostrar mensaje de error
                        Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });
        botonRegistrarse.setOnClickListener(v -> {

            Intent intent = new Intent(PantallaInicioSesion.this, PantallaRegistro.class);
            startActivity(intent);




        });


    }


}