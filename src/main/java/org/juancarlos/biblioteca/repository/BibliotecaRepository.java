package org.juancarlos.biblioteca.repository;

import org.basex.api.client.ClientSession;
import org.juancarlos.biblioteca.conexion.BaseXConnection;
import org.juancarlos.biblioteca.exception.RepositoryException;
import org.juancarlos.biblioteca.model.Libro;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaRepository {

    private static final String COLLECTION_NAME = "biblioteca";
    private ClientSession session;

    // Constructor
    public BibliotecaRepository() throws RepositoryException {
        try {
            // Obtener la sesión de BaseX desde la clase BaseXConnection
            this.session = BaseXConnection.obtenerConexion();

            // Abrir la colección
            session.execute("OPEN " + COLLECTION_NAME);
        } catch (Exception e) {
            throw new RepositoryException("Error al conectar con la base de datos " + COLLECTION_NAME, e);
        }
    }

    // Crear libro
    public void crearLibro(Libro libro) throws RepositoryException {
        try {
            String query = "INSERT INTO BIBLIOTECA "
                    + "<libro>"
                    + "<titulo>" + libro.getTitulo() + "</titulo>"
                    + "<autor>" + libro.getAutor() + "</autor>"
                    + "<anio>" + libro.getAnio() + "</anio>"
                    + "<genero>" + libro.getGenero() + "</genero>"
                    + "</libro>";


            session.execute(query);
        } catch (Exception e) {
            throw new RepositoryException("Error al insertar el libro en la base de datos", e);
        }
    }

    // Mostrar libros
    public List<Libro> consultarLibros() throws RepositoryException {
        // Lista que almacenar libros
        List<Libro> libros;

        try {
            // Ejecuta una consulta XQUERY que obtiene todos los nodos de la colección
            String query = "XQUERY for $v in collection('" + COLLECTION_NAME + "') return $v";
            String result = session.execute(query);

            // Procesar el XML resultante y lo convierte en una lista de objetos Libro
            libros = parsearLibrosXML(result);

            // Mostrar cada libro
            for (Libro libro : libros) {
                System.out.println("ID: " + libro.getId());
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Año: " + libro.getAnio());
                System.out.println("Género: " + libro.getGenero());
                System.out.println("---------------------------------");
            }

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta de libros", e);
        } catch (Exception e) {
            throw new RepositoryException("Error inesperado al consultar los libros", e);
        }
        return libros;
    }

    // Convertir XML a lista de objetos Libro
    private List<Libro> parsearLibrosXML(String xml) throws RepositoryException {
        // Lista donde se almacenarán los objetos Libro convertidos desde el XML
        List<Libro> libros = new ArrayList<>();

        try {
            // Convertir el string XML a documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);

            // Obtiene todos los elementos <libro> del XML
            NodeList nodeList = doc.getElementsByTagName("libro");

            // Itera sobre todos los nodos <libro> encontrados
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Extrae los valores de las etiquetas del XML
                    int id = Integer.parseInt(getTagValue("id", element));
                    String titulo = getTagValue("titulo", element);
                    String autor = getTagValue("autor", element);
                    int anio = Integer.parseInt(getTagValue("anio", element));
                    String genero = getTagValue("genero", element);

                    // Crea un objeto Libro y lo agrega a la lista
                    libros.add(new Libro(id, titulo, autor, anio, genero));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RepositoryException("Error al procesar el XML de los libros", e);
        }
        return libros;
    }

    // Función para obtener el valor de una etiqueta por nombre
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
