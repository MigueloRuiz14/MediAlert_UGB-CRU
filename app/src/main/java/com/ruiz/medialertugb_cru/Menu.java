package com.ruiz.medialertugb_cru;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;


public class Menu extends AppCompatActivity {

    // Constantes para la gestión de preferencias y permisos de ubicación
    static final String PREFS_NAME = "MyPrefsFile";
    static final String PREF_EMAIL = "email";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Administrador de ubicación y coordenadas
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private boolean isLocationUpdatesRequested = false;
    private boolean quebraduraSelected = false;
    private boolean sMentalSelected = false;
    private boolean paroSelected = false;
    private boolean esguincesSelected = false;
    private boolean resfriadoSelected = false;
    private boolean fiebreSelected = false;
    private boolean alergiaSelected = false;
    private boolean dolorestomagoSelected = false;
    private boolean dolorCabezaSelected = false;
    private boolean desmayoSelected = false;

    private String obtenerInformacionEnfermedad(int opcion) {
        String informacion = "";

        switch (opcion) {
            case 1: // Opción para la fractura
                informacion = "FRACTURA: \n La fractura es una lesión en la que uno o más huesos se rompen o fracturan. Puede causar dolor, hinchazón y dificultad para mover la parte afectada. Es importante buscar atención médica.";
                break;
            case 2: // Opción para problemas mentales
                informacion = "SALUD MENTAL: \n Se refiere a un estado de salud mental en el que una persona experimenta un deterioro significativo en su bienestar emocional y psicológico. Puede manifestarse de diferentes maneras y tener diversas causas.";
                break;
            case 3: // Opción para paro cardiaco
                informacion = "PARO CARDÍACO: \n Un ataque cardíaco, o infarto de miocardio, ocurre cuando el flujo sanguíneo al corazón se bloquea, generalmente debido a la acumulación de placas de grasa en las arterias coronarias. Esto daña el músculo cardíaco y puede ser mortal. Los síntomas incluyen dolor en el pecho, dificultad para respirar y sudoración excesiva. Buscar atención médica de inmediato es crucial para prevenir daños graves al corazón.";
                break;
            case 4: // Opción para esguince
                informacion = "ESGUINCE: \n Un esguince es una lesión común que afecta los ligamentos que conectan los huesos. Generalmente, ocurre cuando se estira o desgarra un ligamento debido a una torcedura o movimiento brusco. Los síntomas típicos incluyen dolor, hinchazón y dificultad para mover la articulación afectada. El tratamiento a menudo implica descanso, aplicación de hielo y fisioterapia. Es importante seguir las recomendaciones médicas para una recuperación completa y prevenir lesiones crónicas.";
                break;
            case 5: // Opción para resfriado o gripe
                informacion = "RESFRIADO O GRIPE: \n El resfriado y la gripe son enfermedades respiratorias contagiosas. El resfriado generalmente causa síntomas leves, como congestión nasal y dolor de garganta, mientras que la gripe provoca síntomas más graves, como fiebre alta, dolores musculares y fatiga. Ambas son causadas por virus y se transmiten fácilmente de persona a persona. El reposo, la hidratación y los medicamentos pueden aliviar los síntomas. La prevención a través de la vacunación y una buena higiene, como lavarse las manos con frecuencia, son medidas importantes para evitar su propagación.";
                break;
            case 6: // Opción para fiebre
                informacion = "FIEBRE: \n La fiebre es un síntoma común que indica un aumento en la temperatura corporal. Generalmente, se considera fiebre cuando la temperatura corporal supera los 38 grados Celsius (100.4 grados Fahrenheit). Puede ser causada por infecciones, inflamaciones u otras condiciones médicas. La fiebre es una respuesta del cuerpo para combatir infecciones y estimular el sistema inmunológico. A menudo, se acompaña de otros síntomas, como escalofríos, sudoración y malestar. El tratamiento puede incluir medicamentos para reducir la fiebre, mantenerse bien hidratado y descansar. Si la fiebre es alta o persistente, se debe buscar atención médica.";
                break;
            case 7: // Opción para alergia
                informacion = "ALERGIA: \n Una alergia es una reacción inmunológica anormal a sustancias inofensivas, como polen, alimentos o polvo. Cuando una persona alérgica entra en contacto con el desencadenante, su sistema inmunológico reacciona de manera exagerada, causando síntomas como estornudos, picazón, erupciones cutáneas y dificultad para respirar. El tratamiento implica evitar los desencadenantes y, en algunos casos, tomar antihistamínicos.";
                break;
            case 8: // Opción para dolor de estomago o vomito
                informacion = "DOLOR DE ESTÓMAGO O VÓMITO: \n El dolor de estómago o vómito, también conocido como gastritis, es una afección en la que la mucosa del estómago se inflama. Puede ser causado por infecciones, estrés, consumo excesivo de alcohol o alimentos irritantes. Los síntomas incluyen dolor abdominal, acidez estomacal, náuseas y vómitos. El tratamiento suele involucrar cambios en la dieta, medicamentos y reposo.";
                break;
            case 9: // Opción para dolor de cabeza
                informacion = "DOLOR DE CABEZA: \n El dolor de cabeza, o cefalea, es una molestia o dolor en la cabeza o en la parte superior del cuello. Puede ser causado por diversos factores, como tensión, migrañas, sinusitis, o incluso estrés. Los síntomas incluyen dolor punzante, palpitante o constante en la cabeza. El tratamiento varía según la causa y puede incluir descanso, analgésicos o medicamentos específicos para las migrañas.";
                break;
            case 10: // Opción para desmayo
                informacion = "DESMAYO: \n Un desmayo, también conocido como síncope, es la pérdida temporal de la conciencia y de la capacidad de mantenerse en pie. Esto suele ser causado por una disminución temporal del flujo sanguíneo al cerebro, que puede ser provocado por una variedad de razones, como estrés, deshidratación, bajo nivel de azúcar en sangre, o trastornos cardíacos. Los desmayos suelen ser de corta duración y la persona recupera la conciencia espontáneamente. En casos recurrentes o inexplicables, se debe buscar atención médica.";
                break;
            default:
                informacion = "Información no disponible";
                break;
        }

        return informacion;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Verificar si ya se ha solicitado y otorgado el permiso de ubicación
        boolean hasLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // Si no se ha otorgado el permiso previamente, solicitarlo
        if (!hasLocationPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            setupLocationManager();
        }

        // Recupera el correo del usuario desde las preferencias
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userEmail = sharedPreferences.getString(PREF_EMAIL, null);

        // Función para cerrar sesión
        Button btnCerrarSesion = findViewById(R.id.idCerrarSesión);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Elimina las preferencias al cerrar sesión
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(PREF_EMAIL);
                editor.apply();

                // Redirige a la pantalla de inicio de sesión
                Intent intent = new Intent(Menu.this, Ingreso.class);
                startActivity(intent);
                finish();
            }
        });

        // Botones de alertas médicas (utilizaremos ImageButton)
        // Configura ImageButton y su manejador de clics para mostrar información sobre cada alerta al hacer clic.
        // Esto se repite para varias alertas médicas utilizando ImageButton en lugar de CheckBox.

        //ImageButton para la fractura
        ImageButton quebraduraButton = findViewById(R.id.IdGUno);
        quebraduraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(1); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para problemas mentales
        ImageButton sMentalButton = findViewById(R.id.IdGDos);
        sMentalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(2); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para paro cardiaco
        ImageButton paroButton = findViewById(R.id.IdGTres);
        paroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(3); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para esguince
        ImageButton esguincesButton = findViewById(R.id.IdMUno);
        esguincesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(4); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para gripe
        ImageButton resfriadoButton = findViewById(R.id.IdMDos);
        resfriadoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(5); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para fiebre
        ImageButton fiebreButton = findViewById(R.id.IdMTres);
        fiebreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(6); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para la Alergia
        ImageButton alergiaButton = findViewById(R.id.IdMCuatro);
        alergiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(7); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para dolor de estomago o vomito
        ImageButton dolorestomagoButton = findViewById(R.id.IdLUno);
        dolorestomagoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(8); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para dolor de cabeza
        ImageButton dolorCabezaButton = findViewById(R.id.IdLDos);
        dolorCabezaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(9); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });
        //ImageButton para desmayo
        ImageButton desmayoButton = findViewById(R.id.IdLTres);
        desmayoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String informacion = obtenerInformacionEnfermedad(10); // Llama a obtenerInformacionEnfermedad con el número correspondiente a esta enfermedad
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("enfermedad_seleccionada", informacion); // Envía la información al MainActivity
                startActivity(intent);
            }
        });


        // Botón "Enviar Alerta"
        Button enviarAlertaButton = findViewById(R.id.IdEnviarAlerta);
        enviarAlertaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userEmail != null) {
                    // Verificar si al menos un ImageButton se ha presionado
                    boolean anyButtonSelected = quebraduraSelected || sMentalSelected || paroSelected || esguincesSelected ||
                            resfriadoSelected || fiebreSelected || alergiaSelected || dolorestomagoSelected ||
                            dolorCabezaSelected || desmayoSelected;

                    if (anyButtonSelected) {
                        // Construir un mensaje con la información seleccionada y las coordenadas de ubicación
                        StringBuilder mensaje = new StringBuilder("Usuario: " + userEmail + "\n");
                        if (quebraduraSelected) {
                            mensaje.append("Enfermedad: Quebradura\n");
                        }
                        if (sMentalSelected) {
                            mensaje.append("Enfermedad: Salud Mental\n");
                        }
                        if (paroSelected) {
                            mensaje.append("Enfermedad: Paro Cardiaco\n");
                        }
                        if (esguincesSelected) {
                            mensaje.append("Enfermedad: Esguinces\n");
                        }
                        if (resfriadoSelected) {
                            mensaje.append("Enfermedad: Resfriado o gripe\n");
                        }
                        if (fiebreSelected) {
                            mensaje.append("Enfermedad: Fiebre\n");
                        }
                        if (alergiaSelected) {
                            mensaje.append("Enfermedad: Alergia\n");
                        }
                        if (dolorestomagoSelected) {
                            mensaje.append("Enfermedad: Dolor de estómago o vómito\n");
                        }
                        if (dolorCabezaSelected) {
                            mensaje.append("Enfermedad: Dolor de cabeza\n");
                        }
                        if (desmayoSelected) {
                            mensaje.append("Enfermedad: Desmayo\n");
                        }
                        Intent intent = new Intent(Menu.this, MainActivity.class);
                        intent.putExtra("enfermedad_seleccionada", "Información sobre la enfermedad seleccionada");
                        startActivity(intent);


                        mensaje.append("Ubicación: Latitud ").append(latitude).append(", Longitud ").append(longitude);

                        // Llamar a la función para enviar el correo
                        enviarCorreo("edinamita99@gmail.com", "Alerta de Salud", mensaje.toString());
                    } else {
                        Toast.makeText(Menu.this, "Debes seleccionar al menos una alerta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Menu.this, "No se pudo obtener la dirección de correo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Función para enviar el correo
    private void enviarCorreo(String destinatario, String asunto, String mensaje) {
        // Configura las propiedades del servidor de correo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        // Dirección de correo y contraseña de tu cuenta de correo
        final String correoUsuario = "pusuario952@gmail.com";
        final String contrasena = "hohk urfc kkhz gkrk";

        // Crea una sesión de correo con autenticación
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoUsuario, contrasena);
            }
        });
    }

    // Función para mostrar información sobre una enfermedad en un cuadro de diálogo
    private void mostrarInformacionEnfermedad(String informacion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Información sobre la enfermedad");
        builder.setMessage(informacion);
        builder.setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Configura el administrador de ubicación para obtener actualizaciones de ubicación
    private void setupLocationManager() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                // Dentro del método onLocationChanged
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("latitude", (float) latitude);
                editor.putFloat("longitude", (float) longitude);
                editor.apply();

                // Actualiza los TextView con la latitud y longitud
                updateLocationTextViews(latitude, longitude);

                // Llama a la función para enviar el correo cuando se obtiene la ubicación
                enviarCorreo("edinamita99@gmail.com", "Alerta de Salud", "Información sobre la problema médico");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        isLocationUpdatesRequested = true;
    }

    // Actualiza los TextView con las coordenadas de latitud y longitud
    private void updateLocationTextViews(double latitude, double longitude) {
        // Actualiza los TextView con la latitud y longitud
        TextView latitudTextView = findViewById(R.id.IdLatitud);
        TextView longitudTextView = findViewById(R.id.IdLongitud);

        latitudTextView.setText("Latitud: " + latitude);
        longitudTextView.setText("Longitud: " + longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupLocationManager();
            } else {
                // No se otorgaron permisos de ubicación
                isLocationUpdatesRequested = false;
            }
        }
    }
}
