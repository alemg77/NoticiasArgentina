package kalitero.software.noticiasargentinas.Controlador;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoAPI;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoRoom;
import kalitero.software.noticiasargentinas.modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.modelo.Noticia;
import kalitero.software.noticiasargentinas.modelo.PaqueteNoticias;
import kalitero.software.noticiasargentinas.util.AppDatabase;
import kalitero.software.noticiasargentinas.util.EspressoIdlingTask;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class Repositorio {

    private static Context contexto;
    private static Repositorio instancia;
    private String TAG = getClass().toString();
    public final static String KEY_TEMA_DEPORTES = "sports";
    public final static String KEY_TEMA_NEGOCIOS = "business";
    public final static String KEY_TEMA_ENTRETENIMIENTO = "entertainment";
    public final static String KEY_TEMA_SALUD = "health";
    public final static String KEY_TEMA_TECNOLOGIA = "technology";
    public final static String KEY_TEMA_CIENCIA = "science";
    public final static String KEY_TEMA_GENERAL = "General";
    public final static String KEY_TEMA_BARRIALES = "Firebase";

    private ListaNoticias barriales;

    private PaqueteNoticias paqueteNoticias;

    private static NoticiaDaoAPI instanciaAPI;
    private static NoticiaDaoFirebase instanciaFirebase;
    private static NoticiaDaoRoom instanciaRoom;

    public Repositorio() {
    }

    public ListaNoticias getBarriales() {
        return barriales;
    }
    public void setBarriales(ListaNoticias barriales) {
        this.barriales = barriales;
    }

    public static Repositorio getInstancia(Context context) {
        if (instancia == null) {
            instancia = new Repositorio();
            contexto = context;
            instanciaAPI = NoticiaDaoAPI.getInstancia(contexto);
            instanciaFirebase = NoticiaDaoFirebase.Companion.getIntancia();
            instanciaRoom = AppDatabase.getInstance(contexto).noticiaDaoRoom();
        }
        return instancia;
    }

    public void dameNoticiasBarriales(ResultListener<ListaNoticias> resultListener) {
        if (hayInternet()) {
            instanciaFirebase.buscarNoticias(resultListener);
        } else {
            noticiasBarrialesRoom(resultListener);
        }
    }

    private void noticiasBarrialesRoom(ResultListener<ListaNoticias> resultListener) {
        instanciaRoom.getNoticiasTema(KEY_TEMA_BARRIALES);
    }

    public PaqueteNoticias traerTodoRoom() {
        paqueteNoticias = new PaqueteNoticias();
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_GENERAL), KEY_TEMA_GENERAL));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_CIENCIA), KEY_TEMA_CIENCIA));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_DEPORTES), KEY_TEMA_DEPORTES));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_NEGOCIOS), KEY_TEMA_NEGOCIOS));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_ENTRETENIMIENTO), KEY_TEMA_ENTRETENIMIENTO));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_ENTRETENIMIENTO), KEY_TEMA_SALUD));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_ENTRETENIMIENTO), KEY_TEMA_TECNOLOGIA));
        return paqueteNoticias;
    }

    private void traerTodoRoom(ResultListener<PaqueteNoticias> resultListener) {
        paqueteNoticias = new PaqueteNoticias();
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_GENERAL), KEY_TEMA_GENERAL));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_CIENCIA), KEY_TEMA_CIENCIA));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_DEPORTES), KEY_TEMA_DEPORTES));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_NEGOCIOS), KEY_TEMA_NEGOCIOS));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_ENTRETENIMIENTO), KEY_TEMA_ENTRETENIMIENTO));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_ENTRETENIMIENTO), KEY_TEMA_SALUD));
        paqueteNoticias.agregarListaNoticias(new ListaNoticias(noticiasDeRoom(KEY_TEMA_ENTRETENIMIENTO), KEY_TEMA_TECNOLOGIA));
        barriales = new ListaNoticias(noticiasDeRoom(KEY_TEMA_BARRIALES), KEY_TEMA_BARRIALES);
        resultListener.onFinish(paqueteNoticias);
    }

    public void noticiasDeRoom(ResultListener<ListaNoticias> resultListener) {
        List<Noticia> listNoticiasRoom = instanciaRoom.getNoticias();
        ListaNoticias listaNoticiasRoom = new ListaNoticias(listNoticiasRoom, "Room");
        resultListener.onFinish(listaNoticiasRoom);
    }

    public void noticiasDeRoom(String tema, ResultListener<ListaNoticias> resultListener) {
        List<Noticia> listNoticiasRoom = instanciaRoom.getNoticiasTema(tema);
        ListaNoticias listaNoticiasRoom = new ListaNoticias(listNoticiasRoom, tema);
        resultListener.onFinish(listaNoticiasRoom);
    }

    public void titulares(String tema, ResultListener<ListaNoticias> resultListener) {
        if (hayInternet()) {
            instanciaAPI.titularesNuevos(tema, resultListener);
        } else {
            noticiasDeRoom(tema, resultListener);
        }
    }

    public void buscarEnApi(String tema, ResultListener<ListaNoticias> resultListener) {
        if (hayInternet()) {
            instanciaAPI.porTema(tema, resultListener);
        }
    }

    public void traerTodo(ResultListener<PaqueteNoticias> resultListener) {
        if (hayInternet()) {
            traerTodoInternet(resultListener);
        } else {
            traerTodoRoom(resultListener);
        }
    }

    public List<Noticia> noticiasDeRoom(String tema) {
        return (instanciaRoom.getNoticiasTema(tema));
    }

    public void traerTodoInternet(ResultListener<PaqueteNoticias> resultListener) {
        EspressoIdlingTask.INSTANCE.increment();
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_GENERAL,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        paqueteNoticias = new PaqueteNoticias();
                        paqueteNoticias.agregarListaNoticias(result);
                        traerTodoApi2(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi2(ResultListener<PaqueteNoticias> resultListener) {
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_DEPORTES,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        paqueteNoticias.agregarListaNoticias(result);
                        traerTodoApi3(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi3(ResultListener<PaqueteNoticias> resultListener) {
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_SALUD,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        paqueteNoticias.agregarListaNoticias(result);
                        traerTodoApi4(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi4(ResultListener<PaqueteNoticias> resultListener) {
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_ENTRETENIMIENTO,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        paqueteNoticias.agregarListaNoticias(result);
                        traerTodoApi5(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi5(ResultListener<PaqueteNoticias> resultListener) {
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_TECNOLOGIA,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        paqueteNoticias.agregarListaNoticias(result);
                        traerTodoApi6(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi6(ResultListener<PaqueteNoticias> resultListener) {
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_CIENCIA,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        paqueteNoticias.agregarListaNoticias(result);
                        traerTodoApi7(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    public void traerTodoApi7(ResultListener<PaqueteNoticias> resultListener) {
        instanciaAPI.titularesNuevos(NoticiaDaoAPI.KEY_TEMA_NEGOCIOS,
                new ResultListener<ListaNoticias>() {
                    @Override
                    public void onFinish(ListaNoticias result) {
                        paqueteNoticias.agregarListaNoticias(result);
                        guardarTodoEnRoom();
                        traerTodoApi8(resultListener);
                    }

                    @Override
                    public void onError(@NotNull String message) {

                    }
                });
    }

    private void guardarTodoEnRoom() {
        List<ListaNoticias> paqueteCompleto = paqueteNoticias.getPaqueteCompleto();
        for (ListaNoticias listaNoticias : paqueteCompleto) {
            ArrayList<Noticia> arrayListNoticias = listaNoticias.getArrayListNoticias();
            instanciaRoom.insertAll(arrayListNoticias);
        }
    }

    public void traerTodoApi8(ResultListener<PaqueteNoticias> resultListener) {
        instanciaFirebase.buscarNoticias(new ResultListener<ListaNoticias>() {
            @Override
            public void onFinish(ListaNoticias result) {
                paqueteNoticias.agregarListaNoticias(result);
                barriales = result;
                resultListener.onFinish(paqueteNoticias);
                EspressoIdlingTask.INSTANCE.decrement();
            }

            @Override
            public void onError(@NotNull String message) {
            }
        });
    }

    public boolean hayInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static List<String> crearLista() {
        List<String> listaTemas = new ArrayList<>();
        listaTemas.add(KEY_TEMA_GENERAL);
        listaTemas.add(KEY_TEMA_CIENCIA);
        listaTemas.add(KEY_TEMA_DEPORTES);
        listaTemas.add(KEY_TEMA_ENTRETENIMIENTO);
        listaTemas.add(KEY_TEMA_NEGOCIOS);
        listaTemas.add(KEY_TEMA_SALUD);
        listaTemas.add(KEY_TEMA_TECNOLOGIA);
        return listaTemas;
    }
}
