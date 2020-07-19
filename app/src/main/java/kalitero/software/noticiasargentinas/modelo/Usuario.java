package kalitero.software.noticiasargentinas.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private String uID;
    @ColumnInfo
    private String nombre;

    public Usuario() {
    }

    public Usuario(int id, String email, String uID, String nombre) {
        this.id = id;
        this.email = email;
        this.uID = uID;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
