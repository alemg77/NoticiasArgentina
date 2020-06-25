package kalitero.software.noticiasargentinas.Controlador;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.ComentarioDaoRoom;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoRoom;
import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.util.AppDatabase;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class ComentariosController {

    private NoticiaDaoFirebase noticiaDaoFirebase;
    private ComentarioDaoRoom comentarioDaoRoom;
    private Context context;

    public ComentariosController(Context context) {
        this.noticiaDaoFirebase = new NoticiaDaoFirebase();
        this.context = context;
        this.comentarioDaoRoom = AppDatabase.getInstance(context).comentarioDaoRoom();
    }

    public void getComentarios(ResultListener<List<Comentario>> resultListenerDeLaView, String documentoFirebase) {

        if (hayInternet()) {
            NoticiaDaoFirebase.Companion.getIntancia().buscarComentarios(documentoFirebase, new ResultListener<List<Comentario>>() {
                @Override
                public void onFinish(List<Comentario> result) {
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
