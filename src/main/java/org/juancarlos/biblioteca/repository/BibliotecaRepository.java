package org.juancarlos.biblioteca.repository;

import org.basex.api.client.ClientSession;
import org.juancarlos.biblioteca.conexion.BaseXConnection;
import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;
import org.juancarlos.biblioteca.utils.QueryBuilder;
import org.juancarlos.biblioteca.utils.XMLParser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.juancarlos.biblioteca.ui.FIleChooser.getJFileChooser;

/**
 * Clase que gestiona las operaciones sobre la base de datos de la biblioteca.
 * Proporciona métodos para la creación, consulta, actualización y eliminación de libros y colecciones.
 */
public class BibliotecaRepository {

    private static final String COLLECTION_BASE = "biblioteca";
    private final ClientSession SESSION;

    /**
     * Constructor que establece la conexión con BaseX y abre la colección de biblioteca.
     *
     * @throws RepositoryException si ocurre un error al conectar con la base de datos.
     */
    public BibliotecaRepository() throws RepositoryException {
        try {
            // Obtener la sesión de BaseX desde la clase BaseXConnection
            this.SESSION = BaseXConnection.obtenerConexion();
            // Abrir la colección
            SESSION.execute("OPEN " + COLLECTION_BASE);
        } catch (Exception e) {
            throw new RepositoryException("Error al conectar con la base de datos " + COLLECTION_BASE, e);
        }
    }

    /**
     * Crea una nueva colección en la base de datos.
     *
     * @param tipoGenero Género de la colección a crear.
     * @throws RepositoryException si ocurre un error al crear la colección.
     */
    public void crearColeccion(String tipoGenero) throws RepositoryException {
        try {
            String queryVerificar = QueryBuilder.getVerificarGenero(tipoGenero);
            String resultado = SESSION.execute(queryVerificar);

            if (Integer.parseInt(resultado.trim()) > 0) {
                System.out.println("La colección ya existe");
                return;
            }
            String queryInsertColeccion = QueryBuilder.getInsertColeccion(tipoGenero);
            SESSION.execute(queryInsertColeccion);
            System.out.println("✅ ✅ Colección creada con éxito! ✅ ✅ ");
        } catch (Exception e) {
            throw new RepositoryException("Error al crear la nueva colección", e);
        }
    }

    /**
     * Elimina una colección de la base de datos.
     *
     * @param tipoGenero Género de la colección a eliminar.
     * @throws RepositoryException si ocurre un error al eliminar la colección.
     */
    public void eliminarColeccion(String tipoGenero) throws RepositoryException {
        try {
            String queryVerificar = QueryBuilder.getVerificarGenero(tipoGenero);
            String resultado = SESSION.execute(queryVerificar);

            if (Integer.parseInt(resultado.trim()) == 0) {
                System.out.println("Esta colección no existe");
                return;
            }
            String queryDeleteColeccion = QueryBuilder.getDeleteColeccion(tipoGenero);
            SESSION.execute(queryDeleteColeccion);
            System.out.println("✅ ✅ Colección eliminada con éxito! ✅ ✅ ");
        } catch (Exception e) {
            throw new RepositoryException("Error al eliminar la colección", e);
        }
    }

    /**
     * Consulta y muestra todas las colecciones de la biblioteca.
     *
     * @throws RepositoryException si ocurre un error durante la consulta.
     */
    public void consultarColecciones() throws RepositoryException {
        try {
            String query = QueryBuilder.getListColeccion();
            String resultado = SESSION.execute(query);

            // Verificar si el resultado no está vacío
            if (resultado != null && !resultado.trim().isEmpty()) {
                // Convertir el resultado en una lista separando por saltos de línea o espacios
                List<String> generos = new ArrayList<>(Arrays.asList(resultado.split("\n")));

                System.out.println("📖  LISTA DE COLECCIONES  📖");
                // Mostrar los géneros en consola numerados
                int index = 1;
                for (String genero : generos) {
                    System.out.println(index + ". " + genero.trim());
                    index++;
                }
            } else {
                System.out.println("No se encontraron géneros en la biblioteca.");
            }

        } catch (Exception e) {
            throw new RepositoryException("Error al consultar las colecciones", e);
        }

    }

