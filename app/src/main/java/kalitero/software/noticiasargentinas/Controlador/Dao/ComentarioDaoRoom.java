package kalitero.software.noticiasargentinas.Controlador.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.Comentario;

@Dao
public interface ComentarioDaoRoom {

    @Insert
    void insertAll(Comentario... comentario);

    @Insert
    void insertAll(List<Comentario> comentarioList);

    @Delete
    void delete(Comentario comentario);

    @Query("DELETE FROM Comentario")
    void deleteAll();

    @Query("SELECT * FROM Comentario")
    List<Comentario> getComentarios();

    @Update
    void update(Comentario comentario);
}
