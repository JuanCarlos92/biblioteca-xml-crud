package org.juancarlos.biblioteca.controller;


import org.juancarlos.biblioteca.model.Libro;
import org.juancarlos.biblioteca.service.BibliotecaService;

import java.util.List;

public class BibliotecaController {

    private BibliotecaService service = new BibliotecaService();

    // Crear un nuevo libro
    public void crearLibro(String titulo, String autor, int anio, String genero, double precio) {
        Libro libro = new Libro(0, titulo, autor, anio, genero, precio);  // id se genera automáticamente en la base de datos
        service.crearLibro(libro);
    }

    // Consultar todos los libros
    public void consultarLibros() {
        List<Libro> libros = service.consultarLibros();
        for (Libro libro : libros) {
            System.out.println(libro.getTitulo() + " por " + libro.getAutor());
        }
    }

    // Eliminar un libro
    public void eliminarLibro(int id) {
        service.eliminarLibro(id);
    }

    // Actualizar un libro
    public void actualizarLibro(int id, String titulo, String autor, int anio, String genero, double precio) {
        Libro libro = new Libro(id, titulo, autor, anio, genero, precio);
        service.actualizarLibro(libro);
    }
}

