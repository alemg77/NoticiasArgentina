package kalitero.software.noticiasargentinas.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import kalitero.software.noticiasargentinas.R;

public class DetalleNoticiasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticias);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        DetalleNoticiasFragment detalleNoticiasFragment = new DetalleNoticiasFragment();
        detalleNoticiasFragment.setArguments(bundle);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activityDetalleNoticiasContenedorFragment,detalleNoticiasFragment)
                .commit();



    }
}
