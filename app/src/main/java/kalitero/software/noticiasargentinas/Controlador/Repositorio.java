package kalitero.software.noticiasargentinas.Controlador;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Repositorio {

    private Context context;

    public Repositorio(Context context) {
        this.context = context;
    }

    public boolean hayInternet () {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
