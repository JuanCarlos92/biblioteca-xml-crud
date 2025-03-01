package org.juancarlos.biblioteca.service;

import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;
import org.juancarlos.biblioteca.repository.BibliotecaRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Servicio que gestiona la lógica de negocio de la biblioteca.
 * Permite administrar colecciones y libros.
 */
public class BibliotecaService {
    private BibliotecaRepository repository;

    /**
     * Constructor con inyección de dependencia.
     *
     * @param repository Repositorio de la biblioteca.
     */
    public BibliotecaService(BibliotecaRepository repository) {
        this.repository = repository;
    }

    /**
     * Crea una nueva colección en la biblioteca.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void crearColeccion(Scanner sc) {
        System.out.println("📖  CREAR COLECCION  📖");

        System.out.print("Introduzca el tipo de genero para crear la coleccion: ");
        String tipoGenero = sc.nextLine();

        try {
            repository.crearColeccion(tipoGenero);
        } catch (RepositoryException e) {
            System.err.println("❌ Error al crear la coleccion: " + e.getMessage());
        }
    }

    /**
     * Elimina una colección existente en la biblioteca.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void eliminarColeccion(Scanner sc) {
        System.out.println("📖  ELIMINAR COLECCION  📖");

        System.out.println("Introduzca el tipo de genero para eliminar la coleccion: ");
        String tipoGenero = sc.nextLine();

        try {
            repository.eliminarColeccion(tipoGenero);
        } catch (RepositoryException e) {
            System.err.println("❌ Error al eliminar la coleccion: " + e.getMessage());
        }
    }

    /**
     * Consulta todas las colecciones de la biblioteca.
     */
    public void consultarColecciones() {
        try {
            repository.consultarColecciones();

        } catch (RepositoryException e) {
            System.err.println("❌ Error al consultar las colecciones: " + e.getMessage()); // Manejar la excepción
        }
    }

