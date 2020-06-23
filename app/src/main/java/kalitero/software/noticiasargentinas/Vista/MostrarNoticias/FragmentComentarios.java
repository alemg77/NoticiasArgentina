package kalitero.software.noticiasargentinas.Vista.MostrarNoticias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileReader;

import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentComentariosBinding;

public class FragmentComentarios extends Fragment {

    private FragmentComentariosBinding binding;

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







        return binding.getRoot();
    }
}