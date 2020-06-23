package kalitero.software.noticiasargentinas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

import kalitero.software.noticiasargentinas.Controlador.BuscarNoticiasAPI;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Controlador.RecepcionNoticias;
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.Modelo.PaqueteNoticias;
import kalitero.software.noticiasargentinas.Vista.Login.FragmentVerUsuario;
import kalitero.software.noticiasargentinas.Vista.MostrarNoticias.FragmentListaNoticiasCompacto;
import kalitero.software.noticiasargentinas.Vista.Login.FragmentLogin;
import kalitero.software.noticiasargentinas.Vista.Regresar;
import kalitero.software.noticiasargentinas.Vista.SubirNoticias.SubirNoticias;
import kalitero.software.noticiasargentinas.Vista.MostrarNoticias.ViewPager.ViewPagerListasNoticias;
import kalitero.software.noticiasargentinas.Vista.MostrarNoticias.ViewPager.ViewPagerNoticia;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecepcionNoticias, FragmentListaNoticiasCompacto.Aviso, ViewPagerListasNoticias.SelleccionDos, Regresar {

    // Para ver los logos hay que filtrar con: kalitero.software.noticiasargentinas.Vista
    private String TAG = getClass().toString();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private PaqueteNoticias paqueteNoticias;
    private BuscarNoticiasAPI buscarNoticias;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentLogin fragmentLogin;

    // TODO: Faltan estos logos:

    /**********************
     Ambito.com
     Pagina12.com.ar
     Perfil.com
     Cienradios.com
     ElTerritorio.com.ar
     Depo.com.ar
     Motorsport.com
     Rosario3.com
     Tn.com.ar
     Cadena3.com-
     Elintransigente.com
     */

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "*************** Inicio del Main programa Noticias Argentinas ********************************");
        navigationView = findViewById(R.id.activityMainNavigationView);
        drawerLayout = findViewById(R.id.activityMainDrawerLayout);

        Toolbar toolbar = findViewById(R.id.MainActivityToolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abrir_menu, R.string.cerrar_menu);

        buscarNoticias = new BuscarNoticiasAPI(MainActivity.this);
        fragmentLogin = new FragmentLogin();

        if (savedInstanceState == null) {
            // Si llega aca, es la primera vez que se carga la actividad
            Bundle bundle = this.getIntent().getExtras();
            paqueteNoticias = (PaqueteNoticias) bundle.getSerializable(PaqueteNoticias.class.toString());
            pegarFragment(new ViewPagerListasNoticias(), R.id.activityMainContenedorFragment, paqueteNoticias);
        } else {
            Log.d(TAG, "Otra vez YO");
        }


        FloatingActionButton fab = findViewById(R.id.floating_action_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    startActivity(new Intent(MainActivity.this, SubirNoticias.class));
                } else {
                    pegarFragment(fragmentLogin, R.id.activityMainContenedorFragment);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigationMenuUltimasNoticias:
                        buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA);
                        break;

                    case R.id.navigationMenuCiencia:
                        buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_CIENCIA);
                        break;

                    case R.id.navigationMenuEntretenimiento:
                        buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_ENTRETENIMIENTO);
                        break;

                    case R.id.navigationMenuSalud:
                        buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_SALUD);
                        break;

                    case R.id.navigationMenuNegocios:
                        buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_NEGOCIOS);
                        break;

                    case R.id.navigationMenuDeportes:
                        buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_DEPORTES);
                        break;

                    case R.id.navigationMenuTecnologia:
                        buscarNoticias.titularesNuevos(BuscarNoticiasAPI.KEY_PAIS_ARGENTINA, BuscarNoticiasAPI.KEY_TEMA_TECNOLOGIA);
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

    /*****************
     * Funcion para pegar un fragment enviandole un objeto serializable
     *
     * @param fragmentAPegar  :Fragment a pegar
     * @param containerViewId :Donde lo queres pegar
     * @param serializable    :Que objeto le vas a pasar
     **********/
    private void pegarFragment(Fragment fragmentAPegar, int containerViewId, Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(serializable.getClass().toString(), serializable);
        fragmentAPegar.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(containerViewId, fragmentAPegar).commit();
    }

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(containerViewId, fragmentAPegar).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar aqui...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                paqueteNoticias = new PaqueteNoticias();
                buscarNoticias.porTema(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                paqueteNoticias = new PaqueteNoticias();
                buscarNoticias.porTema(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_usuario:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    pegarFragment(new FragmentVerUsuario(), R.id.activityMainContenedorFragment);

                } else {
                    pegarFragment(new FragmentLogin(), R.id.activityMainContenedorFragment);
                }
                break;

            case R.id.action_firebaseLeer:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    NoticiaDaoFirebase.Companion.getIntancia().buscarNoticias(new ResultListener<ListaNoticias>() {
                        @Override
                        public void onFinish(@NotNull ListaNoticias result) {
                            llegoPaqueteDeNoticias(result);
                        }

                        @Override
                        public void onError(String message) {
                        }
                    });
                } else {
                    Toast.makeText(this, "Debes registrarte primero", Toast.LENGTH_LONG).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        if (supportFragmentManager.getBackStackEntryCount() == 0) {
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void llegoPaqueteDeNoticias(ListaNoticias listaNoticias) {
        Log.d(TAG, "Llego un paquete de noticias");
        if (listaNoticias.getTema() == null) {
            listaNoticias.setTema("General");
        }
        pegarFragment(new FragmentListaNoticiasCompacto(), R.id.activityMainContenedorFragment, listaNoticias);
    }

    @Override
    public void errorPedidoNoticia() {
        // TODO: Ver que hacemos cuando hay un problema en la coneccion con la API
    }

    @Override
    public void selleccion(ListaNoticias listaNoticias) {
        Log.d(TAG, "Selecciono una noticia");
        pegarFragment(new ViewPagerNoticia(), R.id.activityMainContenedorFragment, listaNoticias);
    }

    @Override
    public void sellecionDos(ListaNoticias listaNoticias) {
        Log.d(TAG, "Selecciono la noticia");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }

    @Override
    public void regresarAFragmentAnterior() {
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        supportFragmentManager.popBackStack();
    }
}
