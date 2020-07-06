package kalitero.software.noticiasargentinas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.jetbrains.annotations.NotNull;
import kalitero.software.noticiasargentinas.Controlador.Repositorio;
import kalitero.software.noticiasargentinas.Modelo.PaqueteNoticias;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class SplashActivity extends AppCompatActivity {

    private Animation animationGrupo;
    private Animation animarionElipse;
    private ImageView elipse;
    private ImageView grupo;
    private TextView noticiasArgentinas;
    private PaqueteNoticias paqueteNoticias;
    private Boolean datosListos = false;
    private String TAG = getClass().toString();
    private Boolean finPresentacion = false;
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        elipse = findViewById(R.id.imageView2);
        grupo = findViewById(R.id.imageView);
        noticiasArgentinas = findViewById(R.id.textViewTitulo);
        repositorio = Repositorio.getInstancia(this);

        paqueteNoticias = new PaqueteNoticias();
        repositorio.traerTodo(new ResultListener<PaqueteNoticias>() {
            @Override
            public void onFinish(PaqueteNoticias result) {
                paqueteNoticias = result;
                datosListos = true;
                if (finPresentacion) {
                    pasarAMainActivity();
                }
            }
            @Override
            public void onError(@NotNull String message) {

            }
        });


        rotarAnimacion();
        escalaAnimacion();
        YoYo.with(Techniques.FadeIn).duration(2000).playOn(noticiasArgentinas);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                finPresentacion = true;
                if (datosListos) {
                    pasarAMainActivity();
                } else {
                    Toast.makeText(SplashActivity.this, "Esperando datos... ", Toast.LENGTH_LONG).show();
                }
            }
        }, 2500);
    }

    private void pasarAMainActivity() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PaqueteNoticias.class.toString(), paqueteNoticias);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void rotarAnimacion() {
        animationGrupo = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animationGrupo.setDuration(2500);
        grupo.startAnimation(animationGrupo);
    }

    private void escalaAnimacion() {
        animarionElipse = AnimationUtils.loadAnimation(this, R.anim.scale);
        animarionElipse.setDuration(1500);
        elipse.startAnimation(animarionElipse);
    }

}


