package kalitero.software.noticiasargentinas.Vista.NoticiasGenerales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.ComentariosController;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.Vista.NoticiasBarriales.ComentarioAdapter;
import kalitero.software.noticiasargentinas.databinding.FragmentDetalleNoticiasBinding;
import kalitero.software.noticiasargentinas.util.ResultListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetalleNoticias extends Fragment {
    private String TAG = getClass().toString();

    private FragmentDetalleNoticiasBinding binding;
 //   private FragmentComentarios fragmentComentarios;

    //Fabrica el fragment
    public static FragmentDetalleNoticias dameUnFragment(Noticia noticia){
        FragmentDetalleNoticias detalleNoticiasFragment = new FragmentDetalleNoticias();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Noticia.class.toString(),noticia);
        detalleNoticiasFragment.setArguments(bundle);
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
        // TextView textViewSeccion = binding.fragmentDetalleNot;
        // imageViewNoticia.setImageResource(noticia.getUrlImagen());

  //      fragmentComentarios = new FragmentComentarios();


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

        ComentariosController comentariosController = new ComentariosController(getContext());
        comentariosController.getComentarios(new ResultListener<List<Comentario>>() {
            @Override
            public void onFinish(List<Comentario> result) {
                cargarRecycler(result);
            }

            @Override
            public void onError(@NotNull String message) {

            }
        }, noticia.getDocumentoFirebase());

        return binding.getRoot();
    }

    private void cargarRecycler(List<Comentario> comentarioList) {
        ComentarioAdapter animalAdapter = new ComentarioAdapter(comentarioList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.FragmentComentariosRecyclerView.setLayoutManager(linearLayoutManager);
        binding.FragmentComentariosRecyclerView.setAdapter(animalAdapter);
    }

}
