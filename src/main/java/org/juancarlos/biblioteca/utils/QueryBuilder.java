package org.juancarlos.biblioteca.utils;

import org.juancarlos.biblioteca.model.Libro;

import java.util.List;

/**
 * Clase que proporciona métodos para generar consultas XQuery relacionadas con la gestión
 * de libros y colecciones en una biblioteca.
 * --
 * Esta clase incluye métodos para insertar, actualizar, eliminar libros, así como obtener
 * colecciones y consultar libros por diferentes criterios como título, autor o año.
 */
public class QueryBuilder {
    /**
     * Genera una consulta XQuery para insertar un libro en la colección de la biblioteca.
     *
     * @param libro       El libro a insertar.
     * @param resultadoId El último ID utilizado, se incrementa para generar el nuevo ID.
     * @return Una consulta XQuery que inserta el libro en el XML de la biblioteca.
     */
    public static String getInsertLibro(Libro libro, String resultadoId) {
        int ultimoId = (resultadoId.isEmpty()) ? 0 : Integer.parseInt(resultadoId.trim());
        int nuevoId = ultimoId + 1;

        return "XQUERY insert node " +
                " <libro id='" + nuevoId + "'>" +
                " <titulo>" + libro.getTitulo() + "</titulo>" +
                " <autor>" + libro.getAutor() + "</autor>" +
                " <anio>" + libro.getAnio() + "</anio>" +
                " </libro> into //biblioteca/genero[@tipo='" + libro.getGenero() + "']";

    }

    /**
     * Imprime los detalles de una lista de libros.
     *
     * @param libros La lista de libros a imprimir.
     */
    public static void getListLibros(List<Libro> libros) {
        for (Libro libro : libros) {
            System.out.println("ID: " + libro.getId());
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Año: " + libro.getAnio());
            System.out.println("Género: " + libro.getGenero());
            System.out.println("---------------------------------");
        }
    }

    /**
     * Genera una consulta XQuery para obtener todos los géneros de la biblioteca.
     *
     * @return Una consulta XQuery que devuelve todos los géneros.
     */
    public static String getListColeccion() {
        return "XQUERY for $g in //biblioteca/genero return data($g/@tipo)";
    }

    /**
     * Genera una consulta XQuery para verificar la existencia de un género en la biblioteca.
     *
     * @param tipoGenero El tipo de género a verificar.
     * @return Una consulta XQuery que devuelve el conteo de géneros que coinciden con el tipo dado.
     */
    public static String getVerificarGenero(String tipoGenero) {
        return "XQUERY count(//genero[@tipo='" + tipoGenero + "'])";
    }

    /**
     * Genera una consulta XQuery para insertar un nuevo género en la biblioteca.
     *
     * @param tipoGenero El tipo de género a insertar.
     * @return Una consulta XQuery que inserta un nuevo género en la biblioteca.
     */
    public static String getInsertColeccion(String tipoGenero) {
        return "XQUERY insert node <genero tipo='" + tipoGenero + "'/> into //biblioteca";
    }

    /**
     * Genera una consulta XQuery para eliminar un género de la biblioteca.
     *
     * @param tipoGenero El tipo de género a eliminar.
     * @return Una consulta XQuery que elimina el género de la biblioteca.
     */
    public static String getDeleteColeccion(String tipoGenero) {
        return "XQUERY delete node collection('biblioteca')/biblioteca/genero[@tipo = '" + tipoGenero + "']";
    }

    /**
     * Genera una consulta XQuery para obtener el ID máximo de los libros en la biblioteca.
     *
     * @return Una consulta XQuery que devuelve el ID máximo de los libros.
     */
    public static String getIDLibro() {
        return "XQUERY max(for $v in collection('biblioteca')/biblioteca/genero/libro/@id return xs:int($v))";
    }

    /**
     * Genera una consulta XQuery para obtener todos los libros de la biblioteca.
     *
     * @return Una consulta XQuery que devuelve todos los libros.
     */
    public static String getAllBiblioteca() {
        return "XQUERY for $v in collection('biblioteca') return $v";
    }

    /**
     * Genera una consulta XQuery para obtener libros con un título específico.
     *
     * @param titulo El título del libro a buscar.
     * @return Una consulta XQuery que devuelve los libros con el título dado.
     */
    public static String getQueryTitulo(String titulo) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//genero/libro " +
                "where $v/titulo = '" + titulo + "' " +
                "return <genero tipo='{$v/../@tipo}'>{$v}</genero> " +
                "}</biblioteca>";
    }

    /**
     * Genera una consulta XQuery para obtener libros de un autor específico.
     *
     * @param autor El autor de los libros a buscar.
     * @return Una consulta XQuery que devuelve los libros del autor dado.
     */
    public static String getQueryAutor(String autor) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//genero/libro " +
                "where $v/autor = '" + autor + "' " +
                "return <genero tipo='{$v/../@tipo}'>{$v}</genero> " +
                "}</biblioteca>";
    }

    /**
     * Genera una consulta XQuery para obtener libros publicados después de un año determinado.
     *
     * @param anio El año para comparar.
     * @return Una consulta XQuery que devuelve los libros publicados después del año dado.
     */
    public static String getQueryAnioMayor(int anio) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//genero/libro " +
                "where $v/anio > " + anio +
                " return <genero tipo='{$v/../@tipo}'>{$v}</genero> " +
                "}</biblioteca>";
    }

    /**
     * Genera una consulta XQuery para obtener libros publicados antes de un año determinado.
     *
     * @param anio El año para comparar.
     * @return Una consulta XQuery que devuelve los libros publicados antes del año dado.
     */
    public static String getQueryAnioMenor(int anio) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//genero/libro " +
                "where $v/anio < " + anio +
                " return <genero tipo='{$v/../@tipo}'>{$v}</genero> " +
                "}</biblioteca>";
    }

    /**
     * Genera una consulta XQuery para actualizar un libro en la biblioteca.
     *
     * @param id     El ID del libro a actualizar.
     * @param titulo El nuevo título del libro.
     * @param autor  El nuevo autor del libro.
     * @param anio   El nuevo año del libro.
     * @return Una consulta XQuery para actualizar los datos del libro.
     */
    public static String getActualizarLibro(int id, String titulo, String autor, int anio) {
        return "for $libro in collection('biblioteca')//libro " +
                "where $libro/@id = '" + id + "' " +
                "return (" +
                "  replace value of node $libro/titulo with '" + titulo + "', " +
                "  replace value of node $libro/autor with '" + autor + "', " +
                "  replace value of node $libro/anio with " + anio + " " +
                ")";
    }

    /**
     * Genera una consulta XQuery para eliminar un libro de la biblioteca.
     *
     * @param id El ID del libro a eliminar.
     * @return Una consulta XQuery que elimina el libro con el ID dado.
     */
    public static String getEliminarLibro(int id) {
        return "XQUERY delete node collection('biblioteca')//libro[@id = '" + id + "']";
    }

}
