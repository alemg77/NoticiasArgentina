package kalitero.software.noticiasargentinas.Modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import kalitero.software.noticiasargentinas.util.Converter;

@Entity
@TypeConverters(Converter.class)
public class Comentario {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String usuario;
    @ColumnInfo
    private String opinion;
    @ColumnInfo
    private Date fecha;
    @ColumnInfo
    private Integer positivos;
    @ColumnInfo
    private Integer negativos;

    public Comentario(String usuario, String opinion) {
        this.usuario = usuario;
        this.opinion = opinion;
        fecha = new Date();
        positivos = 0;
        negativos = 0;
    }

    public Comentario() {
        fecha = new Date();
        positivos = 0;
        negativos = 0;
    }



    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getPositivos() {
        return positivos;
    }

    public void setPositivos(Integer positivos) {
        this.positivos = positivos;
    }

    public Integer getNegativos() {
        return negativos;
    }

    public void setNegativos(Integer negativos) {
        this.negativos = negativos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
