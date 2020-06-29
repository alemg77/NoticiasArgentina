package kalitero.software.noticiasargentinas.Controlador;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoAPI;
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

    private ListaNoticias general;
    private ListaNoticias ciencia;
    private ListaNoticias deportes;
    private ListaNoticias negocios;
    private ListaNoticias entretenimiento;
    private ListaNoticias salud;
    private ListaNoticias tecnologia;
    private ListaNoticias barriales;

    private static NoticiaDaoAPI instanciaAPI;
    private static NoticiaDaoFirebase intanciaFirebase;

    public Repositorio() {
    }

    public static Repositorio getInstancia(Context context) {
        if (instancia == null) {
            instancia = new Repositorio();
            contexto = context;
            instanciaAPI = NoticiaDaoAPI.getInstancia(contexto);
            intanciaFirebase = NoticiaDaoFirebase.Companion.getIntancia();
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

    public void titularesAPI(ResultListener<ListaNoticias> resultListener){
        instanciaAPI.titularesNuevos(new ResultListener<ListaNoticias>() {
            @Override
            public void onFinish(ListaNoticias result) {
                resultListener.onFinish(result);
            }

            @Override
            public void onError(@NotNull String message) {

            }
        });
    }

    public void traerTodo(ResultListener<ListaNoticias> resultListener){
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_NEGOCIOS,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        negocios = result;
                        traerTodoApi2(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi2(ResultListener<ListaNoticias> resultListener){
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_DEPORTES,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        deportes = result;
                        traerTodoApi3(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi3(ResultListener<ListaNoticias> resultListener){
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_SALUD,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        salud = result;
                        traerTodoApi4(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi4(ResultListener<ListaNoticias> resultListener){
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_ENTRETENIMIENTO,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        deportes = result;
                        traerTodoApi5(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi5(ResultListener<ListaNoticias> resultListener){
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_TECNOLOGIA,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        tecnologia = result;
                        traerTodoApi6(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi6(ResultListener<ListaNoticias> resultListener){
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_CIENCIA,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        ciencia = result;
                        traerTodoApi7(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi7(ResultListener<ListaNoticias> resultListener){
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_GENERAL,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        general = result;
                        traerTodoApi8(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi8(ResultListener<ListaNoticias> resultListener){
        intanciaFirebase.buscarNoticias(new ResultListener<ListaNoticias>() {
            @Override
            public void onFinish(ListaNoticias result) {
                barriales = result;
                resultListener.onFinish(result);
            }

            @Override
            public void onError(@NotNull String message) {

            }
        });
    }

}
