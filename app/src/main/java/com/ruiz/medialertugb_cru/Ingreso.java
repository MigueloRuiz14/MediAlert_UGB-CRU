package com.ruiz.medialertugb_cru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Ingreso extends AppCompatActivity {
    private EditText cajaCorreo, cajaContrasena;
    private DatabaseHelper databaseHelper;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso);

        Button btnIngresar = findViewById(R.id.idBtnIngresar);
        Button btnRegistrarse = findViewById(R.id.idBRegistro);

        cajaCorreo = findViewById(R.id.idCorreoIng);
        cajaContrasena = findViewById(R.id.idPassSegura);

        databaseHelper = new DatabaseHelper(this);

        // Configura el botón "Ingresar"
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = cajaCorreo.getText().toString();
                String contrasena = cajaContrasena.getText().toString();

                if (correo.isEmpty() || contrasena.isEmpty()) {
                    // Muestra un mensaje si no se completan los campos
                    Toast.makeText(Ingreso.this, "Completar los campos", Toast.LENGTH_SHORT).show();
                } else {
                    String contrasenaEncriptada = encriptarContrasena(contrasena);
                    if (autenticarUsuario(correo, contrasenaEncriptada)) {
                        // Si las credenciales son correctas, muestra un mensaje y redirige al menú
                        Toast.makeText(Ingreso.this, "Ingresaste correctamente", Toast.LENGTH_SHORT).show();

                        // Guardar la dirección de correo en las preferencias compartidas
                        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PREF_EMAIL, correo);
                        editor.apply();

                        Intent intent = new Intent(Ingreso.this, Menu.class);
                        startActivity(intent);
                    } else {
                        // Muestra un mensaje si las credenciales son incorrectas
                        Toast.makeText(Ingreso.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Configura el botón "Registrarse" te lleva de ingreso a registro
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ingreso.this, Registro.class);
                startActivity(intent);
            }
        });
    }

    // Método para encriptar la contraseña
    private String encriptarContrasena(String contrasena) {
        return com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils.sha256Hex(contrasena);
    }

    // Método para autenticar al usuario
    private boolean autenticarUsuario(String correo, String contrasena) {
        return databaseHelper.autenticarUsuario(correo, contrasena);
    }
}
