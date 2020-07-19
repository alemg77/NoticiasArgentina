package kalitero.software.noticiasargentinas.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 *  Esta clase puede empaquetar todas las noticias de todas las categorias.
 *
 *  Se utiliza para pasar las noticiasd de la splash a la main
 */
public class PaqueteNoticias implements Serializable {

    private List<ListaNoticias> paqueteCompleto;

    public PaqueteNoticias() {
        paqueteCompleto = new ArrayList<>();
    }

    public List<ListaNoticias> getPaqueteCompleto() {
        return paqueteCompleto;
    }

    public void agregarListaNoticias(ListaNoticias listaNoticias){
        paqueteCompleto.add(listaNoticias);
    }
}
