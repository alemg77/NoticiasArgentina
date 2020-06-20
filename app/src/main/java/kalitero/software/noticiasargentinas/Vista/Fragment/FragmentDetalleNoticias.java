package kalitero.software.noticiasargentinas.Vista.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentDetalleNoticiasBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetalleNoticias extends Fragment {
    private String TAG = getClass().toString();

    public static final String NOTICIA = "noticia";

    private FragmentDetalleNoticiasBinding binding;

    //Fabrica el fragment
    public static FragmentDetalleNoticias dameUnFragment(Noticia noticia){
        // Crear el fragment
        FragmentDetalleNoticias detalleNoticiasFragment = new FragmentDetalleNoticias();
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
        binding = FragmentDetalleNoticiasBinding.inflate(getLayoutInflater());

        Bundle bundle = getArguments();
        Noticia noticia = (Noticia) bundle.getSerializable(NOTICIA);

        ImageView imageViewNoticia = binding.fragmentDetalleNoticiasImageView;
        TextView textViewNoticia = binding.fragmentDetalleNoticiastextView;
        TextView textViewTitulo = binding.fragmentTituloNoticiastextView;
//        TextView textViewSeccion = binding.fragmentDetalleNot;

        //imageViewNoticia.setImageResource(noticia.getUrlImagen());

        try {
            Picasso.get().load(noticia.getUrlImagen()).into(imageViewNoticia);
        } catch ( Exception e){
            Log.d(TAG, "Error en la URL");
        }

        textViewNoticia.setText(noticia.getDescripcion());
//        textViewSeccion.setText(noticia.getTema());
        String titulo = noticia.getTitulo();
        if ( titulo.contains("-") ) {
            textViewTitulo.setText(titulo.substring(0,titulo.indexOf("-")-1));
        } else {
            textViewTitulo.setText(titulo);
        }

        return binding.getRoot();
    }
}