    /**
     * Carga y agrega un libro desde un archivo XML.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void cargarYAgregarLibroXML(Scanner sc) {
        System.out.println("📖  AÑADIR LIBRO desde un .XML 📖");
        System.out.print("Introduzca el tipo de genero del libro: ");
        String tipoGenero = sc.nextLine();

        try {
            repository.cargarYAgregarLibroXML(tipoGenero);
        } catch (Exception e) {
            System.err.println("❌ Error al crear el ADD libro.xml: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo libro en la biblioteca.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void crearLibro(Scanner sc) {
        System.out.println("📖  AÑADIR LIBRO  📖");

        System.out.print("Introduce el título del libro: ");
        String titulo = sc.nextLine();

        System.out.print("Introduce el autor del libro: ");
        String autor = sc.nextLine();

        int anio = 0;
        anio = getAnio(sc, anio);

        System.out.print("Introduce el género del libro: ");
        String tipoGenero = sc.nextLine();

        Libro libro = new Libro(0, titulo, autor, anio, tipoGenero);

        try {
            repository.crearLibro(libro, tipoGenero);
        } catch (RepositoryException e) {
            System.err.println("❌ Error al crear el libro: " + e.getMessage());
        }
    }

    /**
     * Consulta todos los libros de la biblioteca.
     */
    public void consultarLibros() {
        try {
            System.out.println("📖  LISTA DE LIBROS  📖");
            repository.consultarLibros();

        } catch (RepositoryException e) {
            System.err.println("❌ Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
        }
    }

    /**
     * Consulta los libros de la biblioteca con el titulo indicado.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void consultarLibrosTitulo(Scanner sc) {
        try {
            System.out.print("Introduce el título del libro a buscar: ");
            String buscarTitulo = sc.nextLine();

            System.out.println("📖  LISTA DE LIBROS POR TÍTULO 📖");
            repository.consultarLibrosTitulo(buscarTitulo);

        } catch (RepositoryException e) {
            System.err.println("❌ Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
        }
    }

    /**
     * Consulta los libros de la biblioteca con el autor indicado.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void consultarLibrosAutor(Scanner sc) {
        try {
            System.out.print("Introduce el autor del libro a buscar: ");
            String buscarAutor = sc.nextLine();

            System.out.println("📖  LISTA DE LIBROS POR AUTOR 📖");
            repository.consultarLibrosAutor(buscarAutor);

        } catch (RepositoryException e) {
            System.err.println("❌ Error al consultar los libros: " + e.getMessage()); // Manejar la excepción
        }
    }

    /**
     * Filtra los libros de la biblioteca con el año indicado.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void filtrarLibrosAnio(Scanner sc) {
        int anio = 0;
        anio = getAnio(sc, anio);
        System.out.print("¿Quieres mostrar los libros mayor a esa fecha o menor?: ");
        String mayorOMenor = sc.nextLine();

        try {
            System.out.println("📖  LISTA DE LIBROS CON EL FILTRO APLICADO 📖");
            repository.filtrarLibrosAnio(anio, mayorOMenor);

        } catch (RepositoryException e) {
            System.err.println("❌ Error al aplicar el filtro indicado: " + e.getMessage());
        }
    }

    /**
     * Actualiza un libro en la biblioteca.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void actualizarLibro(Scanner sc) {
        try {
            // Obtener y mostrar los libros disponibles
            List<Libro> libros = repository.consultarNombreLibro();
            if (libros.isEmpty()) {
                System.out.println("⚠️ No hay libros disponibles.");
                return;
            }

            System.out.println("📚 LISTA DE LIBROS DISPONIBLES 📚");
            for (Libro libro : libros) {
                System.out.println("ID: " + libro.getId() + " | Título: " + libro.getTitulo());
            }

            // Pedir el ID del libro a actualizar
            System.out.print("Ingrese el ID del libro a actualizar: ");
            int idLibro = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer del scanner

            // Buscar el libro
            Libro libroActualizar = libros.stream()
                    .filter(libro -> libro.getId() == idLibro)  // Aquí comparamos directamente como enteros
                    .findFirst()
                    .orElse(null);

            if (libroActualizar == null) {
                System.out.println("❌ No se encontró un libro con el ID proporcionado.");
                return;
            }

            // Pedir los datos a actualizar
            System.out.println("📖 ACTUALIZAR LIBRO (Deja en blanco para no modificar)");

            System.out.print("Nuevo título: ");
            String nuevoTitulo = sc.nextLine().trim();
            if (nuevoTitulo.isEmpty()) nuevoTitulo = libroActualizar.getTitulo();

            System.out.print("Nuevo autor: ");
            String nuevoAutor = sc.nextLine().trim();
            if (nuevoAutor.isEmpty()) nuevoAutor = libroActualizar.getAutor();

            System.out.print("Nuevo año: ");
            String nuevoAnioStr = sc.nextLine().trim();
            int nuevoAnio = nuevoAnioStr.isEmpty() ? libroActualizar.getAnio() : Integer.parseInt(nuevoAnioStr);

            System.out.print("Nuevo género: ");
            String nuevoGenero = sc.nextLine().trim();
            if (nuevoGenero.isEmpty()) nuevoGenero = libroActualizar.getGenero();

            // Crear un nuevo objeto Libro con los datos actualizados
            Libro libroModificado = new Libro(
                    libroActualizar.getId(),
                    nuevoTitulo,
                    nuevoAutor,
                    nuevoAnio,
                    nuevoGenero
            );
            repository.actualizarLibro(libroModificado);
        } catch (RepositoryException e) {
            System.err.println("❌ Error al actualizar el libro: " + e.getMessage());
        }
    }

    /**
     * Elimina un libro en la biblioteca.
     *
     * @param sc Scanner para la entrada de datos del usuario.
     */
    public void eliminarLibro(Scanner sc) {
        try {
            // Obtener y mostrar los libros disponibles
            List<Libro> libros = repository.consultarNombreLibro();
            if (libros.isEmpty()) {
                System.out.println("⚠️ No hay libros disponibles.");
                return;
            }

            System.out.println("📚 LISTA DE LIBROS DISPONIBLES 📚");
            for (Libro libro : libros) {
                System.out.println("ID: " + libro.getId() + " | Título: " + libro.getTitulo());
            }

            // Pedir el ID del libro a eliminar
            System.out.print("Ingrese el ID del libro a eliminar: ");
            while (!sc.hasNextInt()) {
                System.out.println("❌ Entrada inválida. Introduzca un número válido.");
                sc.next(); // Limpiar entrada incorrecta
            }
            int idLibro = sc.nextInt();
            sc.nextLine();

            // Buscar el libro
            Libro libroEliminar = libros.stream()
                    .filter(libro -> libro.getId() == idLibro)
                    .findFirst()
                    .orElse(null);

            if (libroEliminar == null) {
                System.out.println("❌ No se encontró un libro con el ID proporcionado.");
                return;
            }

            System.out.println("📖 ¿Está seguro de que desea eliminar el libro: " + libroEliminar.getTitulo() + "? (S/N)");
            String confirmacion = sc.nextLine().trim().toLowerCase();

            if (!confirmacion.equals("s")) {
                System.out.println("🚫 Operación cancelada.");
                return;
            }
            repository.eliminarLibro(libroEliminar);

        } catch (RepositoryException e) {
            System.err.println("❌ Error al eliminar el libro: " + e.getMessage());
        }
    }

    /**
     * Solicita y valida un año de publicación ingresado por el usuario.
     *
     * @param sc Scanner para la entrada de datos.
     * @return Año validado.
     */
    private int getAnio(Scanner sc, int anio) {
        boolean anioValido = false;
        while (!anioValido) {
            System.out.print("Introduce el año de publicación (máximo 4 cifras): ");
            if (sc.hasNextInt()) {
                anio = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea
                if (anio > 0 && anio < 10000) {
                    anioValido = true;
                } else {
                    System.out.println("❌ Error: El año debe tener como máximo 4 cifras");
                }
            } else {
                System.out.println("❌ Error: Debes ingresar un número válido.");
                sc.next(); // Descartar la entrada incorrecta
            }
        }
        return anio;
    }
}
