package kalitero.software.noticiasargentinas;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;


import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kalitero.software.noticiasargentinas.Controlador.BuscarNoticias;
import kalitero.software.noticiasargentinas.Controlador.RecepcionNoticias;
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Vista.Fragment.FragmentListaNoticiasCompacto;
import kalitero.software.noticiasargentinas.Vista.Fragment.FragmentLogin;
import kalitero.software.noticiasargentinas.Vista.ViewPager.ViewPagerListasNoticias;
import kalitero.software.noticiasargentinas.Vista.ViewPager.ViewPagerNoticia;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecepcionNoticias, FragmentListaNoticiasCompacto.Aviso, ViewPagerListasNoticias.SelleccionDos {

    // Para ver los logos hay que filtrar con: kalitero.software.noticiasargentinas.Vista
    private String TAG = getClass().toString();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
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

    void Borrar () {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e(TAG, something);
            }
        }

        catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            Log.e(TAG, e1.toString());
        }

        catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.toString());
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.activityMainNavigationView);
        drawerLayout = findViewById(R.id.activityMainDrawerLayout);


        Toolbar toolbar = findViewById(R.id.MainActivityToolbar);
        setSupportActionBar(toolbar);

        Borrar();

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



    @Override
    public void llegoPaqueteDeNoticias(ListaNoticias listaNoticias) {
        Log.d(TAG, "Llego un paquete de noticias");
        this.listaNoticias = listaNoticias;
        pegarFragment(new ViewPagerListasNoticias(), R.id.activityMainContenedorFragment, listaNoticias);
        //pegarFragment(new ViewPagerNoticia(), R.id.activityMainContenedorFragment, listaNoticias);




    }

    @Override
    public void errorPedidoNoticia() {
        // TODO: Ver que hacemos cuando hay un problema en la coneccion con la API
    }

    @Override
    public void selleccion(int posicion) {
        Log.d(TAG, "Selecciono una noticia");

        listaNoticias.setPosicionInicial(posicion);
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
