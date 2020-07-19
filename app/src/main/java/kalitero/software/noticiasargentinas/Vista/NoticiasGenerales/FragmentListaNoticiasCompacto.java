package kalitero.software.noticiasargentinas.Vista.NoticiasGenerales;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kalitero.software.noticiasargentinas.Controlador.RecepcionNoticias;
import kalitero.software.noticiasargentinas.modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentListaNoticiasCompactoBinding;


public class FragmentListaNoticiasCompacto extends Fragment implements FragmentListaNoticiasCompactoAdapter.AvisoRecyclerViewJava {


    private FragmentListaNoticiasCompactoBinding binding;
    private RecepcionNoticias listener;
    private ListaNoticias listaNoticias;
    public static final String CLAVE_TEMA = "claveTema";
    public static final String LISTA_NOTICIAS = ListaNoticias.class.toString();

    public FragmentListaNoticiasCompacto() {   }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (RecepcionNoticias) context;
    }

    //Fabrica el fragment
    public static FragmentListaNoticiasCompacto dameUnFragment(ListaNoticias listaNoticias){
        FragmentListaNoticiasCompacto fragmentListaNoticiasCompacto = new FragmentListaNoticiasCompacto();
        // Pasar el bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable(LISTA_NOTICIAS,listaNoticias);
        bundle.putString(CLAVE_TEMA, listaNoticias.getTema());
        fragmentListaNoticiasCompacto.setArguments(bundle);
        //Hacer el return
        return fragmentListaNoticiasCompacto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentListaNoticiasCompactoBinding.inflate(inflater, container, false);
        View inflate = inflater.inflate(R.layout.fragment_lista_noticias_compacto, container, false);
        Bundle bundle = getArguments();
        String tema = bundle.getString(CLAVE_TEMA);
        listaNoticias = (ListaNoticias) bundle.getSerializable(LISTA_NOTICIAS);
        binding.fragmentListaNoticiasTextViewcategoria.setText(tema);
        FragmentListaNoticiasCompactoAdapter noticiaAdapter = new FragmentListaNoticiasCompactoAdapter(listaNoticias.getArrayListNoticias(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.FragmentRecyclerViewNoticiasCompactas.setLayoutManager(layoutManager);
        binding.FragmentRecyclerViewNoticiasCompactas.setAdapter(noticiaAdapter);
        return binding.getRoot();
    }

    @Override
    public void recyclerViewClick(int posicion) {
        listaNoticias.setPosicionInicial(posicion);
        listener.mostrarDetalleDeNoticias(listaNoticias);                   // Me llego del Recyclerview y se lo paso a la actividad.
    }

}

