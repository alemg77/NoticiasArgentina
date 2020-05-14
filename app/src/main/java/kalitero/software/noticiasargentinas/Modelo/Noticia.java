package kalitero.software.noticiasargentinas.Modelo;

import java.util.Date;

public class Noticia {
    private String fuente;
    private String autor;
    private String titulo;
    private String descripcion;
    private String urlNoticia;
    private String urlImagen;
    private Date fecha;

    public Noticia(String fuente, String autor, String titulo, String descripcion, String urlNoticia, String urlImagen, Date fecha) {
        this.fuente = fuente;
        this.autor = autor;
        this.urlNoticia = urlNoticia;
        this.urlImagen = urlImagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
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
}
