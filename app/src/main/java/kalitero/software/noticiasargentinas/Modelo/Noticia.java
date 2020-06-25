package kalitero.software.noticiasargentinas.Modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kalitero.software.noticiasargentinas.util.Converter;

@Entity
@TypeConverters(Converter.class)

public class Noticia implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String fuente;
    @ColumnInfo
    private String autor;
    @ColumnInfo
    private String titulo;
    @ColumnInfo
    private String descripcion;
    @ColumnInfo
    private String urlNoticia;
    @ColumnInfo
    private String urlImagen;
    @ColumnInfo()
    private Date fecha;
    @ColumnInfo
    private String tema;
    @ColumnInfo
    private String urlImagenStorage;
    @ColumnInfo
    private String latitud;
    @ColumnInfo
    private String longitud;
    @ColumnInfo
    private String documentoFirebase;

    public String getDocumentoFirebase() {
        return documentoFirebase;
    }

    public void setDocumentoFirebase(String documentoFirebase) {
        this.documentoFirebase = documentoFirebase;
    }

    public Noticia() {
    }

    public Noticia(String fuente, String autor, String titulo, String descripcion, String urlNoticia, String urlImagen, Date fecha, String tema) {
        this.fuente = fuente;
        this.autor = autor;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlNoticia = urlNoticia;
        this.urlImagen = urlImagen;
        this.fecha = fecha;
        this.tema = tema;
        this.urlImagenStorage = null;
        this.latitud = null;
        this.longitud = null;
    }

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

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUrlNoticia(String urlNoticia) {
        this.urlNoticia = urlNoticia;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void setFecha(Date fecha) {
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

    public void setAutor(String autor) {
        this.autor = autor;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
