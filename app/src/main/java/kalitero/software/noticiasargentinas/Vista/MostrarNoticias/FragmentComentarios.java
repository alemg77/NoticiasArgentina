package kalitero.software.noticiasargentinas.Vista.MostrarNoticias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentComentariosBinding;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class FragmentComentarios extends Fragment {

    private String TAG = getClass().toString();
    private FragmentComentariosBinding binding;
    private Noticia noticia;
    private RecyclerView recyclerViewComentarios;

    public FragmentComentarios() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentComentariosBinding.inflate(getLayoutInflater());
        escucharBotonComentar();
        Bundle bundle = getArguments();
        noticia = (Noticia) bundle.getSerializable(Noticia.class.toString());
        NoticiaDaoFirebase.Companion.getIntancia().buscarComentarios(noticia.getDocumentoFirebase(),
                new ResultListener<List<Comentario>>() {
                    @Override
                    public void onFinish(List<Comentario> result) {
                        Log.d(TAG, "Ver aqui que paso?");
                    }

                    @Override
                    public void onError(@NotNull String message) {
                        Log.d(TAG, "QUe paso?");
                    }
                });
        return binding.getRoot();
    }

    private void escucharBotonComentar() {
        binding.fragmentComentariosBotonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = binding.fragmentComentarioEditTextComentarioNuevo.getText().toString();
                if ( texto.length() < 2 ) {
                    Snackbar.make(binding.getRoot(), "Comentario muy corto", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }
                Comentario comentario = new Comentario();
                comentario.setOpinion(texto);
                comentario.setUsuario(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                NoticiaDaoFirebase.Companion.getIntancia().agregarComentario(noticia.getDocumentoFirebase(), comentario);
                Snackbar.make(binding.getRoot(), "Gracias por su comentario", BaseTransientBottomBar.LENGTH_LONG).show();
                binding.fragmentComentarioEditTextComentarioNuevo.setText(" ");
            }
        });
    }
}