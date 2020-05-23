package kalitero.software.noticiasargentinas.Vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleNoticiasFragment extends Fragment {

    public static final String NOTICIA = "noticia";

    public DetalleNoticiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_noticias, container, false);

        Bundle bundle = getArguments();
        Noticia noticia = (Noticia) bundle.getSerializable(NOTICIA);




        ImageView imageViewNoticia = view.findViewById(R.id.fragmentDetalleNoticiasImageView);
        TextView textViewNoticia = view.findViewById(R.id.fragmentDetalleNoticiastextView);

        //imageViewNoticia.setImageResource(noticia.getUrlImagen());

        Picasso.get().load(noticia.getUrlImagen()).into(imageViewNoticia);
        textViewNoticia.setText(noticia.getDescripcion());



        return view;
    }
}
