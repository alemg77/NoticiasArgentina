package kalitero.software.noticiasargentinas.Controlador;

import kalitero.software.noticiasargentinas.modelo.ListaNoticias;

public interface RecepcionNoticias {
    void llegoPaqueteDeNoticias(ListaNoticias listaNoticias);
    void mostrarDetalleDeNoticias(ListaNoticias listaNoticias);
    void errorPedidoNoticia();
}
