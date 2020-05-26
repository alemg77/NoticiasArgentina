package kalitero.software.noticiasargentinas.Vista.ViewPager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kalitero.software.noticiasargentinas.Controlador.BuscarNoticias;
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerListasNoticias extends Fragment {

    private ViewPager viewPager;
    private ListaNoticias listaNoticias;
    private ViewPagerListasNoticias.SelleccionDos listener;


    public ViewPagerListasNoticias() {  }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (SelleccionDos) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_v_p_lista, container, false);

        viewPager = view.findViewById(R.id.fragmentVPLista_viewPager);
        Bundle bundle = getArguments();
        ListaNoticias listaNoticias = (ListaNoticias) bundle.getSerializable(ListaNoticias.class.toString());
        //creo el adapter
        ViewPagerListaNoticiasAdapter adapter = new ViewPagerListaNoticiasAdapter(getActivity().getSupportFragmentManager(), BuscarNoticias.crearLista(), listaNoticias);
        //le seteo el adapter al view pager
        viewPager.setAdapter(adapter);

        return view;
    }

    public interface SelleccionDos {
        void sellecionDos (ListaNoticias listaNoticias);
    }
}
