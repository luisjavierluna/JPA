import servicios.AutorServicio;
import servicios.EditorialServicio;
import servicios.LibroServicio;

public class main {

    public static void main(String[] args) throws Exception {
        
        AutorServicio as = new AutorServicio();
        EditorialServicio es = new EditorialServicio();
        LibroServicio ls = new LibroServicio();
        
        
        // AUTOR
            // as.darAltaAutor();
            // System.out.println(as.buscarAutorPorId(4));
            // System.out.println(as.obtenerAutores());
            // as.modificarAutor(12);
            // as.darBajaAutor(4);
        
        // EDITORIAL
            // es.darAltaEditorial();
            // System.out.println(es.buscarEditorialPorId(2));
            // System.out.println(es.obtenerEditoriales());
            // es.modificarEditorial(1);
            // es.darBajaEditorial(0);
            
        // LIBRO
            // ls.darAltaLibro();
            // System.out.println(ls.buscarLibroPorId(4));
            // System.out.println(ls.obtenerLibros());
            // ls.modificarLibro(3);
            // ls.darBajaLibro(2);
            
    }
    
}
