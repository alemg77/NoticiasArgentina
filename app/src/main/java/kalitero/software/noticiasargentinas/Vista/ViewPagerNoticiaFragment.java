package kalitero.software.noticiasargentinas.Vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerNoticiaFragment extends Fragment {

    private ViewPager viewPager;


    public ViewPagerNoticiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_view_pager_noticia, container, false);

        viewPager = inflate.findViewById(R.id.viewPagerDetalleNoticia);

        Bundle bundle = getArguments();
        ListaNoticias listaNoticias = (ListaNoticias) bundle.getSerializable(ListaNoticias.class.toString());
        ViewPageAdapter adapter = new ViewPageAdapter(getActivity().getSupportFragmentManager(),listaNoticias.getArrayListNoticias());
        viewPager.setAdapter(adapter);

        return inflate;



    }
}
