package kalitero.software.noticiasargentinas.Vista.NoticiasGenerales.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.Vista.Detalle.FragmentDetalleNoticias;

public class ViewPagerNoticiaAdapter extends FragmentStatePagerAdapter {

    //Lista de fragmentos
    private List<FragmentDetalleNoticias> listaDeFragments;

    public ViewPagerNoticiaAdapter(FragmentManager fm, List<Noticia> noticiasAMostrar) {
        super(fm);
        this.listaDeFragments = new ArrayList<>();

        for (Noticia noticia : noticiasAMostrar) {
            FragmentDetalleNoticias fragment = FragmentDetalleNoticias.dameUnFragment(noticia);
            listaDeFragments.add(fragment);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listaDeFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaDeFragments.size();
    }


}


