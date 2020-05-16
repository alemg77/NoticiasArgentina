package kalitero.software.noticiasargentinas.Vista;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

import kalitero.software.noticiasargentinas.Controlador.BuscarNoticias;
import kalitero.software.noticiasargentinas.Controlador.RecepcionNoticias;
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecepcionNoticias, FragmentListaNoticiasCompacto.Aviso {


    // Para ver los logos hay que filtrar con: kalitero.software.noticiasargentinas.Vista
    private String TAG = getClass().toString();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.activityMainNavigationView);
        drawerLayout = findViewById(R.id.activityMainDrawerLayout);

        Log.d(TAG, "*************** Inicio del programa Noticias Argentinas ********************************");

        // Pido noticias para tener algo que mostrar antes de empezar
        final BuscarNoticias buscarNoticias = new BuscarNoticias(MainActivity.this);
        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigationMenuUltimasNoticias:
                        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA);
                        break;

                    case R.id.navigationMenuCiencia:
                        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_CIENCIA);
                        break;

                    case R.id.navigationMenuEntretenimiento:
                        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_ENTRETENIMIENTO);
                        break;

                    case R.id.navigationMenuSalud:
                        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_SALUD);
                        break;

                    case R.id.navigationMenuNegocios:
                        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_NEGOCIOS);
                        break;

                    case R.id.navigationMenuDeportes:
                        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_DEPORTES);
                        break;

                    case R.id.navigationMenuTecnologia:
                        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_TECNOLOGIA);
                        break;

                    case R.id.navigationMenuNoticiasBarriales:
                        Toast.makeText(MainActivity.this, "Noticias Barriales", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(MainActivity.this, "En Construccion", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }


    /**
     * Funcion para pegar un fragment enviandole un objeto serializable     *
     *
     * @param fragmentAPegar  :Fragment a pegar
     * @param containerViewId :Donde lo queres pegar
     * @param serializable    :Que objeto le vas a pasar
     */
    private void pegarFragment(Fragment fragmentAPegar, int containerViewId, Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(serializable.getClass().toString(), serializable);
        fragmentAPegar.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(containerViewId, fragmentAPegar).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void llegoPaqueteDeNoticias(ListaNoticias listaNoticias) {
        Log.d(TAG, "Llego un paquete de noticias");
        pegarFragment(new FragmentListaNoticiasCompacto(), R.id.activityMainContenedorFragment, listaNoticias);
    }

    @Override
    public void errorPedidoNoticia() {
    }


    @Override
    public void selleccion(Noticia noticia) {
    }
}
