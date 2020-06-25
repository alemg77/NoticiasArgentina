package kalitero.software.noticiasargentinas.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kalitero.software.noticiasargentinas.Controlador.Dao.ComentarioDaoRoom;
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoRoom;
import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.Modelo.Noticia;

@Database(entities = {Noticia.class, Comentario.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoticiaDaoRoom noticiaDaoRoom();

    public abstract ComentarioDaoRoom comentarioDaoRoom();

    private static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null){
        INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "noticiasArgentinas-db")
                .allowMainThreadQueries()
                .build();
        }
        return INSTANCE;
    }
}
