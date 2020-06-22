package kalitero.software.noticiasargentinas.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.BuscarNoticiasAPI;

public class ListaNoticias implements Serializable {

    private int posicionInicial;
    private String tema;

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        switch ( tema ) {
            case BuscarNoticiasAPI.KEY_TEMA_DEPORTES:
                this.tema = "Deportes";
                break;

            case BuscarNoticiasAPI.KEY_TEMA_CIENCIA:
                this.tema = "Ciencia";
                break;

            case BuscarNoticiasAPI.KEY_TEMA_ENTRETENIMIENTO:
                this.tema = "Espectaculos";
                break;

            case BuscarNoticiasAPI.KEY_TEMA_NEGOCIOS:
                this.tema = "Negocios";
                break;

            case BuscarNoticiasAPI.KEY_TEMA_SALUD:
                this.tema = "Salud";
                break;

            case BuscarNoticiasAPI.KEY_TEMA_TECNOLOGIA:
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

    public ListaNoticias(List<Noticia> arrayListNoticias, String tema) {
        this.arrayListNoticias = (ArrayList<Noticia>) arrayListNoticias;
        setTema(tema);
    }


    public ListaNoticias(String tema) {
        this.arrayListNoticias = new ArrayList<>();
        setTema(tema);
    }

    public ListaNoticias() {
    }


    public void agregar (Noticia unaNoticia){
        arrayListNoticias.add(unaNoticia);
    }

    public ArrayList<Noticia> getArrayListNoticias() {
        return arrayListNoticias;
    }

    public void setArrayListNoticias(ArrayList<Noticia> arrayListNoticias) {
        this.arrayListNoticias = arrayListNoticias;
    }

    public Noticia getNoticia (int posicion) {
        return arrayListNoticias.get(posicion);
    }

    public int getPosicionInicial() {
        return posicionInicial;
    }

    public void setPosicionInicial(int posicionInicial) {
        this.posicionInicial = posicionInicial;
    }
}
