package org.juancarlos.biblioteca.utils;

import org.juancarlos.biblioteca.model.Libro;

import java.util.List;

public class QueryBuilder {
    public static String getInsertQuery(Libro libro, String resultadoId) {
        int ultimoId = (resultadoId.isEmpty()) ? 0 : Integer.parseInt(resultadoId.trim());
        int nuevoId = ultimoId + 1;

        return "insert node " +
                " <libro id='" + nuevoId + "'>" +
                " <titulo>" + libro.getTitulo() + "</titulo>" +
                " <autor>" + libro.getAutor() + "</autor>" +
                " <anio>" + libro.getAnio() + "</anio>" +
                " <genero>" + libro.getGenero() + "</genero>" +
                "</libro> into /biblioteca";
    }

    public static void getListQuery(List<Libro> libros) {
        for (Libro libro : libros) {
            System.out.println("ID: " + libro.getId());
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Año: " + libro.getAnio());
            System.out.println("Género: " + libro.getGenero());
            System.out.println("---------------------------------");
        }
    }

    public static String getQuery() {
        return "XQUERY max(for $v in collection('biblioteca')/biblioteca/libro/@id return xs:int($v))";
    }

    public static String getQuerylibro() {
        return "XQUERY for $v in collection('biblioteca') return $v";
    }

    public static String getQueryByTitulo(String titulo) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')/biblioteca/libro " +
                "where $v/titulo = '" + titulo + "' " +
                "return $v " +
                "}</biblioteca>";
    }

    public static String getQueryByAutor(String autor) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')/biblioteca/libro " +
                "where $v/autor = '" + autor + "' " +
                "return $v " +
                "}</biblioteca>";
    }

    public static String getQueryByGeneno(String genero) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')/biblioteca/libro " +
                "where $v/genero = '" + genero + "' " +
                "return $v " +
                "}</biblioteca>";
    }

    public static String getQueryByAnioMayor(int anio) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//libro " +
                "where $v/anio > " + anio +
                " return $v " +
                "}</biblioteca>";
    }

    public static String getQueryByAnioMenor(int anio) {
        return "XQUERY " +
                "<biblioteca>{ " +
                "for $v in collection('biblioteca')//libro " +
                "where $v/anio < " + anio +
                " return $v " +
                "}</biblioteca>";
    }

    public static String getQueryActualizar(int id, String titulo, String autor, int anio, String genero) {
        return "for $libro in collection('biblioteca')/biblioteca/libro " +
                "where $libro/@id = '" + id + "' " +
                "return (" +
                "  replace value of node $libro/titulo with '" + titulo + "', " +
                "  replace value of node $libro/autor with '" + autor + "', " +
                "  replace value of node $libro/anio with " + anio + ", " +
                "  replace value of node $libro/genero with '" + genero + "'" +
                ")";
    }
    public static String getQueryEliminar(int id) {
        return "XQUERY delete node collection('biblioteca')/biblioteca/libro[@id = '" + id + "']";
    }
}
