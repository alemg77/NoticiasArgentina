package kalitero.software.noticiasargentinas.Modelo;

import java.util.Date;

public class Comentario {
    private String usuario;
    private String opinion;
    private Date fecha;
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
}
