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

import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;


public class FragmentListaNoticiasCompacto extends Fragment implements NoticiaAdapter.AvisoRecyclerView{

    private RecyclerView recyclerView;
    private FragmentListaNoticiasCompacto.Aviso listener;

    public FragmentListaNoticiasCompacto() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (Aviso) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_lista_noticias_compacto, container, false);
        recyclerView = inflate.findViewById(R.id.FragmentRecyclerViewNoticiasCompactas);
        Bundle bundle = getArguments();
        ListaNoticias listaNoticias = (ListaNoticias) bundle.getSerializable(ListaNoticias.class.toString());
        NoticiaAdapter noticiaAdapter = new NoticiaAdapter(listaNoticias.getArrayListNoticias(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noticiaAdapter);
        return inflate;
    }

    @Override
    public void recyclerViewClick(Noticia noticia) {
        listener.selleccion(noticia);                   // Me llego del Recyclerview y se lo paso a la actividad.
    }

    public interface Aviso {
        void selleccion (Noticia noticia);
    }

}

