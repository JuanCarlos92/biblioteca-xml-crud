package org.juancarlos.biblioteca.service;

import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {
    private BibliotecaRepository repository;

    // Constructor con inyección de dependencia
    public BibliotecaService(BibliotecaRepository repository) {
        this.repository = repository;
    }

    // Crear un libro
    public void crearLibro(Libro libro) {
        try {
            repository.crearLibro(libro);

        } catch (RepositoryException e) {
            System.err.println("Error al crear el libro: " + e.getMessage());// Manejar la excepción
        }
    }

    // Consultar todos los libros
    public List<Libro> consultarLibros() {
        try {
            return repository.consultarLibros();

        } catch (RepositoryException e) {
            System.err.println("Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
            return new ArrayList<>();  // lista vacía
        }
    }

    // Obtener un libro por su ID
    public Libro obtenerLibroPorId(int id) {
        try {
            List<Libro> libros = repository.consultarLibros();
            for (Libro libro : libros) {
                if (libro.getId() == id) {
                    return libro;
                }
            }
        } catch (RepositoryException e) {
            System.err.println("Error al obtener el libro: " + e.getMessage());
        }
        return null;
    }

    // Eliminar un libro
    public void eliminarLibro(int id) {
        try {
            repository.eliminarLibro(id);
        } catch (RepositoryException e) {
            System.err.println("Error al eliminar el libro con id " + id + ": " + e.getMessage());
        }

    }

    // Actualizar un libro
    public void actualizarLibro(Libro libro) {
        try {
            repository.actualizarLibro(libro);
        } catch (RepositoryException e) {
            System.err.println("Error al actualizar el libro: " + e.getMessage());
        }

    }
}
