package kalitero.software.noticiasargentinas.Vista.MostrarNoticias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileReader;

import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentComentariosBinding;

public class FragmentComentarios extends Fragment {

    private String TAG = getClass().toString();
    private FragmentComentariosBinding binding;
    private Noticia noticia;

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
        return binding.getRoot();
    }

    private void escucharBotonComentar() {
        binding.fragmentComentariosBotonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = binding.fragmentComentarioEditTextComentarioNuevo.getText().toString();
                Log.d(TAG, "Quiero opinar:" + texto);
            }
        });
    }
}