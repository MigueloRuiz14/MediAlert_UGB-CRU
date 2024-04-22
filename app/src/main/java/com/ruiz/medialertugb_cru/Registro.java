package com.ruiz.medialertugb_cru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private EditText cajaNombres, cajaApellidos, cajaEdad, cajaGenero, cajaCarrera, cajaCorreo, cajaContrasena, cajaRepetirContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        Button btnRegis = findViewById(R.id.idIn);
        databaseHelper = new DatabaseHelper(this);

        cajaNombres = findViewById(R.id.idCajaNombres);
        cajaApellidos = findViewById(R.id.idCajaApellido);
        cajaEdad = findViewById(R.id.idCajaEdad);
        cajaGenero = findViewById(R.id.idCajaGener);
        cajaCarrera = findViewById(R.id.idCajaCarrera);
        cajaCorreo = findViewById(R.id.idCajaCorreoInsti);
        cajaContrasena = findViewById(R.id.idCajaContra);
        cajaRepetirContra = findViewById(R.id.idCajaRepetir); // Agrega el campo de repetir contraseña

        Button btnRegistro = findViewById(R.id.idBtnRegi);
        btnRegistro.setOnClickListener(view -> {
            // Verificar si todos los campos están llenos
            if (camposEstanLlenos()) {
                String nombres = cajaNombres.getText().toString();
                String apellidos = cajaApellidos.getText().toString();
                int edad = Integer.parseInt(cajaEdad.getText().toString());
                String genero = cajaGenero.getText().toString();
                String carrera = cajaCarrera.getText().toString();
                String correo = cajaCorreo.getText().toString();

                // Obtener las contraseñas y encriptarlas
                String contrasena = cajaContrasena.getText().toString();
                String contrasenaRepetida = cajaRepetirContra.getText().toString();

                if (correo.endsWith("@ugb.edu.sv")) {
                    // Verificar si las contraseñas coinciden
                    if (contrasena.equals(contrasenaRepetida)) {
                        // Las contraseñas coinciden, puedes guardar los datos en la base de datos
                        String contrasenaEncriptada = encriptarContrasena(contrasena);
                        long resultado = databaseHelper.insertarUsuario(nombres, apellidos, edad, genero, carrera, correo, contrasenaEncriptada);
                        if (resultado != -1) {
                            // Registro exitoso, redirigir al menú
                            Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registro.this, Menu.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Registro.this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Las contraseñas no coinciden
                        Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // El correo no es válido
                    Toast.makeText(Registro.this, "El correo debe terminar en @ugb.edu.sv", Toast.LENGTH_SHORT).show();
                }
            } else {
                // No todos los campos están llenos
                Toast.makeText(Registro.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
        // Configura el botón "INGRESAR"
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, Ingreso.class);
                startActivity(intent);
            }
        });
    }

    // Método para encriptar la contraseña
    private String encriptarContrasena(String contrasena) {
        return com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils.sha256Hex(contrasena);
    }

    // Método para verificar si todos los campos están llenos
    private boolean camposEstanLlenos() {
        return !cajaNombres.getText().toString().isEmpty() &&
                !cajaApellidos.getText().toString().isEmpty() &&
                !cajaEdad.getText().toString().isEmpty() &&
                !cajaGenero.getText().toString().isEmpty() &&
                !cajaCarrera.getText().toString().isEmpty() &&
                !cajaCorreo.getText().toString().isEmpty() &&
                !cajaContrasena.getText().toString().isEmpty() &&
                !cajaRepetirContra.getText().toString().isEmpty();
    }
}

