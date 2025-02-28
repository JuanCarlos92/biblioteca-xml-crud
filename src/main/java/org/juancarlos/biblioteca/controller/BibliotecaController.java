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
    // Crear coleccion
    public void crearColeccion(){
        service.crearColeccion(sc);
    }
    // ELiminar coleccion
    public void eliminarColeccion(){
        service.eliminarColeccion(sc);
    }
    // Consultar colecciones
    public void consultarColecciones(){
        service.consultarColecciones();
    }
    // Crear libro con fichero libro.xml
    public void cargarYAgregarLibroXML(){
        service.cargarYAgregarLibroXML(sc);
    }
    // Crear libro
    public void crearLibro() {
        service.crearLibro(sc);
    }
    // Consultar libros
    public void consultarLibros() {
        service.consultarLibros();
    }
    // Consultar libros titulo
    public void consultarLibrosTitulo() {
        service.consultarLibrosTitulo(sc);
    }
    // Consultar libros Autor
    public void consultarLibrosAutor() {
        service.consultarLibrosAutor(sc);
    }
    //filtrar libros por anio
    public void filtrarLibrosAnio() {
        service.filtrarLibrosAnio(sc);
    }
    //Actualizar libro
    public void actualizarLibro() {
        service.actualizarLibro(sc);
    }
    //Eliminar libro
    public void eliminarLibro() {
        service.eliminarLibro(sc);
    }
}
