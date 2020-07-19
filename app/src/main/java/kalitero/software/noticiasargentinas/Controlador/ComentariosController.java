package kalitero.software.noticiasargentinas.Controlador;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.ComentarioDaoRoom;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.modelo.Comentario;
import kalitero.software.noticiasargentinas.modelo.Noticia;
import kalitero.software.noticiasargentinas.util.AppDatabase;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class ComentariosController {

    private static ComentariosController instancia;
    private static NoticiaDaoFirebase noticiaDaoFirebase;
    private static Context context;
    private static ComentarioDaoRoom comentarioDaoRoom;

    public ComentariosController() {
    }

    public static ComentariosController getInstancia (Context contexto){
        if ( instancia == null ){
            context = contexto;
            instancia = new ComentariosController();
            noticiaDaoFirebase = NoticiaDaoFirebase.Companion.getIntancia();
            comentarioDaoRoom = AppDatabase.getInstance(contexto).comentarioDaoRoom();
        }
        return instancia;
    }

    public void getComentarios(Noticia noticia, ResultListener<List<Comentario>> resultListenerDeLaView) {
        if (hayInternet()) {
            NoticiaDaoFirebase.Companion.getIntancia().buscarComentarios(noticia, new ResultListener<List<Comentario>>() {
                @Override
                public void onFinish(List<Comentario> result) {
                    // TODO: Ver de pasarle la noticia para saber de quien es el comentario al recuperarlos
                    comentarioDaoRoom.deleteAll(); // los borro todos porque van a cambiar los likes aunque el resto de los datos no
                    comentarioDaoRoom.insertAll(result);
                    resultListenerDeLaView.onFinish(result);
                }

                @Override
                public void onError(@NotNull String message) {
                    resultListenerDeLaView.onError(message);
                }
            });
        } else {
            List<Comentario> listaComentarios = comentarioDaoRoom.getComentarios();
            resultListenerDeLaView.onFinish(listaComentarios);
        }
    }

    public boolean hayInternet () {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
