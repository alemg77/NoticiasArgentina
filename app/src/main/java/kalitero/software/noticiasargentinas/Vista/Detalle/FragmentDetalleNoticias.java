package kalitero.software.noticiasargentinas.Vista.Detalle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.ComentariosController;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.Modelo.Voto;
import kalitero.software.noticiasargentinas.databinding.FragmentDetalleNoticiasBinding;
import kalitero.software.noticiasargentinas.util.ResultListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetalleNoticias extends Fragment implements ComentarioAdapter.Votacion {
    private String TAG = getClass().toString();

    private FragmentDetalleNoticiasBinding binding;
    private List<Comentario> comentarios;
    private Noticia noticia;
    private ComentarioAdapter animalAdapter;

    //Fabrica el fragment
    public static FragmentDetalleNoticias dameUnFragment(Noticia noticia) {
        FragmentDetalleNoticias detalleNoticiasFragment = new FragmentDetalleNoticias();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Noticia.class.toString(), noticia);
        detalleNoticiasFragment.setArguments(bundle);
        return detalleNoticiasFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetalleNoticiasBinding.inflate(getLayoutInflater());

        Bundle bundle = getArguments();
        noticia = (Noticia) bundle.getSerializable(Noticia.class.toString());

        String urlImagenStorage = noticia.getUrlImagenStorage();
        if (urlImagenStorage != null) {
            StorageReference child = FirebaseStorage.getInstance().getReference().child(urlImagenStorage);
            Glide.with(binding.getRoot()).load(child).into(binding.imagenNoticia);
        } else if (noticia.getUrlImagen() != null) {
            binding.CardAgregarComentario.setVisibility(View.INVISIBLE);
            try {
                Glide.with(binding.getRoot()).load(noticia.getUrlImagen()).into(binding.imagenNoticia);
            } catch (Exception e) {
                Log.d(TAG, "Problema al cargar imagen:" + e.toString());
            }
        }

        binding.fragmentDetalleNoticiastextView.setText(noticia.getDescripcion());
        String titulo = noticia.getTitulo();
        if (titulo.contains("-")) {
            binding.textViewTitulo.setText(titulo.substring(0, titulo.indexOf("-") - 1));
        } else {
            binding.textViewTitulo.setText(titulo);
        }

        cargarRecycler();

        escucharComentariosNuevos();

        return binding.getRoot();
    }

    private void escucharComentariosNuevos() {
        binding.TextoComentarioNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if ( currentUser == null ){
                    Snackbar.make(binding.getRoot(), "Es necesario registrarse para comentar", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                String email = currentUser.getEmail();
                if (email == null) {
                    Snackbar.make(binding.getRoot(), "Es necesario registrarse para comentar", Snackbar.LENGTH_LONG).show();
                    return;
                }
                String opionion = binding.TextoComentarioNuevo.getText().toString();
                if (opionion.length() < 2) {
                    Snackbar.make(binding.getRoot(), "El comentario debe tener mas de un caracter", Snackbar.LENGTH_LONG).show();
                    return;
                }
                Comentario comentario = new Comentario(email, opionion);
                NoticiaDaoFirebase.Companion.getIntancia().agregarComentario(noticia, comentario, new ResultListener<String>() {
                    @Override
                    public void onFinish(String result) {
                        actualizarRecycler();
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
                binding.TextoComentarioNuevo.setText(" ");
                Snackbar.make(binding.getRoot(), "Gracias por comentar!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void cargarRecycler() {
        animalAdapter = new ComentarioAdapter(noticia, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.FragmentComentariosRecyclerView.setLayoutManager(linearLayoutManager);
        binding.FragmentComentariosRecyclerView.setAdapter(animalAdapter);
        actualizarRecycler();
    }

    private void actualizarRecycler() {
        ComentariosController.getInstancia(getContext()).getComentarios(noticia,
                new ResultListener<List<Comentario>>() {
                    @Override
                    public void onFinish(@NotNull List<Comentario> result) {
                        comentarios = result;
                        animalAdapter.Actualizar(comentarios);
                    }

                    @Override
                    public void onError(@NotNull String message) {  }
                });
    }

    @Override
    public void votoPositivo(int posicion) {
        Comentario comentario = comentarios.get(posicion);
        String usuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Voto voto = new Voto(usuario, true);
        NoticiaDaoFirebase.Companion.getIntancia().votoComentario(noticia, comentario, voto);
        actualizarRecycler();
    }

    @Override
    public void votoNegativo(int posicion) {
        Comentario comentario = comentarios.get(posicion);
        String usuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Voto voto = new Voto(usuario, false);
        NoticiaDaoFirebase.Companion.getIntancia().votoComentario(noticia, comentario, voto);
        actualizarRecycler();
    }
}
