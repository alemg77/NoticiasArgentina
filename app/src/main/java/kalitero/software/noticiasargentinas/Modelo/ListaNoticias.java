package kalitero.software.noticiasargentinas.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

import kalitero.software.noticiasargentinas.Controlador.BuscarNoticias;

public class ListaNoticias implements Serializable {

    private int posicionInicial;
    private String tema;

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        switch ( tema ) {
            case BuscarNoticias.KEY_TEMA_DEPORTES:
                this.tema = "Deportes";
                break;

            case BuscarNoticias.KEY_TEMA_CIENCIA:
                this.tema = "Ciencia";
                break;

            case BuscarNoticias.KEY_TEMA_ENTRETENIMIENTO:
                this.tema = "Espectaculos";
                break;

            case BuscarNoticias.KEY_TEMA_NEGOCIOS:
                this.tema = "Negocios";
                break;

            case BuscarNoticias.KEY_TEMA_SALUD:
                this.tema = "Salud";
                break;

            case BuscarNoticias.KEY_TEMA_TECNOLOGIA:
                this.tema = "Tecnologia";
                break;

            default:
                this.tema = tema;
                break;
        }
    }

    private ArrayList<Noticia> arrayListNoticias;

    public ListaNoticias(ArrayList<Noticia> arrayListNoticias, String tema) {
        this.arrayListNoticias = arrayListNoticias;
        setTema(tema);
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
