package com.estructuras.tictactoe.model.persistencia;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.persistencia.RegistroPartida;

public class PartidaSerializer {

    /**
     * 
     * AppendingObjectOutputStream
     * 
     * Clase auxiliar para agregar objetos a un archivo sin sobreescribirlo.
     * 
     */
    private static class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }
        @Override
        protected void writeStreamHeader() throws IOException {
            // Omitimos la cabecera para no corromper la lectura secuencial
            reset();
        }
    }

    /**
     * Guarda una partida en un archivo.
     * @param partida Partida que se desea guardar.
     * @param rutaArchivo Ruta del archivo donde se guardará la partida.
     * @throws IOException
     */
    public static void guardarPartida(RegistroPartida partida, String rutaArchivo) throws IOException { 
        File file = new File(rutaArchivo);
        boolean append = file.exists() && file.length() > 0;

        try (FileOutputStream fos = new FileOutputStream(file, append);
             ObjectOutputStream oos = append ? new AppendingObjectOutputStream(fos) : new ObjectOutputStream(fos)) {
            oos.writeObject(partida);
        }
    }

    /**
     * Carga una partida desde un archivo, la elimina al cargarla y la retorna.
     * Tiene ese comportamiento, para evitar que se queden guardados estados de partidas ya terminadas
     * @param nombrePartida Nombre de la partida.
     * @param rutaArchivo Ruta del archivo donde se encuentra la partida.
     * @return Partida cargada.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Partida cargarPartida(String nombrePartida, String rutaArchivo) throws IOException, ClassNotFoundException { 
        File file = new File(rutaArchivo);
        if (!file.exists()) 
            throw new IOException("El archivo no existe");
        if (file.length() == 0) 
            throw new IOException("El archivo está vacío");

        File temp = new File("temp.dat");
        RegistroPartida registroPartida = null;
        RegistroPartida registroTemp = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo));
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("temp.dat"))) {
            
            while((registroTemp = leerRegistro(ois)) != null ) {
                if (registroTemp.getNombrePartida().equals(nombrePartida)) {
                    registroPartida = registroTemp;
                } else {
                    oos.writeObject(registroTemp);
                }
            }
        }  

        if ( registroPartida == null) {
            temp.delete();
            throw new ClassNotFoundException("No se encontró la partida");
        }

        if (!file.delete()) {
            throw new IOException("No se pudo borrar el archivo original");
        }
        if (!temp.renameTo(file)) {
            throw new IOException("No se pudo renombrar el archivo temporal");
        }

        return registroPartida.getPartida();
    }

    /**
     * Obtener la lista de todas las partidas que se han guardado.
     * @param rutaArchivo Ruta del archivo donde se encuentran las partidas.
     * @return
     */
    public static List<String> obtenerPartidasGuardadas(String rutaArchivo) throws IOException, ClassNotFoundException { 
        List<String> partidas = new LinkedList<>();
        File file = new File(rutaArchivo);
        RegistroPartida registro = null;

        if (!file.exists() || file.length() == 0) 
            return partidas;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while ((registro = leerRegistro(ois)) != null) {
                partidas.add(registro.getNombrePartida());
            }
        }

        return partidas;
    }

    /**
     * Método auxiliar para leer los registros y deserializarlos.
     * @param ois ObjectInputStream que contiene los registros.
     * @return RegistroPartida deserializado, o null si no hay más registros.
     */
    private static RegistroPartida leerRegistro( ObjectInputStream ois) throws IOException, ClassNotFoundException{
        try {
            return (RegistroPartida) ois.readObject();
        } catch (EOFException e) {
            return null;
        } 
    }
}
