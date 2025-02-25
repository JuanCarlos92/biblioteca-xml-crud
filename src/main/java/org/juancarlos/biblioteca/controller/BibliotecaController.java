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

    // Crear libro
    public void crearLibro() {
        service.crearLibro(sc);
    }

    // Consultar libros
    public void consultarLibros() {
        service.consultarLibros();
    }

}
