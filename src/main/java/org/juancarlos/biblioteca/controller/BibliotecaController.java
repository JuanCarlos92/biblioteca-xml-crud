package org.juancarlos.biblioteca.controller;

import org.juancarlos.biblioteca.model.Libro;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;
import org.juancarlos.biblioteca.service.BibliotecaService;

import java.util.List;
import java.util.Scanner;

public class BibliotecaController {

    private BibliotecaService service;
    private Scanner sc;

    // Constructor que inicializa el servicio y el Scanner
    public BibliotecaController(Scanner sc, BibliotecaRepository repository) {
        this.service = new BibliotecaService(repository);
        this.sc = sc;
    }

    // Crear un nuevo libro
    public void crearLibro(String titulo, String autor, int anio, String genero, double precio) {
        Libro libro = new Libro(0, titulo, autor, anio, genero, precio);
        service.crearLibro(libro);
    }

    // Consultar todos los libros
    public void consultarLibros() {
        List<Libro> libros = service.consultarLibros();
        for (Libro libro : libros) {
            System.out.println(libro.getId() + ". " + libro.getTitulo() + " por " + libro.getAutor());
        }
    }

    // Eliminar un libro
    public void eliminarLibro(int id) {
        service.eliminarLibro(id);
    }

    // Actualizar un libro
    public void actualizarLibro(int id, String titulo, String autor, Integer anio, String genero, Double precio) {
        Libro libroExistente = service.obtenerLibroPorId(id);
        if (libroExistente != null) {
            if (titulo != null && !titulo.isEmpty()) libroExistente.setTitulo(titulo);
            if (autor != null && !autor.isEmpty()) libroExistente.setAutor(autor);
            if (anio != null) libroExistente.setAnio(anio);
            if (genero != null && !genero.isEmpty()) libroExistente.setGenero(genero);
            if (precio != null) libroExistente.setPrecio(precio);

            service.actualizarLibro(libroExistente);
        } else {
            System.out.println("No se encontró el libro con ID: " + id);
        }
    }
}
