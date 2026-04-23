package io;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Ejemplos de I/O y NIO.2
 * - Streams de bytes y caracteres
 * - Serialización
 * - NIO.2 (java.nio.file)
 */

// Clase serializable
class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private int edad;
    private transient String contraseña;  // No se serializa
    
    public Usuario(String nombre, int edad, String contraseña) {
        this.nombre = nombre;
        this.edad = edad;
        this.contraseña = contraseña;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
               "nombre='" + nombre + '\'' +
               ", edad=" + edad +
               ", contraseña='" + contraseña + '\'' +
               '}';
    }
}

public class EjemplosIO {
    
    static final String ARCHIVO_TEXTO = "/tmp/ejemplo.txt";
    static final String ARCHIVO_BINARIO = "/tmp/datos.bin";
    static final String ARCHIVO_SERIALIZADO = "/tmp/usuario.ser";
    
    public static void main(String[] args) {
        System.out.println("=== ENTRADA/SALIDA (I/O) ===\n");
        
        try {
            ejemploEscrituraLecturaByte();
            ejemploEscrituraLecturaCaracteres();
            ejemploSerializacion();
            ejemplosNIO2();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Streams de bytes
    static void ejemploEscrituraLecturaByte() throws IOException {
        System.out.println("--- Streams de Bytes ---");
        
        // Escribir bytes
        try (FileOutputStream fos = new FileOutputStream(ARCHIVO_BINARIO)) {
            String mensaje = "Hola, Mundo!";
            fos.write(mensaje.getBytes());
            System.out.println("Bytes escritos en " + ARCHIVO_BINARIO);
        }
        
        // Leer bytes
        try (FileInputStream fis = new FileInputStream(ARCHIVO_BINARIO)) {
            byte[] buffer = new byte[1024];
            int bytesLeidos = fis.read(buffer);
            String contenido = new String(buffer, 0, bytesLeidos);
            System.out.println("Contenido leído: " + contenido);
        }
    }
    
    // Streams de caracteres con BufferedReader/PrintWriter
    static void ejemploEscrituraLecturaCaracteres() throws IOException {
        System.out.println("\n--- Streams de Caracteres ---");
        
        // Escribir caracteres
        try (PrintWriter writer = new PrintWriter(
                 new FileWriter(ARCHIVO_TEXTO))) {
            writer.println("Primera línea");
            writer.println("Segunda línea");
            writer.println("Tercera línea");
            System.out.println("Líneas escritas en " + ARCHIVO_TEXTO);
        }
        
        // Leer caracteres con BufferedReader
        try (BufferedReader reader = new BufferedReader(
                 new FileReader(ARCHIVO_TEXTO))) {
            String linea;
            System.out.println("Contenido del archivo:");
            while ((linea = reader.readLine()) != null) {
                System.out.println("  " + linea);
            }
        }
    }
    
    // Serialización de objetos
    static void ejemploSerializacion() throws IOException, ClassNotFoundException {
        System.out.println("\n--- Serialización ---");
        
        Usuario usuario = new Usuario("Ana", 28, "miContraseña123");
        System.out.println("Usuario original: " + usuario);
        
        // Serializar (escribir objeto)
        try (ObjectOutputStream oos = new ObjectOutputStream(
                 new FileOutputStream(ARCHIVO_SERIALIZADO))) {
            oos.writeObject(usuario);
            System.out.println("Usuario serializado en " + ARCHIVO_SERIALIZADO);
        }
        
        // Deserializar (leer objeto)
        try (ObjectInputStream ois = new ObjectInputStream(
                 new FileInputStream(ARCHIVO_SERIALIZADO))) {
            Usuario usuarioRecuperado = (Usuario) ois.readObject();
            System.out.println("Usuario deserializado: " + usuarioRecuperado);
            System.out.println("Nota: contraseña es null porque tiene @transient");
        }
    }
    
    // NIO.2 (java.nio.file)
    static void ejemplosNIO2() throws IOException {
        System.out.println("\n--- NIO.2 (java.nio.file) ---");
        
        Path archivo = Paths.get(ARCHIVO_TEXTO);
        Path directorio = Paths.get("/tmp/ejemploJava");
        
        // Crear directorio
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio);
            System.out.println("Directorio creado: " + directorio);
        }
        
        // Escribir con NIO.2
        Path nuevoArchivo = directorio.resolve("datos.txt");
        Files.write(nuevoArchivo, 
                   "Contenido con NIO.2\nLínea 2\nLínea 3".getBytes(),
                   StandardOpenOption.CREATE,
                   StandardOpenOption.WRITE);
        System.out.println("Archivo escrito con NIO.2: " + nuevoArchivo);
        
        // Leer con NIO.2
        System.out.println("Contenido leído con NIO.2:");
        List<String> lineas = Files.readAllLines(nuevoArchivo);
        for (String linea : lineas) {
            System.out.println("  " + linea);
        }
        
        // Información del archivo
        System.out.println("\n--- Información del Archivo ---");
        System.out.println("Existe: " + Files.exists(nuevoArchivo));
        System.out.println("Es archivo: " + Files.isRegularFile(nuevoArchivo));
        System.out.println("Es directorio: " + Files.isDirectory(nuevoArchivo));
        System.out.println("Tamaño: " + Files.size(nuevoArchivo) + " bytes");
        
        // Copiar archivo
        System.out.println("\n--- Operaciones con Archivos ---");
        Path copia = directorio.resolve("copia.txt");
        Files.copy(nuevoArchivo, copia, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Archivo copiado a: " + copia);
        
        // Listar contenido del directorio
        System.out.println("\nArchivos en " + directorio + ":");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directorio)) {
            for (Path archivo2 : stream) {
                System.out.println("  - " + archivo2.getFileName());
            }
        }
        
        // Mover archivo
        Path movido = directorio.resolve("movido.txt");
        Files.move(copia, movido, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Archivo movido a: " + movido);
        
        // Eliminar archivo
        Files.delete(movido);
        System.out.println("Archivo eliminado");
    }
}
