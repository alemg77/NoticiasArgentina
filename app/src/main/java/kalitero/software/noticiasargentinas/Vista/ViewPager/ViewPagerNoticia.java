package kalitero.software.noticiasargentinas.Vista.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerNoticia extends Fragment {

    private ViewPager viewPager;


    public ViewPagerNoticia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_view_pager_noticia, container, false);

        viewPager = inflate.findViewById(R.id.viewPagerDetalleNoticia);

        Bundle bundle = getArguments();
        ListaNoticias listaNoticias = (ListaNoticias) bundle.getSerializable(ListaNoticias.class.toString());
        ViewPagerNoticiaAdapter adapter = new ViewPagerNoticiaAdapter(getActivity().getSupportFragmentManager(),listaNoticias.getArrayListNoticias());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(listaNoticias.getPosicionInicial());

        return inflate;



    }
}
