package org.juancarlos.biblioteca.menu;

/**
 * Enumeración que representa las opciones del menú de la biblioteca.
 * Cada opción tiene un código asociado y una descripción.
 */
public enum EnumMenu {
    CREAR_COLECCION(1, "Añadir Nueva Colección"),
    ELIMINAR_COLECCION(2, "Eliminar Colección"),
    CONSULTAR_COLECCIONES(3, "Consultar Colecciones"),
    CREAR_ADD_LIBRO(4, "Añadir Libro.xml"),
    CREAR_LIBRO(5, "Añadir Libro"),
    CONSULTAR_LIBROS(6, "Consultar Libros"),
    CONSULTAR_LIBROS_TITULO(7, "Consultar por Título"),
    CONSULTAR_LIBROS_AUTOR(8, "Consultar por Autor"),
    FILTRAR_LIBROS_ANIO(9, "Filtrar por Año"),
    ACTUALIZAR_LIBRO(10, "Actualizar Libro"),
    ELIMINAR_LIBRO(11, "Eliminar Libro"),
    SALIR(12, "Salir");

    private final int codigo;
    private final String descripcion;

    /**
     * Constructor de la enumeración.
     *
     * @param codigo      Código numérico de la opción.
     * @param descripcion Descripción textual de la opción.
     */
    EnumMenu(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Retorna la opción del menú biblioteca correspondiente al código proporcionado.
     * Si no existe ninguna coincidencia, retorna null.
     *
     * @param codigo Código de la opción buscada.
     * @return La opción del menú correspondiente o null si no se encuentra.
     */
    public static EnumMenu obtenerPorCodigo(int codigo) {
        for (EnumMenu opcion : values()) {
            if (opcion.getCodigo() == codigo) {
                return opcion;
            }
        }
        return null;
    }

    /**
     * Muestra por consola todas las opciones del menú de la biblioteca con su código y descripción.
     */
    public static void mostrarOpcionesMenu() {
        System.out.println("📔 --- MENÚ DE BIBLIOTECA --- 📔");
        for (EnumMenu opcion : EnumMenu.values()) {
            System.out.println(opcion.getCodigo() + ". " + opcion.getDescripcion());
        }
    }
}
