package org.juancarlos.biblioteca.menu;

public enum EnumMenu {
    CREAR_LIBRO(1, "Crear Nuevo Libro"),
    CONSULTAR_LIBROS(2, "Consultar todos los Libros"),
    ELIMINAR_LIBRO(3, "Eliminar Libro (por ID)"),
    ACTUALIZAR_LIBRO(4, "Actualizar Libro"),
    SALIR(5, "SALIR");

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
     * Retorna la opción del menú bilioteca correspondiente al código proporcionado.
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
        System.out.println("--- MENÚ DE BIBLIOTECA ---");
        for (EnumMenu opcion : EnumMenu.values()) {
            System.out.println(opcion.getCodigo() + ". " + opcion.getDescripcion());
        }
    }
}
