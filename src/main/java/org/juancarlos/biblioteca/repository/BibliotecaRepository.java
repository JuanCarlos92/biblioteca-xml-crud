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

    // Crear un libro (insertar XML en BaseX)
    public void crearLibro(Libro libro) throws RepositoryException {
        try {
            // Convierte el objeto libro a un string XML
            String xmlContent = libro.toXml();

            // Ejecuta el comando de BaseX para agregar el XML
            session.execute("add " + COLLECTION_NAME + "/libro" + libro.getId() + ".xml " + xmlContent);
            System.out.println("Libro agregado: " + libro.getTitulo());

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar el libro XML", e);
        } catch (Exception e) {
            throw new RepositoryException("Error inesperado al agregar el libro", e);
        }
    }

    // Consultar todos los libros
    public List<Libro> consultarLibros() throws RepositoryException {
        // Lista que almacenará los libros
        List<Libro> libros = new ArrayList<>();

        try {
            // Ejecuta una consulta XQUERY que obtiene todos los nodos de la colección
            String query = "XQUERY for $v in collection('" + COLLECTION_NAME + "') return $v";
            String result = session.execute(query);

            // Procesar el XML resultante y lo convierte en una lista de objetos Libro
            libros = parsearLibrosXML(result);

        } catch (IOException e) {
            throw new RepositoryException("Error al procesar la consulta de libros", e);
        } catch (Exception e) {
            throw new RepositoryException("Error inesperado al consultar los libros", e);
        }
        return libros;
    }

    // Metodo para convertir XML a una lista de objetos Libro
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

                    // Extrae los atributos y valores de las etiquetas del XML
                    int id = Integer.parseInt(element.getAttribute("id"));
                    String titulo = getTagValue("titulo", element);
                    String autor = getTagValue("autor", element);
                    int anio = Integer.parseInt(getTagValue("anio", element));
                    String genero = getTagValue("genero", element);
                    double precio = Double.parseDouble(getTagValue("precio", element));

                    // Crea un objeto Libro y lo agrega a la lista
                    libros.add(new Libro(id, titulo, autor, anio, genero, precio));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RepositoryException("Error al procesar el XML de los libros", e);
        }
        return libros;
    }

    // Metodo para obtener el valor de una etiqueta en un elemento XML
    private String getTagValue(String tag, Element element) {

        // Obtiene todos los nodos con el nombre de la etiqueta
        NodeList nodeList = element.getElementsByTagName(tag);

        // Si existen nodos con esa etiqueta, devuelve el texto contenido en la primera coincidencia
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        //Si no se encuentra la etiqueta, devuelve una cadena vacía
        return "";
    }

    // Metodo para eliminar un libro de la base de datos usando su id
    public void eliminarLibro(int id) throws RepositoryException {
        try {
            // Ejecuta un comando para eliminar el nodo XML correspondiente al libro con el ID dado
            session.execute("delete node collection('" + COLLECTION_NAME + "')" + "/libro" + id + ".xml");

            System.out.println("Libro eliminado con id: " + id);

        } catch (Exception e) {
            throw new RepositoryException("Error al eliminar el libro con id: " + id, e);
        }
    }

    // Actualizar un libro (reemplazar XML existente)
    public void actualizarLibro(Libro libro) throws RepositoryException {
        try {
            // Convierte el objeto libro a un string XML
            String xmlContent = libro.toXml();

            // Ejecuta el comando para reemplazar el archivo XML existente en la base de datos
            session.execute("replace node collection('" + COLLECTION_NAME + "')" + "/libro" + libro.getId() + ".xml " + xmlContent);

            System.out.println("Libro actualizado: " + libro.getTitulo());

        } catch (Exception e) {
            throw new RepositoryException("Error al actualizar el libro", e);
        }
    }
}
