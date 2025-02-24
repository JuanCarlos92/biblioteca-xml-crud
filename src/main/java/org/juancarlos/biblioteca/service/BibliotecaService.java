package org.juancarlos.biblioteca.service;

import org.juancarlos.biblioteca.model.Libro;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;
import java.util.List;

public class BibliotecaService {

    private BibliotecaRepository repository = new BibliotecaRepository();

    // Crear un libro
    public void crearLibro(Libro libro) {
        repository.crearLibro(libro);
    }

    // Consultar todos los libros
    public List<Libro> consultarLibros() {
        return repository.consultarLibros();
    }

    // Eliminar un libro
    public void eliminarLibro(int id) {
        repository.eliminarLibro(id);
    }

    // Actualizar un libro
    public void actualizarLibro(Libro libro) {
        repository.actualizarLibro(libro);
    }
}
