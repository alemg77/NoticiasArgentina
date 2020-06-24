package kalitero.software.noticiasargentinas.Vista.NoticiasGenerales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentDetalleNoticiasBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetalleNoticias extends Fragment {
    private String TAG = getClass().toString();

    private FragmentDetalleNoticiasBinding binding;
    private FragmentComentarios fragmentComentarios;

    //Fabrica el fragment
    public static FragmentDetalleNoticias dameUnFragment(Noticia noticia){
        // Crear el fragment
        FragmentDetalleNoticias detalleNoticiasFragment = new FragmentDetalleNoticias();
        // Pasar el bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable(Noticia.class.toString(),noticia);
        detalleNoticiasFragment.setArguments(bundle);
        //Hacer el return
        return detalleNoticiasFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetalleNoticiasBinding.inflate(getLayoutInflater());

        Bundle bundle = getArguments();
        Noticia noticia = (Noticia) bundle.getSerializable(Noticia.class.toString());

        ImageView imageViewNoticia = binding.fragmentDetalleNoticiasImageView;
        TextView textViewNoticia = binding.fragmentDetalleNoticiastextView;
        TextView textViewTitulo = binding.fragmentTituloNoticiastextView;
        FloatingActionButton botonComentarios = binding.fragmentDetalleNoticiaFABcomentario;
        // TextView textViewSeccion = binding.fragmentDetalleNot;
        // imageViewNoticia.setImageResource(noticia.getUrlImagen());

        fragmentComentarios = new FragmentComentarios();
        botonComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarFragment(fragmentComentarios, R.id.fragmentDetalleNoticiasContenedorComentarios,noticia);
            }
        });


        String urlImagenStorage = noticia.getUrlImagenStorage();
        if (urlImagenStorage != null) {
            StorageReference child = FirebaseStorage.getInstance().getReference().child(urlImagenStorage);
            Glide.with(binding.getRoot()).load(child).into(binding.fragmentDetalleNoticiasImageView);
        } else if (noticia.getUrlImagen() != null) {
            try {
                Glide.with(binding.getRoot()).load(noticia.getUrlImagen()).into(binding.fragmentDetalleNoticiasImageView);
            } catch (Exception e) {
                Log.d(TAG, "Problema al cargar imagen:" + e.toString());
            }
        }

        textViewNoticia.setText(noticia.getDescripcion());
        // textViewSeccion.setText(noticia.getTema());
        String titulo = noticia.getTitulo();
        if ( titulo.contains("-") ) {
            textViewTitulo.setText(titulo.substring(0,titulo.indexOf("-")-1));
        } else {
            textViewTitulo.setText(titulo);
        }

        return binding.getRoot();
    }

    private void pegarFragment(Fragment fragmentAPegar, int containerViewId, Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(serializable.getClass().toString(), serializable);
        fragmentAPegar.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(containerViewId, fragmentAPegar).commit();
    }

}
