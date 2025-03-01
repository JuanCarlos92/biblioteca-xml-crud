package org.juancarlos.biblioteca.menu;

import org.juancarlos.biblioteca.controller.BibliotecaController;
import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;

import java.io.IOException;
import java.util.Scanner;

/**
 * Clase que representa el menú principal de la aplicación de la biblioteca.
 * Permite interactuar con el usuario y gestionar la ejecución de las distintas
 * funcionalidades de la biblioteca a través de un controlador.
 */
public class Menu {
    /**
     * Inicia la ejecución del menú principal y gestiona la interacción con el usuario.
     *
     * @throws RepositoryException Si ocurre un error al acceder al repositorio.
     * @throws IOException         Si ocurre un error de entrada/salida.
     */
    public void iniciar() throws RepositoryException, IOException {
        Scanner sc = new Scanner(System.in);
        BibliotecaRepository repository = new BibliotecaRepository();
        BibliotecaController bibliotecaController = new BibliotecaController(sc, repository);

        boolean salir = false;

        while (!salir) {
            EnumMenu.mostrarOpcionesMenu();
            EnumMenu opcionPrincipal = validarOpcionPrincipal(sc);

            if (opcionPrincipal == null) {
                System.out.println("Opción no válida.");
                continue;
            }
            switch (opcionPrincipal) {
                case CREAR_COLECCION:
                    bibliotecaController.crearColeccion();
                    break;
                case ELIMINAR_COLECCION:
                    bibliotecaController.eliminarColeccion();
                    break;
                case CONSULTAR_COLECCIONES:
                    bibliotecaController.consultarColecciones();
                    break;
                case CREAR_ADD_LIBRO:
                    bibliotecaController.cargarYAgregarLibroXML();
                    break;
                case CREAR_LIBRO:
                    bibliotecaController.crearLibro();
                    break;
                case CONSULTAR_LIBROS:
                    bibliotecaController.consultarLibros();
                    break;
                case CONSULTAR_LIBROS_TITULO:
                    bibliotecaController.consultarLibrosTitulo();
                    break;
                case CONSULTAR_LIBROS_AUTOR:
                    bibliotecaController.consultarLibrosAutor();
                    break;
                case FILTRAR_LIBROS_ANIO:
                    bibliotecaController.filtrarLibrosAnio();
                    break;
                case ACTUALIZAR_LIBRO:
                    bibliotecaController.actualizarLibro();
                    break;
                case ELIMINAR_LIBRO:
                    bibliotecaController.eliminarLibro();
                    break;
                case SALIR:
                    salir = true;
                    break;
            }
        }
        sc.close();
    }

    /**
     * Solicita al usuario que seleccione una opción del menú principal.
     *
     * @param sc Scanner para leer la entrada del usuario.
     * @return La opción seleccionada del menú o null si la entrada no es válida.
     */
    private EnumMenu validarOpcionPrincipal(Scanner sc) {
        System.out.print("📝 Selecciona una opción: ");
        int codigo = leerEntero(sc);
        return EnumMenu.obtenerPorCodigo(codigo);
    }

    /**
     * Lee un valor entero introducido por el usuario mediante Scanner.
     *
     * @param sc Scanner para leer la entrada del usuario.
     * @return El número entero ingresado por el usuario.
     */
    private int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("⚠️ Ingrese un número válido:");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

}
