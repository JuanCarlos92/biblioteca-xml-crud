package org.juancarlos.biblioteca.repository;

import org.basex.api.client.ClientSession;
import org.juancarlos.biblioteca.conexion.BaseXConnection;
import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;

import javax.swing.*;

import org.juancarlos.biblioteca.utils.QueryBuilder;
import org.juancarlos.biblioteca.utils.XMLParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class BibliotecaRepository {

    private static final String COLLECTION_BASE = "biblioteca";
    private final ClientSession SESSION;

    // Constructor
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

    //Crear libro con ADD file.xml
    public void cargarYAgregarLibroXML() throws RepositoryException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo XML");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos XML", "xml"));

        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            System.out.println("📂 Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());

            try {
                String contenidoXML = new String(Files.readAllBytes(archivoSeleccionado.toPath()));
                contenidoXML = "<biblioteca>" + contenidoXML + "</biblioteca>";

                // Obtener el ID máximo actual
                String resultadoId = SESSION.execute(QueryBuilder.getQuery());
                int ultimoId = Integer.parseInt(resultadoId.trim());

                // Añadir los ID a los libros antes de parsearlo
                contenidoXML = agregarIdAContenidoXML(contenidoXML, ultimoId);

                // Parsear el XML a libros
                List<Libro> libros = XMLParser.parsearLibrosXML(contenidoXML);

                // Insertar los libros con el nuevo ID
                for (Libro libro : libros) {
                    String query = QueryBuilder.getInsertQuery(libro, String.valueOf(ultimoId));
                    SESSION.execute("XQUERY " + query);
                    ultimoId++;  // Incrementar el ID para el siguiente libro
                }

                System.out.println("✅ Archivo XML añadido correctamente a la base de datos.");
            } catch (Exception e) {
                throw new RepositoryException("Error al leer el archivo XML", e);
            }
        } else {
            System.out.println("⚠️ No se seleccionó ningún archivo.");
        }
    }

    // Añadir el ID al contenido XML antes de parsearlo
    private String agregarIdAContenidoXML(String contenidoXML, int ultimoId) {
        // Reemplazar cada <libro> para agregar el atributo id
        contenidoXML = contenidoXML.replaceAll("(<libro>)", "<libro id=\"" + ultimoId + "\">");
        return contenidoXML;
    }

    // Crear libro
    public void crearLibro(Libro libro) throws RepositoryException {
        try {
            // Obtener el último id del XML
            String queryId = QueryBuilder.getQuery();
            String resultadoId = SESSION.execute(queryId);
            String query = QueryBuilder.getInsertQuery(libro, resultadoId);
            SESSION.execute("XQUERY " + query);

        } catch (Exception e) {
            throw new RepositoryException("Error al insertar el libro en la base de datos", e);
        }
    }

    // Mostrar libros
    public void consultarLibros() throws RepositoryException {
        try {
            // Ejecuta una consulta XQUERY que obtiene todos los nodos de la colección
            String query = QueryBuilder.getQuerylibro();
            String resultado = SESSION.execute(query);

            // Procesar el XML resultante y lo convierte en una lista de objetos Libro
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta de libros", e);
        }
    }

    // Mostrar libros por titulo
    public void consultarLibrosTitulo(String buscarTitulo) throws RepositoryException {
        try {
            // Consulta XQuery para buscar libros cuyo título coincida
            String query = QueryBuilder.getQueryByTitulo(buscarTitulo);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por titulo", e);
        }
    }

    // Mostrar libros por Autor
    public void consultarLibrosAutor(String buscarAutor) throws RepositoryException {
        try {
            // Consulta XQuery para buscar libros cuyo autor coincida
            String query = QueryBuilder.getQueryByAutor(buscarAutor);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por autor", e);
        }
    }

    // Mostrar libros por Genero
    public void consultarLibrosGenero(String buscarGenero) throws RepositoryException {
        try {
            // Consulta XQuery para buscar libros cuyo autor coincida
            String query = QueryBuilder.getQueryByGeneno(buscarGenero);
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por género", e);
        }
    }

    // Filtrar libros por anio
    public void filtrarLibrosAnio(int anio, String mayorOMenor) throws RepositoryException {
        try {
            String query;
            if (mayorOMenor.equalsIgnoreCase("mayor")) {
                query = QueryBuilder.getQueryByAnioMayor(anio);
            } else {
                query = QueryBuilder.getQueryByAnioMenor(anio);
            }
            String resultado = SESSION.execute(query);
            List<Libro> libros = XMLParser.parsearLibrosXML(resultado);
            QueryBuilder.getListQuery(libros);
        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta por año", e);
        }
    }

    public List<Libro> consultarlibroParaActualizar() throws RepositoryException {
        try {
            String query = QueryBuilder.getQuerylibro();
            String resultado = SESSION.execute(query);
            return XMLParser.parsearLibrosXML(resultado);
        } catch (IOException e) {
            throw new RepositoryException("Error al obtener la lista de libros", e);
        }
    }

    public void actualizarLibro(Libro libro) throws RepositoryException {
        try {
            String query = QueryBuilder.getQueryActualizar(libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getAnio(), libro.getGenero());
            SESSION.execute("XQUERY " + query);

        } catch (IOException e) {
            throw new RepositoryException("Error al actualizar el libro", e);
        }
    }

    public void eliminarLibro(Libro libro) throws RepositoryException {
        try {
            String query = QueryBuilder.getQueryEliminar(libro.getId());
            SESSION.execute(query);
        } catch (IOException e) {
            throw new RepositoryException("Error al eliminar el libro", e);
        }
    }
}
