package kalitero.software.noticiasargentinas.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaNoticias implements Serializable {

    private int posicionInicial;
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

    public int getPosicionInicial() {
        return posicionInicial;
    }

    public void setPosicionInicial(int posicionInicial) {
        this.posicionInicial = posicionInicial;
    }
}
