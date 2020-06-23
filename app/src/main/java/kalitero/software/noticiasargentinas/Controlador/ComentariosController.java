package kalitero.software.noticiasargentinas.Controlador;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class ComentariosController {

    private NoticiaDaoFirebase noticiaDaoFirebase;
    private Context context;

    public ComentariosController(Context context) {
        this.noticiaDaoFirebase = new NoticiaDaoFirebase();
        this.context = context;
    }

    public void getComentarios(ResultListener<List<Comentario>> resultListenerDeLaView){

        if (hayInternet()){
            noticiaDaoFirebase.buscarComentarios(new ResultListener<List<Comentario>>(), ){
                @Override
                public void onFinish(List<Comentario> result) {
                    resultListenerDeLaView.onFinish(result);
                }

                @Override
                public void onError(String message) {
                    resultListenerDeLaView.onError(message);
                }
            });

        } else {
            Toast.makeText(context, "No hay Internet", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean hayInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
