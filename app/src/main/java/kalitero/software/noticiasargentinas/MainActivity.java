package kalitero.software.noticiasargentinas;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;


import java.io.Serializable;

import kalitero.software.noticiasargentinas.Controlador.BuscarNoticias;
import kalitero.software.noticiasargentinas.Controlador.RecepcionNoticias;
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.PaqueteNoticias;
import kalitero.software.noticiasargentinas.Vista.Fragment.FragmentListaNoticiasCompacto;
import kalitero.software.noticiasargentinas.Vista.Fragment.FragmentLogin;
import kalitero.software.noticiasargentinas.Vista.ViewPager.ViewPagerListasNoticias;
import kalitero.software.noticiasargentinas.Vista.ViewPager.ViewPagerNoticia;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecepcionNoticias, FragmentListaNoticiasCompacto.Aviso, ViewPagerListasNoticias.SelleccionDos {

    // Para ver los logos hay que filtrar con: kalitero.software.noticiasargentinas.Vista
    private String TAG = getClass().toString();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private PaqueteNoticias paqueteNoticias;
    private BuscarNoticias buscarNoticias;
    private ListaNoticias listaNoticias; // La ultima lista de noticias que envie al Fragmente que tiene el Recycler view

    // TODO: Faltan estos logos:
    /*
        Ambito.com
        Pagina12.com.ar
        Perfil.com
        Cienradios.com
        ElTerritorio.com.ar
        Depo.com.ar
        Motorsport.com
        Rosario3.com
        Tn.com.ar
        Cadena3.com
        Elintransigente.com
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "*************** Inicio del programa Noticias Argentinas ********************************");
        navigationView = findViewById(R.id.activityMainNavigationView);
        drawerLayout = findViewById(R.id.activityMainDrawerLayout);

        Toolbar toolbar = findViewById(R.id.MainActivityToolbar);
        setSupportActionBar(toolbar);

        // Pido noticias para tener algo que mostrar antes de empezar
        buscarNoticias = new BuscarNoticias(MainActivity.this);
        buscarNoticias();


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
        fragmentTransaction.add(containerViewId, fragmentAPegar).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_Item1:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.actionbar_usuario:
                getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.activityMainContenedorFragment, new FragmentLogin()).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private Integer pedidos_api;

    void buscarNoticias (){
        pedidos_api = 0;
        paqueteNoticias =  new PaqueteNoticias();
        buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA);
    }

    @Override
    public void llegoPaqueteDeNoticias(ListaNoticias listaNoticias) {
        Log.d(TAG, "Llego un paquete de noticias");
        this.listaNoticias = listaNoticias;
        paqueteNoticias.agregarListaNoticias(listaNoticias);
        pedidos_api++;
        switch ( pedidos_api ){
            case 1:
                buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_CIENCIA);
                break;

            case 2:
                buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_ENTRETENIMIENTO);
                break;

            case 3:
                buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_SALUD);
                break;

            case 4:
                buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_NEGOCIOS);
                break;

            case 5:
                buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_DEPORTES);
                break;

            case 6:
                buscarNoticias.titularesNuevos(BuscarNoticias.KEY_PAIS_ARGENTINA, BuscarNoticias.KEY_TEMA_TECNOLOGIA);
                break;

            default:
                // TODO:  Enviar paquete de noticias en vez de lista de noticas
                pegarFragment(new ViewPagerListasNoticias(), R.id.activityMainContenedorFragment, paqueteNoticias);
                break;

        }
    }





    @Override
    public void errorPedidoNoticia() {
        // TODO: Ver que hacemos cuando hay un problema en la coneccion con la API
    }

    @Override
    public void selleccion(ListaNoticias listaNoticias) {
        Log.d(TAG, "Selecciono una noticia");
        pegarFragment(new ViewPagerNoticia(), R.id.activityMainContenedorFragment, listaNoticias);

        /*
        //Toast.makeText(this, noticia.getDescripcion(),Toast.LENGTH_LONG).show();
        Intent unItent = new Intent(this, DetalleNoticiasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentDetalleNoticias.NOTICIA,noticia);
        unItent.putExtras(bundle);
        startActivity(unItent);
         */

    }

    @Override
    public void sellecionDos(ListaNoticias listaNoticias) {
        Log.d(TAG,"Selecciono la noticia");
    }
}
