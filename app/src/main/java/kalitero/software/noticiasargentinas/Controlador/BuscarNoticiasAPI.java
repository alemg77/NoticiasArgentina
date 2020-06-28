package kalitero.software.noticiasargentinas.Controlador;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoRoom;
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.util.AppDatabase;

public class BuscarNoticiasAPI extends AppCompatActivity {

    public final static String KEY_PAIS_ARGENTINA = "ar";
    public final static String KEY_PAIS_USA = "us";
    public final static String KEY_PAIS_BRASIL = "br";
    public final static String KEY_PAIS_COLOMBIA = "co";

    public final static String KEY_TEMA_DEPORTES = "sports";
    public final static String KEY_TEMA_NEGOCIOS = "business";
    public final static String KEY_TEMA_ENTRETENIMIENTO = "entertainment";
    public final static String KEY_TEMA_SALUD = "health";
    public final static String KEY_TEMA_TECNOLOGIA = "technology";
    public final static String KEY_TEMA_CIENCIA = "science";
    public final static String KEY_TEMA_GENERAL = "General";

    public final static String KEY_API = "958203d2e91b4511936cf0ad0acc25ae";

    private String TAG = getClass().toString();

    private RecepcionNoticias listener;

    private NoticiaDaoRoom noticiaDaoRoom;

    private Context context;

    public BuscarNoticiasAPI(RecepcionNoticias listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.noticiaDaoRoom = AppDatabase.getInstance(context).noticiaDaoRoom();
    }

    public void titularesNuevos(String pais) {
        getRequest("https://newsapi.org/v2/top-headlines?country=" + pais);
    }

    public void porTema(String tema) {
        getRequest("https://newsapi.org/v2/everything?q=" + tema);
    }

    public void porTemaEnEsp(String tema) {
        getRequest("https://newsapi.org/v2/everything?domains=cnnespanol.cnn.com,elmundo.es,news.google.com,lagaceta.com.ar,lanacion.com.ar,marca.com&q=" + tema);
    }

    public void titularesNuevos(String pais, final String temadelPedido) {
        String url = "https://newsapi.org/v2/top-headlines?country=" + pais + "&category=" + temadelPedido;
        RequestQueue queue = Volley.newRequestQueue((Context) listener);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Noticia> listaDeNoticias = new ArrayList<>();
                        try {
                            JSONObject jsonNoticias = new JSONObject(response);
                            JSONArray jsonArticulos = jsonNoticias.getJSONArray("articles");
                            for (int i = 0; i < jsonArticulos.length(); i++) {
                                JSONObject jsonNoticia = (JSONObject) jsonArticulos.get(i);
                                JSONObject jsonFuente = (JSONObject) jsonNoticia.get("source");
                                String noticiaTitulo = jsonNoticia.getString("title");
                                String noticiaAutor = jsonNoticia.getString("author");
                                String noticiaFuente = jsonFuente.getString("name");
                                String noticiaDescripcion = jsonNoticia.getString("description");
                                String urlNoticia = jsonNoticia.getString("url");
                                String urlToImage = jsonNoticia.getString("urlToImage");
                                String noticiaTema = temadelPedido;
                                Boolean origenFirebase = false;
                                Noticia noticia = new Noticia(noticiaFuente, noticiaAutor, noticiaTitulo, noticiaDescripcion, urlNoticia, urlToImage, new Date(), noticiaTema, origenFirebase);
                                listaDeNoticias.add(noticia);
                            }
                            // TODO: ESTO DEBERIA HACERLO EL REPOSITORIO!!!!!!!!
                            //noticiaDaoRoom.insertAll(listaDeNoticias);

                            ListaNoticias listaNoticias = new ListaNoticias(listaDeNoticias, temadelPedido);
                            listener.llegoPaqueteDeNoticias(listaNoticias);
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.errorPedidoNoticia();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error!!:" + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Api-Key", KEY_API);
                return params;
            }
        };
        queue.add(getRequest);
    }

    private void getRequest(String url) {
        RequestQueue queue = Volley.newRequestQueue((Context) listener);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Noticia> listaDeNoticias = new ArrayList<>();
                        try {
                            JSONObject jsonNoticias = new JSONObject(response);
                            JSONArray jsonArticulos = jsonNoticias.getJSONArray("articles");
                            for (int i = 0; i < jsonArticulos.length(); i++) {
                                JSONObject jsonNoticia = (JSONObject) jsonArticulos.get(i);
                                JSONObject jsonFuente = (JSONObject) jsonNoticia.get("source");
                                String noticiaTitulo = jsonNoticia.getString("title");
                                String noticiaAutor = jsonNoticia.getString("author");
                                String noticiaFuente = jsonFuente.getString("name");
                                String noticiaDescripcion = jsonNoticia.getString("description");
                                String urlNoticia = jsonNoticia.getString("url");
                                String urlToImage = jsonNoticia.getString("urlToImage");
                                String noticiaTema = "General";
                                Boolean origenFirebase = false;
                                Noticia noticia = new Noticia(noticiaFuente, noticiaAutor, noticiaTitulo, noticiaDescripcion, urlNoticia, urlToImage, new Date(), noticiaTema, origenFirebase);
                                listaDeNoticias.add(noticia);
                            }

                            // TODO: ESTO DEBERIA HACERLO EL REPOSITORIO!!!!!!!!
                            //noticiaDaoRoom.deleteAll();
                            //noticiaDaoRoom.insertAll(listaDeNoticias);
                            listener.llegoPaqueteDeNoticias(new ListaNoticias(listaDeNoticias, "General"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.errorPedidoNoticia();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error!!:" + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Api-Key", KEY_API);
                return params;
            }
        };
        queue.add(getRequest);
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