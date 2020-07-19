package kalitero.software.noticiasargentinas.Vista.MostrarNoticias.ViewPager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kalitero.software.noticiasargentinas.Controlador.Repositorio;
import kalitero.software.noticiasargentinas.modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.modelo.PaqueteNoticias;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.Vista.NoticiasGenerales.ViewPager.ViewPagerListasNoticiasAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerListasNoticias extends Fragment {

    private ViewPager viewPager;
    private ViewPagerListasNoticias.SelleccionDos listener;
    private Repositorio repositorio;


    public ViewPagerListasNoticias() {  }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (SelleccionDos) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_v_p_lista, container, false);

        viewPager = view.findViewById(R.id.fragmentVPLista_viewPager);
        Bundle bundle = getArguments();
        PaqueteNoticias paqueteNoticias = (PaqueteNoticias) bundle.getSerializable(PaqueteNoticias.class.toString());
            ViewPagerListasNoticiasAdapter adapter = new ViewPagerListasNoticiasAdapter(getActivity().getSupportFragmentManager(), Repositorio.crearLista(), paqueteNoticias);
            viewPager.setAdapter(adapter);

        return view;
    }

    public interface SelleccionDos {
        void sellecionDos (ListaNoticias listaNoticias);
    }
}
