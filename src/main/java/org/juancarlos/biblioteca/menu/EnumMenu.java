package org.juancarlos.biblioteca.menu;

public enum EnumMenu {
    CREAR_LIBRO(1, "Añadir Nuevo Libro"),
    CONSULTAR_LIBROS(2, "Consultar Libros"),
    CONSULTAR_LIBROS_TITULO(3, "Consultar por Título"),
    CONSULTAR_LIBROS_AUTOR(4, "Consultar por Autor"),
    CONSULTAR_LIBROS_GENERO(5, "Consultar por Género"),
    FILTRAR_LIBROS_ANIO(6, "Filtrar por Año de publicación"),
    ACTUALIZAR_LIBRO(7, "Actualizar Libro"),
    ELIMINAR_LIBRO(8, "Eliminar Libro"),
    SALIR(9, "Salir");

    private final int codigo;
    private final String descripcion;

    //Constructor de la enumeración. Asigna el código y descripción a la opción.
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
     * Muestra por consola todas las opciones del menú biblioteca con su código y descripción.
     */
    public static void mostrarOpcionesMenu() {
        System.out.println("📔 --- MENÚ DE BIBLIOTECA --- 📔");
        for (EnumMenu opcion : EnumMenu.values()) {
            System.out.println(opcion.getCodigo() + ". " + opcion.getDescripcion());
        }
    }
}
