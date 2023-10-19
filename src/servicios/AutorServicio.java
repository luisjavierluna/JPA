package servicios;

import entidades.Autor;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;

public class AutorServicio {
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    LibreriaServicio p = new LibreriaServicio();
    
    public void darAltaAutor() throws Exception {
        try {
            
            EntityManager em = p.crearEntityManager();
            
            try {
                // Crear autor
                Autor autor = ingresarDatosAutor(new Autor());
                
                // Validaciones
                if (autor.getNombre().isEmpty()) {
                    throw new Exception("El campo Nombre es obligatorio");
                }
                if (existeAutor(autor.getNombre())) {
                    throw new Exception("Ya existe el autor: " + autor.getNombre());
                }

                em.getTransaction().begin();
                em.persist(autor);
                em.getTransaction().commit();
                
            } catch (Exception e) {
                throw e;
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modificarAutor(int id) throws Exception {
        EntityManager em = p.crearEntityManager();
        
        // Validaciones
        if (Integer.toString(id).isEmpty()) {
            throw new Exception("El campo Id es obligatorio");
        }
        
        try {
            Autor autor = (Autor) em
                    .createQuery("SELECT a FROM Autor a WHERE a.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            // Pasar los nuevos datos a autor
            Autor nuevoAutor = ingresarDatosAutor(autor);
            autor.setNombre(nuevoAutor.getNombre());

            em.getTransaction().begin();
            em.merge(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("No se encontraron registros con el id: " + id);
        }
            
    }
    
    public void darBajaAutor(int id) throws Exception {
        EntityManager em = p.crearEntityManager();
        
        // Validaciones
        if (Integer.toString(id).isEmpty()) {
            throw new Exception("El campo Id es obligatorio");
        }
        
        Autor autor = (Autor) em
                .createQuery("SELECT a FROM Autor a WHERE a.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        
        autor.setAlta(false);
        
        em.getTransaction().begin();
        em.merge(autor);
        em.getTransaction().commit();
    }
    
    public Autor buscarAutorPorId(int id) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        if (Integer.toString(id).isEmpty()) {
            throw new Exception("El campo Id es obligatorio");
        }
        
        try {
            Autor autor = (Autor) em
                    .createQuery("SELECT a FROM Autor a WHERE a.nombre = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            return autor;
            
        } catch (Exception e) {
            throw new Exception("No se encontraron registros con el id: " + id);
        }
        
    }
    
    public boolean existeAutor(String nombre) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        if (nombre.isEmpty()) {
            throw new Exception("El campo nombre es obligatorio");
        }
        
        try {
            Autor autor = (Autor) em
                    .createQuery("SELECT a FROM Autor a WHERE a.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
            
            return true;
            
        } catch (Exception e) {
            return false;
        }
        
    }
    
    public List<Autor> obtenerAutores() throws Exception {
        EntityManager em = p.crearEntityManager();

        List<Autor> autores = em
                .createQuery("SELECT a FROM Autor a WHERE a.alta <> false")
                .getResultList();

        if (autores.isEmpty()) 
            System.out.println("Aun no hay autores");
        
        return autores;
    }
    
    private Autor ingresarDatosAutor(Autor autor) {
        System.out.println("Ingresa el nombre del autor");
        autor.setNombre(scan.nextLine());
        
        if (!autor.isAlta()) {
            autor.setAlta(true);
        }
        
        return autor;
    }
    
}
