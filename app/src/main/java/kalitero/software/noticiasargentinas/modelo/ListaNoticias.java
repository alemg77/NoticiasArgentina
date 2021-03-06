package kalitero.software.noticiasargentinas.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Repositorio;

@Entity
public class ListaNoticias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private int posicionInicial;
    @ColumnInfo
    private String tema;
    private String origen;

    public String getTema() {
        return tema;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setTema(String tema) {
        switch ( tema ) {

            case Repositorio.KEY_TEMA_DEPORTES:
                this.tema = "Deportes";
                break;

            case Repositorio.KEY_TEMA_CIENCIA:
                this.tema = "Ciencia";
                break;

            case Repositorio.KEY_TEMA_ENTRETENIMIENTO:
                this.tema = "Espectaculos";
                break;

            case Repositorio.KEY_TEMA_NEGOCIOS:
                this.tema = "Negocios";
                break;

            case Repositorio.KEY_TEMA_SALUD:
                this.tema = "Salud";
                break;

            case Repositorio.KEY_TEMA_TECNOLOGIA:
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
