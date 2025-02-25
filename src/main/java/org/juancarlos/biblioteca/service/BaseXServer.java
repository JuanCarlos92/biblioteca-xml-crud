//package org.juancarlos.biblioteca.service;
//
//import java.io.IOException;
//
//public class BaseXServer {
//    // Iniciar basexserver
//    public static Process iniciarBaseXServer() throws IOException {
//        String command = "basexserver";
//
//        // Ejecutar el comando en la terminal
//        Process process = new ProcessBuilder(command).start();
//
//        return process;
//
//    }
//    // Parar basexserver
//    public static void detenerBaseXServer() throws IOException {
//        String command = "basexserver stop";
//
//        // Ejecutar el comando para detener el servidor
//        new ProcessBuilder(command).start();
//    }
//}
