package kalitero.software.noticiasargentinas.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Noticia implements Serializable {
    private String fuente;
    private String autor;
    private String titulo;
    private String descripcion;
    private String urlNoticia;
    private String urlImagen;
    private Date fecha;
    private String tema;
    private String urlImagenStorage;
    private String latitud;
    private String longitud;


    public Noticia(String fuente, String autor, String titulo, String descripcion, String urlNoticia, String urlImagen, Date fecha, String tema, String urlImagenStorage, String latitud, String longitud) {
        this.fuente = fuente;
        this.autor = autor;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlNoticia = urlNoticia;
        this.urlImagen = urlImagen;
        this.fecha = fecha;
        this.tema = tema;
        this.urlImagenStorage = urlImagenStorage;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Noticia() {
    }

    public String getUrlNoticia() {
        return urlNoticia;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getFuente() {
        return fuente;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

   public String getTema() { return tema; }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getUrlImagenStorage() {
        return urlImagenStorage;
    }

    public void setUrlImagenStorage(String urlImagenStorage) {
        this.urlImagenStorage = urlImagenStorage;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
