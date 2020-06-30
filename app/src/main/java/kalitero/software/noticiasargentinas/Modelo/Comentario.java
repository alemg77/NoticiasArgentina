package kalitero.software.noticiasargentinas.Modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    private String documentoFirebase;

    public Comentario(String usuario, String opinion) {
        this.usuario = usuario;
        this.opinion = opinion;
        fecha = new Date();
    }

    public Comentario() {
        fecha = new Date();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDocumentoFirebase() {
        return documentoFirebase;
    }

    public void setDocumentoFirebase(String documentoFirebase) {
        this.documentoFirebase = documentoFirebase;
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
