package kalitero.software.noticiasargentinas.Vista.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.aprilapps.easyphotopicker.EasyImage;
import kalitero.software.noticiasargentinas.databinding.FragmentDetalleBarrialBinding;

public class FragmentDetalleBarrial extends Fragment {

    private FragmentDetalleBarrialBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentDetalleBarrialBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }




}