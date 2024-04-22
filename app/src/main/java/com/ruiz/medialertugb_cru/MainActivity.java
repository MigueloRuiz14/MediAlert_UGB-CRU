package com.ruiz.medialertugb_cru;

import static com.ruiz.medialertugb_cru.Menu.PREFS_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {

    String correo;
    String contraseña;
    EditText mensaje;
    Button enviar;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText ubicacionEditText = findViewById(R.id.ubi);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat("latitude", 0.0f);
        float longitude = sharedPreferences.getFloat("longitude", 0.0f);

        String coordenadas = String.format("%.8f, %8f", latitude, longitude);
        ubicacionEditText.setText(coordenadas);

        // Función para cancelar
        Button btnCancelar = findViewById(R.id.idCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }
        });

        mensaje = findViewById(R.id.mensaje);
        enviar = findViewById(R.id.enviar);

        correo = "pusuario952@gmail.com";
        contraseña = "xbvl yfsx ionw gefa";

        Intent intent = getIntent();
        if (intent != null) {
            String informacionEnfermedad = intent.getStringExtra("enfermedad_seleccionada");

            // Establecer la información de la enfermedad y la ubicación en el EditText
            mensaje.setText("Información de la enfermedad:\n" + informacionEnfermedad + "\n\nUbicación:\n" + coordenadas);
        }

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.googlemail.com");
                properties.put("mail.smtp.socketFactory.port", "465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.port", "465");

                try {
                    session = Session.getDefaultInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correo, contraseña);
                        }
                    });
                    if (session != null) {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(correo));
                        message.setSubject("Asunto del correo");
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("edinamita99@gmail.com"));
                        message.setContent(mensaje.getText().toString(), "text/html; charset=utf-8");
                        Transport.send(message);

                        // Mostrar mensaje de éxito y redirigir a Ingreso.java
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Su alerta ha sido enviada exitosamente, pronto será atendido", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, Ingreso.class);
                                startActivity(intent);
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
