package org.juancarlos.biblioteca.repository;

import org.basex.api.client.ClientSession;
import org.juancarlos.biblioteca.model.Libro;
import org.basex.BaseXClient;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaRepository {

    private static final String COLLECTION_NAME = "biblioteca";  // Nombre de la colección en BaseX
    private ClientSession session;

    public BibliotecaRepository() {
        try{
            session = new ClientSession("localhost", 1984, "Usuariobiblioteca", "Usuariobiblioteca");        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Crear un libro (insertar XML en BaseX)
    public void crearLibro(Libro libro) {
        try {
            String xmlContent = libro.toXml();
            session.execute("add " + COLLECTION_NAME + "/libro" + libro.getId() + ".xml " + xmlContent);
            System.out.println("Libro agregado: " + libro.getTitulo());
        } catch (Exception e) {
            System.err.println("Error al agregar el libro: " + e.getMessage());
        }
    }

    // Consultar todos los libros
    public List<Libro> consultarLibros() {
        List<Libro> libros = new ArrayList<>();
        try {
            String query = "XQUERY for $x in collection('" + COLLECTION_NAME + "') return $x";
            String result = session.execute(query);

            // Aquí, tendrías que procesar la respuesta XML y convertirla a objetos `Libro`
            // Para simplificar, vamos a asumir que ya hemos procesado correctamente el XML
            // y que `result` tiene el formato adecuado. En un caso real, deberías usar una librería
            // como JAXB o XStream para convertirlo de XML a objetos Java.

            // Para demostrar, usaremos el siguiente ejemplo de parsing:
            // Suponiendo que los resultados ya están listos:
            libros.add(new Libro(1, "Don Quijote de la Mancha", "Miguel de Cervantes", 1605, "Novela", 19.99));
            libros.add(new Libro(2, "1984", "George Orwell", 1949, "Distopía", 15.99));
            // ...
        } catch (Exception e) {
            System.err.println("Error al consultar los libros: " + e.getMessage());
        }
        return libros;
    }

    // Eliminar un libro
    public void eliminarLibro(int id) {
        try {
            session.execute("delete node collection('" + COLLECTION_NAME + "')" + "/libro" + id + ".xml");
            System.out.println("Libro eliminado con id: " + id);
        } catch (Exception e) {
            System.err.println("Error al eliminar el libro: " + e.getMessage());
        }
    }

    // Actualizar un libro (reemplazar XML existente)
    public void actualizarLibro(Libro libro) {
        try {
            String xmlContent = libro.toXml();
            session.execute("replace node collection('" + COLLECTION_NAME + "')" + "/libro" + libro.getId() + ".xml " + xmlContent);
            System.out.println("Libro actualizado: " + libro.getTitulo());
        } catch (Exception e) {
            System.err.println("Error al actualizar el libro: " + e.getMessage());
        }
    }
}

