package kalitero.software.noticiasargentinas.Vista.MostrarNoticias.ViewPager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoRoom;
import kalitero.software.noticiasargentinas.modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.modelo.Noticia;
import kalitero.software.noticiasargentinas.Vista.NoticiasGenerales.FragmentListaNoticiasCompacto;
import kalitero.software.noticiasargentinas.util.AppDatabase;

public class ViewPagerListasNoticiasAdapterRoom extends FragmentStatePagerAdapter {

    private List<FragmentListaNoticiasCompacto> listaDeFragments;
    private NoticiaDaoRoom noticiaDaoRoom;
    private Context context;

    public ViewPagerListasNoticiasAdapterRoom(@NonNull FragmentManager fm, List<String> listaTema, Context context) {
        super(fm);
        this.context = context;
        this.noticiaDaoRoom = AppDatabase.getInstance(context).noticiaDaoRoom();

       // List<ListaNoticias> ListaDeListasDeNoticia = paqueteNoticias.getPaqueteCompleto();
        listaDeFragments = new ArrayList<>();
        for (String tema : listaTema) {
            List<Noticia>listaNoticiasRoom = noticiaDaoRoom.getNoticiasTema(tema);
            ListaNoticias listaNoticiasRoomParaFragment = new ListaNoticias(listaNoticiasRoom, tema);

            FragmentListaNoticiasCompacto fragment = FragmentListaNoticiasCompacto.dameUnFragment(listaNoticiasRoomParaFragment);
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
