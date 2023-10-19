package entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Libro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long isbn;
    private String titulo;
    private int anio;
    private int ejemplares;
    private int ejemplaresPrestados;
    private int ejemplaresRestantes;
    private boolean alta;
    @OneToOne
    private Autor autor;
    @OneToOne
    private Editorial editorial;

    public Libro() {
    }

    public Libro(long isbn, String titulo, int anio, int ejemplares, int ejemplaresPrestados, int ejemplaresRestantes, boolean alta, Autor autor, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.ejemplaresRestantes = ejemplaresRestantes;
        this.alta = alta;
        this.autor = autor;
        this.editorial = editorial;
    }

    public long getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnio() {
        return anio;
    }

    public int getEjemplares() {
        return ejemplares;
    }

    public int getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public int getEjemplaresRestantes() {
        return ejemplaresRestantes;
    }

    public boolean isAlta() {
        return alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setEjemplares(int ejemplares) {
        this.ejemplares = ejemplares;
    }

    public void setEjemplaresPrestados(int ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public void setEjemplaresRestantes(int ejemplaresRestantes) {
        this.ejemplaresRestantes = ejemplaresRestantes;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "\nLibro{" + "isbn=" + isbn + ", titulo=" + titulo + ", anio=" + anio + ", ejemplares=" + ejemplares + ", ejemplaresPrestados=" + ejemplaresPrestados + ", ejemplaresRestantes=" + ejemplaresRestantes + ", alta=" + alta + ", autor=" + autor + ", editorial=" + editorial + '}';
    }
    
    
}