    /**
     * Carga un archivo XML desde el sistema de archivos y lo inserta en la base de datos.
     *
     * @param tipoGenero Género al que pertenece el libro.
     * @throws RepositoryException si ocurre un error al agregar el libro.
     * @throws IOException         si ocurre un error al leer el archivo.
     */
    public void cargarYAgregarLibroXML(String tipoGenero) throws RepositoryException, IOException {
        String queryVerificar = QueryBuilder.getVerificarGenero(tipoGenero);
        String resultado = SESSION.execute(queryVerificar);

        if (Integer.parseInt(resultado.trim()) == 0) {
            System.out.println("Esta colección no existe, primero crea la coleccion con ese género");
            return;
        }

        // Si el género existe o se crea, entonces proceder a mostrar el JFileChooser
        JFileChooser fileChooser = getJFileChooser();

        int resultadoFile = fileChooser.showOpenDialog(null);
        if (resultadoFile == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            System.out.println("📂 Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());

            try {
                // Leer contenido del archivo seleccionado
                String contenidoXML = new String(Files.readAllBytes(archivoSeleccionado.toPath()));

                // Obtener el ID máximo actual
                String resultadoId = SESSION.execute(QueryBuilder.getIDLibro());
                int ultimoId = resultadoId.trim().isEmpty() ? 0 : Integer.parseInt(resultadoId.trim());
                ultimoId++;

                // Asignar ID a cada libro dentro del XML
                contenidoXML = agregarIdAContenidoXML(contenidoXML, ultimoId);

                SESSION.execute("XQUERY insert node " + contenidoXML + "into //biblioteca/genero[@tipo='" + tipoGenero + "']");

                System.out.println("✅ ✅ Archivo XML añadido correctamente a la base de datos ✅ ✅ ");

            } catch (Exception e) {
                throw new RepositoryException("Error al leer el archivo XML", e);

            }
        } else {
            System.out.println("⚠️ No se seleccionó ningún archivo.");
        }
    }

    /**
     * Agrega un identificador único a cada libro en el XML.
     *
     * @param contenidoXML Contenido del archivo XML.
     * @param ultimoId     Último ID registrado.
     * @return Contenido XML con los ID agregados.
     */
    private String agregarIdAContenidoXML(String contenidoXML, int ultimoId) {
        contenidoXML = contenidoXML.replaceAll("(<libro>)", "<libro id=\"" + ultimoId + "\">");

        return contenidoXML;
    }

    /**
     * Crea un nuevo libro en la base de datos.
     *
     * @param libro      Objeto de tipo Libro que contiene los datos del libro a agregar.
     * @param tipoGenero Género al que pertenece el libro.
     * @throws RepositoryException si ocurre un error al insertar el libro.
     */
    public void crearLibro(Libro libro, String tipoGenero) throws RepositoryException {
        try {
            String queryVerificar = QueryBuilder.getVerificarGenero(tipoGenero);
            String resultado = SESSION.execute(queryVerificar);

            if (Integer.parseInt(resultado.trim()) == 0) {
                System.out.println("Esta colección no existe, primero crea la coleccion con ese género");
                return;
            }
            // Obtener el último id del XML
            String queryId = QueryBuilder.getIDLibro();
            String resultadoId = SESSION.execute(queryId);

            String query = QueryBuilder.getInsertLibro(libro, resultadoId);
            SESSION.execute(query);
            System.out.println("✅ ✅ Libro creado con éxito! ✅ ✅ ");

        } catch (Exception e) {
            throw new RepositoryException("Error al insertar el libro en la base de datos", e);
        }
    }

    /**
     * Consulta y muestra todos los libros de la base de datos.
     *
     * @throws RepositoryException si ocurre un error durante la consulta.
     */
    public void consultarLibros() throws RepositoryException {
        try {
            String query = QueryBuilder.getAllBiblioteca();
            String resultado = SESSION.execute(query);

            // Procesar el XML resultante y lo convierte en una lista de objetos Libro
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListLibros(libros);

        } catch (Exception e) {
            throw new RepositoryException("Error al procesar la consulta de libros", e);
        }
    }

    /**
     * Consulta y muestra libros por título.
     *
     * @param buscarTitulo Título del libro a buscar.
     * @throws RepositoryException si ocurre un error durante la consulta.
     */
    public void consultarLibrosTitulo(String buscarTitulo) throws RepositoryException {
        try {
            String query = QueryBuilder.getQueryTitulo(buscarTitulo);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListLibros(libros);

        } catch (Exception e) {
            throw new RepositoryException("Error al procesar la consulta por titulo", e);
        }
    }

    /**
     * Consulta y muestra libros por autor.
     *
     * @param buscarAutor Nombre del autor de los libros a buscar.
     * @throws RepositoryException si ocurre un error durante la consulta.
     */
    public void consultarLibrosAutor(String buscarAutor) throws RepositoryException {
        try {
            String query = QueryBuilder.getQueryAutor(buscarAutor);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListLibros(libros);

        } catch (Exception e) {
            throw new RepositoryException("Error al procesar la consulta por autor", e);
        }
    }

    /**
     * Filtra y muestra los libros según su año de publicación.
     *
     * @param anio        Año de referencia para el filtrado.
     * @param mayorOMenor Define si se buscan libros publicados antes o después del año indicado.
     * @throws RepositoryException si ocurre un error durante la consulta.
     */
    public void filtrarLibrosAnio(int anio, String mayorOMenor) throws RepositoryException {
        try {
            String query;
            if (mayorOMenor.equalsIgnoreCase("mayor")) {
                query = QueryBuilder.getQueryAnioMayor(anio);
            } else {
                query = QueryBuilder.getQueryAnioMenor(anio);
            }
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListLibros(libros);
        } catch (Exception e) {
            throw new RepositoryException("Error al procesar la consulta por año", e);
        }
    }

    /**
     * Obtiene todos los libros de la biblioteca y los convierte en una lista.
     *
     * @return Lista de libros.
     * @throws RepositoryException Si ocurre un error al obtener la lista de libros.
     */
    public List<Libro> consultarNombreLibro() throws RepositoryException {
        try {
            String query = QueryBuilder.getAllBiblioteca();
            String resultado = SESSION.execute(query);
            return XMLParser.parsearLibrosXML(resultado);
        } catch (Exception e) {
            throw new RepositoryException("Error al obtener la lista de libros", e);
        }
    }

    /**
     * Actualiza los datos de un libro en la biblioteca.
     *
     * @param libro El libro con los nuevos datos.
     * @throws RepositoryException Si ocurre un error al actualizar el libro.
     */
    public void actualizarLibro(Libro libro) throws RepositoryException {
        try {
            String query = QueryBuilder.getActualizarLibro(libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getAnio());
            SESSION.execute("XQUERY " + query);
            System.out.println("✅ ✅ Libro actualizado correctamente ✅ ✅ ");

        } catch (Exception e) {
            throw new RepositoryException("Error al actualizar el libro", e);
        }
    }

    /**
     * Elimina un libro de la biblioteca.
     *
     * @param libro El libro a eliminar.
     * @throws RepositoryException Si ocurre un error al eliminar el libro.
     */
    public void eliminarLibro(Libro libro) throws RepositoryException {
        try {
            String query = QueryBuilder.getEliminarLibro(libro.getId());
            SESSION.execute(query);
            System.out.println("✅ ✅ Libro eliminado con éxito ✅ ✅");
        } catch (Exception e) {
            throw new RepositoryException("Error al eliminar el libro", e);
        }
    }
}


