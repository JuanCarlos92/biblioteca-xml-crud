package org.juancarlos.biblioteca.service;

import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;

import java.util.List;
import java.util.Scanner;

public class BibliotecaService {
    private BibliotecaRepository repository;

    // Constructor con inyección de dependencia
    public BibliotecaService(BibliotecaRepository repository) {
        this.repository = repository;
    }

    // Crear un libro
    public void crearLibro(Scanner sc) {
        System.out.println("Crear un nuevo libro");

        System.out.print("Introduce el título del libro: ");
        String titulo = sc.nextLine();

        System.out.print("Introduce el autor del libro: ");
        String autor = sc.nextLine();

        System.out.print("Introduce el año de publicación: ");
        int anio = sc.nextInt();
        sc.nextLine();

        System.out.print("Introduce el género del libro: ");
        String genero = sc.nextLine();

        Libro libro = new Libro(0, titulo, autor, anio, genero);

        try {
            repository.crearLibro(libro);
            System.out.println("Libro creado con éxito.");
        } catch (Exception e) {
            System.err.println("Error al crear el libro: " + e.getMessage());
        }
    }

    // Consultar todos los libros
    public void consultarLibros() {
        try {
            System.out.println("--> LISTA DE LIBROS <--");
            repository.consultarLibros();

        } catch (RepositoryException e) {
            System.err.println("Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
        }
    }
}
