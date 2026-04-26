package jdklibs;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ejemplos de java.util.logging: logging del JDK
 * - Logger, niveles de log
 * - Handlers y formatters
 */
public class EjemplosLogging {

    private static final Logger logger = Logger.getLogger(EjemplosLogging.class.getName());

    static {
        // Configurar logger
        logger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false); // Evitar logs duplicados
    }

    public static void main(String[] args) {
        System.out.println("=== EJEMPLOS JAVA.UTIL.LOGGING ===\n");

        // Diferentes niveles de log
        logger.severe("Mensaje SEVERE (grave)");
        logger.warning("Mensaje WARNING (advertencia)");
        logger.info("Mensaje INFO (información)");
        logger.config("Mensaje CONFIG (configuración)");
        logger.fine("Mensaje FINE (detallado)");
        logger.finer("Mensaje FINER (más detallado)");
        logger.finest("Mensaje FINEST (muy detallado)");

        // Log con parámetros
        String usuario = "admin";
        int intentos = 3;
        logger.warning("Usuario {0} falló {1} veces al iniciar sesión", new Object[]{usuario, intentos});

        // Log de excepciones
        try {
            int division = 10 / 0;
        } catch (ArithmeticException e) {
            logger.log(Level.SEVERE, "Error aritmético", e);
        }

        System.out.println("\nNota: Los niveles FINE, FINER y FINEST requieren configuración adicional para mostrarse.");
    }
}