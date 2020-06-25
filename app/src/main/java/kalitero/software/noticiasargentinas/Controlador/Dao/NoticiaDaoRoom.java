package kalitero.software.noticiasargentinas.Controlador.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;

@Dao
public interface NoticiaDaoRoom {

    @Insert
    void insertAll(Noticia... noticia);

    @Insert
    void insertAll(List<Noticia>listaNoticias);

   // @Insert
    //void insertAll(List<ListaNoticias> listaDeListaNoticias);

    @Delete
    void delete(Noticia noticia);

    @Query("SELECT * FROM Noticia")
    List<Noticia> getNoticias();

    @Query("SELECT * FROM Noticia WHERE tema = :tema")
    List<Noticia> getNoticiasTema(String tema);
}
