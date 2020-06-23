package kalitero.software.noticiasargentinas.Modelo;

import java.util.Date;

public class Comentario {
    private String usuario;
    private String opinion;
    private Date fecha;
    private Integer positivos;
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
}
