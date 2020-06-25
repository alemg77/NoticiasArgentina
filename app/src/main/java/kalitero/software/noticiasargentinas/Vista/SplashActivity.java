package kalitero.software.noticiasargentinas.Vista;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.BuscarNoticiasAPI;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoRoom;
import kalitero.software.noticiasargentinas.Controlador.RecepcionNoticias;
import kalitero.software.noticiasargentinas.MainActivity;
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.Modelo.PaqueteNoticias;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.util.AppDatabase;

public class SplashActivity extends AppCompatActivity implements RecepcionNoticias {

    private Animation animationGrupo;
    private Animation animarionElipse;
    private ImageView elipse;
    private ImageView grupo;
    private TextView noticiasArgentinas;
    private BuscarNoticiasAPI buscarNoticias;
    private PaqueteNoticias paqueteNoticias;
    private Integer pedidosApi;
    private String TAG = getClass().toString();
    private Boolean fin_presentacion = false;
    private NoticiaDaoRoom noticiaDaoRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        elipse = findViewById(R.id.imageView2);
        grupo = findViewById(R.id.imageView);
        noticiasArgentinas = findViewById(R.id.textViewTitulo);
        this.noticiaDaoRoom = AppDatabase.getInstance(getApplicationContext()).noticiaDaoRoom();


        paqueteNoticias = new PaqueteNoticias();

            buscarNoticias = new BuscarNoticiasAPI(SplashActivity.this, this);
            pedidosApi = 0;
            buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA);


        rotarAnimacion();
        escalaAnimacion();
        YoYo.with(Techniques.FadeIn).duration(2000).playOn(noticiasArgentinas);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                fin_presentacion = true;
                if (pedidosApi > 6) {
                    pasarAMainActivity();
                }
            }
        }, 2500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if ( pedidosApi <= 3) {
                    Toast.makeText(SplashActivity.this, "Esperando datos... ", Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);
    }

    private void pasarAMainActivity() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PaqueteNoticias.class.toString(), paqueteNoticias);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void rotarAnimacion(){
        animationGrupo = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animationGrupo.setDuration(2500);
        grupo.startAnimation(animationGrupo);
    }

    private  void escalaAnimacion(){
        animarionElipse= AnimationUtils.loadAnimation(this, R.anim.scale);
        animarionElipse.setDuration(1500);
        elipse.startAnimation(animarionElipse);
    }

    @Override
    public void llegoPaqueteDeNoticias(ListaNoticias listaNoticias) {
        Log.d(TAG, "Llego un paquete de noticias");
        noticiaDaoRoom.insertAll((List<ListaNoticias>) listaNoticias);
        paqueteNoticias.agregarListaNoticias(listaNoticias);

        pedidosApi++;
        switch (pedidosApi) {
            case 1:
                buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_CIENCIA);
                break;

            case 2:
                buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_ENTRETENIMIENTO);
                break;

            case 3:
                buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_SALUD);
                break;

            case 4:
                buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_NEGOCIOS);
                break;

            case 5:
                buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_DEPORTES);
                break;

            case 6:
                buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_TECNOLOGIA);
                break;

            case 7:
                if (fin_presentacion) {
                    pasarAMainActivity();
                }
        }
    }

    @Override
    public void errorPedidoNoticia() {

    }

    public boolean hayInternet () {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


