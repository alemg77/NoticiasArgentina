package kalitero.software.noticiasargentinas.Vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleNoticiasFragment extends Fragment {
    private String TAG = getClass().toString();

    public static final String NOTICIA = "noticia";

    //Fabrica el fragment
    public static DetalleNoticiasFragment dameUnFragment(Noticia noticia){
        // Crear el fragment
        DetalleNoticiasFragment detalleNoticiasFragment = new DetalleNoticiasFragment();
        // Pasar el bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable(NOTICIA,noticia);
        detalleNoticiasFragment.setArguments(bundle);
        //Hacer el return
        return detalleNoticiasFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_noticias, container, false);

        Bundle bundle = getArguments();
        Noticia noticia = (Noticia) bundle.getSerializable(NOTICIA);

        ImageView imageViewNoticia = view.findViewById(R.id.fragmentDetalleNoticiasImageView);
        TextView textViewNoticia = view.findViewById(R.id.fragmentDetalleNoticiastextView);
        TextView textViewTitulo = view.findViewById(R.id.fragmentTituloNoticiastextView);

        //imageViewNoticia.setImageResource(noticia.getUrlImagen());

        try {
            Picasso.get().load(noticia.getUrlImagen()).into(imageViewNoticia);
        } catch ( Exception e){
            Log.d(TAG, "Error en la URL");
        }

        textViewNoticia.setText(noticia.getDescripcion());
        String titulo = noticia.getTitulo();
        if ( titulo.contains("-") ) {
            textViewTitulo.setText(titulo.substring(0,titulo.indexOf("-")-1));
        } else {
            textViewTitulo.setText(titulo);
        }

        return view;
    }
}
