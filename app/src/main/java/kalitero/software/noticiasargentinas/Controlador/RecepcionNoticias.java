package kalitero.software.noticiasargentinas.Controlador;

import org.json.JSONObject;

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;

public interface RecepcionNoticias {
    void llegoPaqueteDeNoticias(ListaNoticias listaNoticias);
    void mostrarDetalleDeNoticias(ListaNoticias listaNoticias);
    void errorPedidoNoticia();
}
