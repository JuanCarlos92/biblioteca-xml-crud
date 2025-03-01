package org.juancarlos.biblioteca.controller;

import org.juancarlos.biblioteca.repository.BibliotecaRepository;
import org.juancarlos.biblioteca.service.BibliotecaService;

import java.util.Scanner;

/**
 * Controlador de la biblioteca que gestiona la interacción entre el usuario y el servicio de biblioteca.
 */
public class BibliotecaController {

    private BibliotecaService service;
    private Scanner sc;

    /**
     * Constructor que inicializa el servicio de biblioteca y el Scanner.
     *
     * @param sc         Scanner para la entrada de datos del usuario.
     * @param repository Repositorio de la biblioteca que maneja la persistencia de datos.
     */
    public BibliotecaController(Scanner sc, BibliotecaRepository repository) {
        this.service = new BibliotecaService(repository);
        this.sc = sc;
    }

    /**
     * Crea una nueva colección en la biblioteca.
     */
    public void crearColeccion() {
        service.crearColeccion(sc);
    }

    /**
     * Elimina una colección existente en la biblioteca.
     */
    public void eliminarColeccion() {
        service.eliminarColeccion(sc);
    }

    /**
     * Consulta y muestra todas las colecciones disponibles en la biblioteca.
     */
    public void consultarColecciones() {
        service.consultarColecciones();
    }

    /**
     * Carga un archivo XML y agrega un libro a la biblioteca.
     */
    public void cargarYAgregarLibroXML() {
        service.cargarYAgregarLibroXML(sc);
    }

    /**
     * Crea un nuevo libro en la biblioteca.
     */
    public void crearLibro() {
        service.crearLibro(sc);
    }

    /**
     * Consulta y muestra todos los libros disponibles en la biblioteca.
     */
    public void consultarLibros() {
        service.consultarLibros();
    }

    /**
     * Consulta libros por su título.
     */
    public void consultarLibrosTitulo() {
        service.consultarLibrosTitulo(sc);
    }

    /**
     * Consulta libros por su autor.
     */
    public void consultarLibrosAutor() {
        service.consultarLibrosAutor(sc);
    }

    /**
     * Filtra y muestra libros según el año de publicación.
     */
    public void filtrarLibrosAnio() {
        service.filtrarLibrosAnio(sc);
    }

    /**
     * Actualiza la información de un libro existente en la biblioteca.
     */
    public void actualizarLibro() {
        service.actualizarLibro(sc);
    }

    /**
     * Elimina un libro de la biblioteca.
     */
    public void eliminarLibro() {
        service.eliminarLibro(sc);
    }
}
