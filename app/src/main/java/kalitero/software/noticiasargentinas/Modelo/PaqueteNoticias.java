package kalitero.software.noticiasargentinas.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaqueteNoticias implements Serializable {

    private List<ListaNoticias> paqueteCompleto;

    public PaqueteNoticias() {
        paqueteCompleto = new ArrayList<>();
    }

    public List<ListaNoticias> getPaqueteCompleto() {
        return paqueteCompleto;
    }

    public void setPaqueteCompleto(List<ListaNoticias> paqueteCompleto) {
        this.paqueteCompleto = paqueteCompleto;
    }

    public void agregar_lista_noticias (ListaNoticias listaNoticias){
        paqueteCompleto.add(listaNoticias);
    }
}
