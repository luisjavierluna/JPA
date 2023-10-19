package entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Autor implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private boolean alta;

    public Autor() {
    }

    public Autor(int id, String nombre, boolean alta) {
        this.id = id;
        this.nombre = nombre;
        this.alta = alta;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    @Override
    public String toString() {
        return "\nAutor{" + "id=" + id + ", nombre=" + nombre + ", alta=" + alta + '}';
    }
    
}
