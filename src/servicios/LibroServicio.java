package servicios;

import entidades.Autor;
import entidades.Editorial;
import entidades.Libro;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;

public class LibroServicio {
    AutorServicio as = new AutorServicio();
    EditorialServicio es = new EditorialServicio();
    
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    LibreriaServicio p = new LibreriaServicio();
    
    public Libro darAltaLibro() throws Exception {
        EntityManager em = p.crearEntityManager();

        try {
            // Crear Libro
            Libro libro = ingresarDatosLibro(new Libro());

            // Persistir la entidad en DB
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();

            return libro;

        } catch (Exception e) {
            throw e;
        }
    }
    
    public Libro modificarLibro(int isbn) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        if (Integer.toString(isbn).isEmpty()) 
            throw new Exception("El campo ISBN es obligatorio");
        if (buscarLibroPorISBN(isbn).getIsbn() != isbn) 
            throw new Exception("No se encontraron libros con el ISBN: " + isbn);

        try {
            // Traer datos del libro existente
            Libro libro = (Libro) em
                    .createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn")
                    .setParameter("isbn", isbn)
                    .getSingleResult();

            // Crear los nuevos datos y guardarlos en un nuevo libro
            Libro nuevoLibro = ingresarDatosLibro(libro);

            // Pasar los nuevos datos a Libro
            libro.setTitulo(nuevoLibro.getTitulo());
            libro.setAnio(nuevoLibro.getAnio());
            libro.setEjemplares(nuevoLibro.getEjemplares());
            libro.setEjemplaresPrestados(nuevoLibro.getEjemplaresPrestados());
            libro.setEjemplaresRestantes(nuevoLibro.getEjemplaresRestantes());
            libro.setAutor(nuevoLibro.getAutor());
            libro.setEditorial(nuevoLibro.getEditorial());

            // Persistir la entidad en DB
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();

            return libro;
            
        } catch (Exception e) {
            throw e;
        }
            
    }
    
    public void darBajaLibro(int isbn) throws Exception {
        EntityManager em = p.crearEntityManager();
        
        // Validaciones
        // Validar que el isbn ingresado exista en la BD
        if (isbn < 1) throw new Exception("No se ingresó un ISBN correcto");
        
        try {
            Libro libro = (Libro) em
                    .createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn")
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            
            as.darBajaAutor(libro.getAutor().getId());
            es.darBajaEditorial(libro.getEditorial().getId());
            
            libro.setAlta(false);
            
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Libro buscarLibroPorISBN(int isbn) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        // Validar que el isbn ingresado exista en la BD
        if (isbn < 1) throw new Exception("No se ingresó un ISBN correcto");
        
        try {
            Libro libro = (Libro) em
                    .createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn")
                    .setParameter("isbn", isbn)
                    .getSingleResult();

            return libro;
            
        } catch (Exception e) {
            throw e;
        }   
    }
    
    public List<Libro> obtenerLibros() throws Exception {
        EntityManager em = p.crearEntityManager();

        List<Libro> libros = em
                .createQuery("SELECT l FROM Libro l WHERE l.alta <> false")
                .getResultList();

        if (libros.isEmpty()) 
            System.out.println("Aun no hay Libros");
        
        return libros;
    }
    
    public Libro buscarLibroPorTitulo(String titulo) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        // Validar que la string no esté vacía
        if (titulo.isEmpty()) throw new Exception("No se ingresó un Titulo");
        
        try {
            Libro libro = (Libro) em
                    .createQuery("SELECT l FROM Libro l WHERE l.titulo = :titulo")
                    .setParameter("titulo", titulo)
                    .getSingleResult();

            return libro;
            
        } catch (Exception e) {
            throw e;
        }   
    }
    
    public Libro buscarLibroPorNombreAutor(String nombreAutor) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        // Validar que la string no esté vacía
        if (nombreAutor.isEmpty()) throw new Exception("No se ingresó un nombre");
        
        try {
            Libro libro = (Libro) em
                    .createQuery("SELECT l FROM Libro l JOIN l.autor a WHERE a.nombre = :nombreAutor")
                    .setParameter("nombreAutor", nombreAutor)
                    .getSingleResult();

            return libro;
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Libro buscarLibroPorNombreEditorial(String nombreEditorial) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        // Validar que la string no esté vacía
        if (nombreEditorial.isEmpty()) throw new Exception("No se ingresó un nombre");
        
        try {
            Libro libro = (Libro) em
                    .createQuery("SELECT l FROM Libro l JOIN l.editorial e WHERE e.nombre = :nombreEditorial")
                    .setParameter("nombreEditorial", nombreEditorial)
                    .getSingleResult();

            return libro;
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public boolean existeLibro(String titulo) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        // Validar si no se ingresó una string
        if (titulo.isEmpty()) throw new Exception("El campo Titulo es obligatorio");
        
        try {
            Libro libro = (Libro) em
                    .createQuery("SELECT l FROM Libro l WHERE l.titulo = :titulo")
                    .setParameter("titulo", titulo)
                    .getSingleResult();
            
            return true;
            
        } catch (Exception e) {
            return false;
        }    
    }
    
    private Libro ingresarDatosLibro(Libro libro) throws Exception {
        
        System.out.println("Ingresa el título del Libro");
        libro.setTitulo(scan.nextLine());
        
        // Validaciones Título
        if (libro.getTitulo().isEmpty()) throw new Exception("El campo Título es obligatorio");
        if (existeLibro(libro.getTitulo())) throw new Exception("Ya existe el libro: " + libro.getTitulo());
        
        System.out.println("Ingresa el anio del Libro");
        
        // Validación año, que se ingrese un entero en ese campo
        if (scan.hasNextInt()) libro.setAnio(scan.nextInt());
        else throw new Exception("No se ingreso un año");
            
        
        if (libro.getIsbn() == 0) {
            System.out.println("Ingresa el número total de ejemplares");
            if (scan.hasNextInt()) libro.setEjemplares(scan.nextInt());
            else throw new Exception("No se ingreso un formato adecuado de cantidad");
            
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(libro.getEjemplares());
            
            System.out.println("\nDatos del Autor");
            Autor autor = as.darAltaAutor();
            libro.setAutor(autor);

            System.out.println("\nDatos de la Editorial");
            Editorial editorial = es.darAltaEditorial();
            libro.setEditorial(editorial);
            
            libro.setAlta(true);
            
        } else {
            System.out.println("\nA continuación ingresa los datos del Autor");
            Autor autor = as.modificarAutor(libro.getAutor().getId());
            libro.setAutor(autor);

            System.out.println("\nA continuación ingresa los datos de la Editorial");
            Editorial editorial = es.modificarEditorial(libro.getEditorial().getId());
            libro.setEditorial(editorial);
        }
        scan.nextLine();
        
        return libro;
    }
}
