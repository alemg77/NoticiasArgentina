package kalitero.software.noticiasargentinas.Modelo;

public class Comentario {
    private String usuario;
    private String opinion;

    public Comentario(String usuario, String opinion) {
        this.usuario = usuario;
        this.opinion = opinion;
    }

    public Comentario() {
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
