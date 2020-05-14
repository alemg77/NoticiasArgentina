package kalitero.software.noticiasargentinas.Controlador;

import org.json.JSONObject;

public interface RecepcionNoticias {
    void llegoPaqueteDeNoticias(JSONObject jsonNoticias);
    void errorPedidoNoticia();
}
