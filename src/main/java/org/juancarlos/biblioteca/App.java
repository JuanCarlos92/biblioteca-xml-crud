package org.juancarlos.biblioteca;

import org.juancarlos.biblioteca.menu.Menu;


import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Process baseXProcess = null;

        try {

            Menu menu = new Menu();
            menu.iniciar();

        } catch (IOException e) {
            System.err.println("Error al iniciar o detener el servidor BaseX: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
