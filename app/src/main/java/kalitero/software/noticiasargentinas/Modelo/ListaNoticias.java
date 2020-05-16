package kalitero.software.noticiasargentinas.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaNoticias implements Serializable {
    private ArrayList<Noticia> arrayListNoticias;

    public ListaNoticias(ArrayList<Noticia> arrayListNoticias) {
        this.arrayListNoticias = arrayListNoticias;
    }

    public ArrayList<Noticia> getArrayListNoticias() {
        return arrayListNoticias;
    }

    public void setArrayListNoticias(ArrayList<Noticia> arrayListNoticias) {
        this.arrayListNoticias = arrayListNoticias;
    }
}
