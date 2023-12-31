package servicios;

import entidades.Editorial;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;

public class EditorialServicio {
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    LibreriaServicio p = new LibreriaServicio();
    
    public Editorial darAltaEditorial() throws Exception {
        EntityManager em = p.crearEntityManager();

        try {
            // Crear Editorial
            Editorial editorial = ingresarDatosEditorial(new Editorial());

            em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();

            return editorial;

        } catch (Exception e) {
            throw e;
        }
    }
    
    public Editorial modificarEditorial(int id) throws Exception {
        EntityManager em = p.crearEntityManager();
        
        // Validaciones
        if (Integer.toString(id).isEmpty())
            throw new Exception("El campo Id es obligatorio");
        if (buscarEditorialPorId(id).getId()!= id) 
            throw new Exception("No se encontraron editoriales con el Id: " + id);
        
        try {
            Editorial editorial = (Editorial) em
                    .createQuery("SELECT e FROM Editorial e WHERE e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            // Pasar los nuevos datos a Editorial
            Editorial nuevaEditorial = ingresarDatosEditorial(editorial);
            editorial.setNombre(nuevaEditorial.getNombre());

            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
            
            return editorial;
            
        } catch (Exception e) {
            throw e;
        }       
    }
    
    public void darBajaEditorial(int id) throws Exception {
        EntityManager em = p.crearEntityManager();
        
        // Validaciones
        // Validar que el Id pueda existir en la base de datos
        if (id < 1) throw new Exception("No se ingresó un Id correcto");
        
        try {
            Editorial editorial = (Editorial) em
                    .createQuery("SELECT e FROM Editorial e WHERE e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            editorial.setAlta(false);

            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Editorial buscarEditorialPorId(int id) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        // Validar que el Id pueda existir en la base de datos
        if (id < 1) throw new Exception("No se ingresó un Id correcto");
        
        try {
            Editorial editorial = (Editorial) em
                    .createQuery("SELECT e FROM Editorial e WHERE e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            return editorial;
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<Editorial> obtenerEditoriales() throws Exception {
        EntityManager em = p.crearEntityManager();

        List<Editorial> editoriales = em
                .createQuery("SELECT e FROM Editorial e WHERE e.alta <> false")
                .getResultList();

        if (editoriales.isEmpty()) 
            System.out.println("Aun no hay editoriales");
        
        return editoriales;
    }
    
    public boolean existeEditorial(String nombre) throws Exception {
        EntityManager em = p.crearEntityManager();

        // Validaciones
        // Validar si se ingresó una string
        if (nombre.isEmpty()) throw new Exception("El campo nombre es obligatorio");
        
        try {
            Editorial editorial = (Editorial) em
                    .createQuery("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
            
            return true;
            
        } catch (Exception e) {
            return false;
        }   
    }
    
    private Editorial ingresarDatosEditorial(Editorial editorial) throws Exception {
        System.out.println("Ingresa el nombre de la Editorial");
        editorial.setNombre(scan.nextLine());
        
        // Validaciones
        if (editorial.getNombre().isEmpty()) 
            throw new Exception("El campo Nombre es obligatorio");
        if (existeEditorial(editorial.getNombre())) 
            throw new Exception("Ya existe la editorial: " + editorial.getNombre());
        
        if (editorial.getId() == 0) {
            editorial.setAlta(true);
        }
        
        return editorial;
    }
}
