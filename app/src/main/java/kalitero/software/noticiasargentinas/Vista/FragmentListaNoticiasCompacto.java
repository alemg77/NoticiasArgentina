package kalitero.software.noticiasargentinas.Vista;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;


public class FragmentListaNoticiasCompacto extends Fragment implements NoticiaAdapter.AvisoRecyclerView{

    private RecyclerView recyclerView;
    private FragmentListaNoticiasCompacto.Aviso listener;
    private TextView textViewCategoria;
    public static final String CLAVE_TEMA = "claveTema";
    public static final String LISTA_NOTICIAS = "listaNoticias";

    public FragmentListaNoticiasCompacto() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (Aviso) context;
    }

    //fabrica de fragments
    //Fabrica el fragment
    public static FragmentListaNoticiasCompacto dameUnFragment(String tema, ListaNoticias listaNoticias){
        // Crear el fragment
        FragmentListaNoticiasCompacto fragmentListaNoticiasCompacto = new FragmentListaNoticiasCompacto();
        // Pasar el bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable(LISTA_NOTICIAS,listaNoticias);
        bundle.putString(CLAVE_TEMA, tema);
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
        View inflate = inflater.inflate(R.layout.fragment_lista_noticias_compacto, container, false);
        recyclerView = inflate.findViewById(R.id.FragmentRecyclerViewNoticiasCompactas);
        textViewCategoria = inflate.findViewById(R.id.fragmentListaNoticiasTextViewcategoria);
        Bundle bundle = getArguments();
        String tema = bundle.getString(CLAVE_TEMA);
        ListaNoticias listaNoticias = (ListaNoticias) bundle.getSerializable(LISTA_NOTICIAS);
        textViewCategoria.setText(tema);
        NoticiaAdapter noticiaAdapter = new NoticiaAdapter(listaNoticias.getArrayListNoticias(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noticiaAdapter);
        return inflate;
    }

    @Override
    public void recyclerViewClick(int posicion) {
        listener.selleccion(posicion);                   // Me llego del Recyclerview y se lo paso a la actividad.
    }

    public interface Aviso {
        void selleccion (int posicion);
    }

}

