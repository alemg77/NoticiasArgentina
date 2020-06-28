package kalitero.software.noticiasargentinas.Controlador;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoRoom;
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.util.AppDatabase;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class Repositorio {

    private static Context contexto;
    private static Repositorio instancia;
    private String TAG = getClass().toString();

    public Repositorio() {
    }

    public static Repositorio getInstancia(Context context) {
        if (instancia == null) {
            instancia = new Repositorio();
            contexto = context;
        }
        return instancia;
    }

    public boolean hayInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void dameNoticias(ResultListener<ListaNoticias> resultListener){
        if (hayInternet()) {
                // todo: traer API y Firebase
        } else {
                // todo: traer room
        }
    }

    public void noticiasDeFirebase(ResultListener<ListaNoticias> resultListener) {
        NoticiaDaoFirebase intanciaFirebase = NoticiaDaoFirebase.Companion.getIntancia();
        intanciaFirebase.buscarNoticias(new ResultListener<ListaNoticias>() {
            @Override
            public void onFinish(ListaNoticias result) {
                NoticiaDaoRoom noticiaDaoRoom = AppDatabase.getInstance(contexto).noticiaDaoRoom();
                // FIXME: Esta funcion no borra nada
                noticiaDaoRoom.deleteFirebase();
                noticiaDaoRoom.insertAll(result.getArrayListNoticias());
                resultListener.onFinish(result);
            }

            @Override
            public void onError(@NotNull String message) {

            }
        });
    }

    public void noticiasDeRoom(ResultListener<ListaNoticias> resultListener) {
        // TODO: Hacer que se ejecute en otro thread
        NoticiaDaoRoom noticiaDaoRoom = AppDatabase.getInstance(contexto).noticiaDaoRoom();
        List<Noticia> listNoticiasRoom = noticiaDaoRoom.getNoticias();
//        List<Noticia> listNoticiasDeportes = noticiaDaoRoom.getNoticiasTema(BuscarNoticiasAPI.KEY_TEMA_DEPORTES);
        ListaNoticias listaNoticiasRoom = new ListaNoticias(listNoticiasRoom, "Room");
        resultListener.onFinish(listaNoticiasRoom);
    }

    public void noticiasDeRoom(String tema, ResultListener<ListaNoticias> resultListener) {
        // TODO: Hacer que se ejecute en otro thread
        NoticiaDaoRoom noticiaDaoRoom = AppDatabase.getInstance(contexto).noticiaDaoRoom();
        List<Noticia> listNoticiasRoom = noticiaDaoRoom.getNoticiasTema(tema);
        ListaNoticias listaNoticiasRoom = new ListaNoticias(listNoticiasRoom, tema);
        resultListener.onFinish(listaNoticiasRoom);
    }

    public void noticiasAPI(){

    }


}
