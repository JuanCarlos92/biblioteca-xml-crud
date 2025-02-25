package org.juancarlos.biblioteca.menu;

import org.juancarlos.biblioteca.controller.BibliotecaController;
import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;


import java.io.IOException;
import java.util.Scanner;

public class Menu {

    // Inicia la ejecución del menú principal
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
                case CREAR_LIBRO:
                    bibliotecaController.crearLibro();
                    break;
                case CONSULTAR_LIBROS:
                    bibliotecaController.consultarLibros();
                    break;
                case ELIMINAR_LIBRO:

                    break;
                case ACTUALIZAR_LIBRO:

                    break;
                case SALIR:
                    salir = true;
                    break;
            }
        }
        sc.close();
    }

    // Solicita al usuario que seleccione una opción del menú principal
    private EnumMenu validarOpcionPrincipal(Scanner sc) {
        System.out.print("Selecciona una opción: ");
        int codigo = leerEntero(sc);
        return EnumMenu.obtenerPorCodigo(codigo);
    }

    // Lee un valor entero introducido por el usuario mediante Scanner.
    private int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Ingrese un número válido:");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer
        return valor;
    }

}
