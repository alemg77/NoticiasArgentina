package kalitero.software.noticiasargentinas.Vista.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.PaqueteNoticias;
import kalitero.software.noticiasargentinas.Vista.Fragment.FragmentListaNoticiasCompacto;

public class ViewPagerListasNoticiasAdapter extends FragmentStatePagerAdapter {

    private List<FragmentListaNoticiasCompacto> listaDeFragments;

    public ViewPagerListasNoticiasAdapter(@NonNull FragmentManager fm, List<String> listaTema, PaqueteNoticias paqueteNoticias) {
        super(fm);

        List<ListaNoticias> ListaDeListasDeNoticia = paqueteNoticias.getPaqueteCompleto();
        listaDeFragments = new ArrayList<>();
        for (ListaNoticias listaNoticias : ListaDeListasDeNoticia) {
            FragmentListaNoticiasCompacto fragment = FragmentListaNoticiasCompacto.dameUnFragment(listaNoticias);
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
