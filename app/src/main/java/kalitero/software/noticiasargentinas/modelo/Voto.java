package kalitero.software.noticiasargentinas.modelo;

public class Voto {
    private String usuario;
    private Boolean positivo;

    public Voto() {
    }

    public Voto(String usuario, Boolean positivo) {
        this.usuario = usuario;
        this.positivo = positivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Boolean getPositivo() {
        return positivo;
    }

    public void setPositivo(Boolean positivo) {
        this.positivo = positivo;
    }
}
