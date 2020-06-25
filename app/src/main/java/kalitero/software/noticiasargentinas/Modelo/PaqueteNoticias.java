package kalitero.software.noticiasargentinas.Modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PaqueteNoticias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
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

    public void agregarListaNoticias(ListaNoticias listaNoticias){
        paqueteCompleto.add(listaNoticias);
    }
}
