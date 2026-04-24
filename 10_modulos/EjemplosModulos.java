package modulos;

/**
 * Ejemplos de Módulos en Java (Java 9+)
 * 
 * Los módulos proporcionan:
 * - Encapsulación fuerte (strong encapsulation)
 * - Resolución de dependencias
 * - Mejor mantenimiento de proyectos grandes
 * 
 * ESTRUCTURA DE UN PROYECTO MODULAR:
 * 
 * proyecto/
 * ├── src/
 * │   ├── modulo.utilitarios/
 * │   │   ├── module-info.java
 * │   │   └── com/
 * │   │       └── utilidades/
 * │   │           └── Calculadora.java
 * │   │
 * │   └── modulo.principal/
 * │       ├── module-info.java
 * │       └── com/
 * │           └── aplicacion/
 * │               └── Aplicacion.java
 * │
 * └── mods/ (archivos compilados)
 */

public class EjemplosModulos {
    
    public static void main(String[] args) {
        System.out.println("=== MÓDULOS EN JAVA (Java 9+) ===\n");
        
        ejemploModuleInfo();
        ejemploExports();
        ejemploRequires();
        ejemploOpens();
        ejemploProvideUses();
        ejemploBuenasPracticas();
    }
    
    // EJEMPLO 1: Estructura module-info.java
    static void ejemploModuleInfo() {
        System.out.println("--- Estructura de module-info.java ---");
        
        System.out.println("""
            // module-info.java básico
            module com.miempresa.utilidades {
                // Exportar módulos públicamente
                exports com.miempresa.utilidades;
                
                // Requerir dependencias
                requires java.base;  // Implícitamente siempre requerido
            }
            """);
    }
    
    // EJEMPLO 2: Exports (Exportar paquetes)
    static void ejemploExports() {
        System.out.println("\n--- EXPORTS (Exportar paquetes) ---");
        
        System.out.println("""
            module com.miempresa.servicios {
                // Exportar paquete públicamente
                exports com.miempresa.servicios.api;
                
                // Exportar a módulos específicos
                exports com.miempresa.servicios.interno
                    to com.miempresa.aplicacion;
                
                // Paquetes no exportados NO son accesibles externamente
                // (com.miempresa.servicios.privado sigue siendo privado)
            }
            
            // Uso en otro módulo:
            // import com.miempresa.servicios.api.MiServicio;  ✓ OK
            // import com.miempresa.servicios.privado.*;       ✗ ERROR
            """);
        
        System.out.println("✓ Exports proporciona encapsulación a nivel de módulo");
    }
    
    // EJEMPLO 3: Requires (Dependencias)
    static void ejemploRequires() {
        System.out.println("\n--- REQUIRES (Dependencias) ---");
        
        System.out.println("""
            module com.miempresa.aplicacion {
                // Dependencia básica
                requires com.miempresa.servicios;
                
                // Dependencia transitiva (también expone a dependientes)
                requires transitive java.sql;
                
                // Dependencia estática (solo en tiempo de compilación)
                requires static com.miempresa.opcional;
                
                // Módulos de la plataforma
                requires java.base;        // Implícito siempre
                requires java.logging;
                requires java.desktop;
                
                // Exportar lo requerido
                exports com.miempresa.aplicacion.publica;
            }
            
            MÓDULOS COMUNES DE JAVA:
            ├── java.base           (Fundamental)
            ├── java.sql            (Base de datos)
            ├── java.logging        (Sistema de logs)
            ├── java.desktop        (GUI - Swing, AWT)
            ├── java.naming         (JNDI)
            ├── java.xml            (XML processing)
            └── java.net.http       (HTTP Client)
            """);
    }
    
    // EJEMPLO 4: Opens y Opens to
    static void ejemploOpens() {
        System.out.println("\n--- OPENS (Acceso reflexivo) ---");
        
        System.out.println("""
            module com.miempresa.datos {
                exports com.miempresa.datos.api;
                
                // Permitir acceso reflexivo (reflection)
                opens com.miempresa.datos.entidades;
                
                // Permitir reflexión a módulos específicos
                // (útil para frameworks como Spring, Jackson)
                opens com.miempresa.datos.entidades
                    to com.miempresa.framework;
                
                // Deep reflection sin opens requiere --add-opens en JVM
                // java --add-opens module/package=destino
            }
            
            CASOS DE USO:
            • Frameworks que usan reflection (Spring, Hibernate)
            • Serialización (Jackson, Gson)
            • Testing (Mockito)
            • ORMs (JPA, Hibernate)
            """);
        
        System.out.println("⚠️ Opens permite que otros módulos accedan mediante reflection");
    }
    
    // EJEMPLO 5: Provides y Uses
    static void ejemploProvideUses() {
        System.out.println("\n--- PROVIDES y USES (Service Provider Interface) ---");
        
        System.out.println("""
            // Interfaz en módulo 'com.miempresa.api'
            module com.miempresa.api {
                exports com.miempresa.api.servicio;
            }
            
            ---
            
            // Implementación en módulo 'com.miempresa.impl'
            module com.miempresa.impl {
                requires com.miempresa.api;
                
                // Proporcionar implementación de servicio
                provides com.miempresa.api.servicio.Procesador
                    with com.miempresa.impl.ProcesadorJSON;
                
                provides com.miempresa.api.servicio.Procesador
                    with com.miempresa.impl.ProcesadorXML;
            }
            
            ---
            
            // Consumidor en módulo 'com.miempresa.cliente'
            module com.miempresa.cliente {
                requires com.miempresa.api;
                
                // Usar cualquier implementación disponible
                uses com.miempresa.api.servicio.Procesador;
            }
            
            // Código:
            ServiceLoader<Procesador> cargador = 
                ServiceLoader.load(Procesador.class);
            
            for (Procesador p : cargador) {
                System.out.println(p.getClass().getName());
            }
            """);
        
        System.out.println("✓ Proporciona inyección de dependencias desacoplada");
    }
    
    // EJEMPLO 6: Buenas prácticas
    static void ejemploBuenasPracticas() {
        System.out.println("\n--- BUENAS PRÁCTICAS CON MÓDULOS ---");
        
        System.out.println("""
            ✅ HACER:
            
            1. Exportar solo lo necesario (minimizar superficie pública)
            2. Usar requires transitive para dependencias clave
            3. Separar API de implementación
            4. Usar opens solo cuando sea necesario
            5. Documentar las dependencias
            
            ❌ NO HACER:
            
            1. Exportar paquetes innecesarios
            2. Crear dependencias cíclicas
            3. Usar opens sin necesidad
            4. Ignorar compilación con módulos
            5. Mezclar código modular con no-modular sin cuidado
            
            ESTRUCTURA RECOMENDADA:
            
            com.empresa/
            ├── modulos de API
            │   └── exports com.empresa.api.*
            │
            ├── modulos de implementación
            │   ├── requires API
            │   └── provides ...
            │
            └── módulos de aplicación
                ├── requires API, IMPL
                └── exports nada (app final)
            """);
    }
}

/**
 * EJEMPLO DEL MODULE-INFO.JAVA REAL
 * 
 * Para crear un proyecto modular en Java 11+:
 * 
 * 1. Estructura de directorios:
 *    src/
 *    └── com.miempresa.utilidades/
 *        ├── module-info.java
 *        └── com/miempresa/utilidades/
 *            └── Calculadora.java
 * 
 * 2. Compilar:
 *    javac -d mods --module-source-path src $(find src -name "*.java")
 * 
 * 3. Ejecutar:
 *    java --module-path mods --module com.miempresa.utilidades/com.miempresa.utilidades.Main
 * 
 * 4. Ver módulos:
 *    java --list-modules
 *    java --describe-module java.base
 */

/**
 * MIGRACIÓN A MÓDULOS
 * 
 * Proyecto antiguo (sin módulos):
 * └── src/
 *     └── com/miempresa/
 *         └── Clase.java
 * 
 * ↓ Agregar module-info.java
 * 
 * Proyecto modular:
 * ├── src/
 * │   ├── com.empresa.api/
 * │   │   ├── module-info.java      ← Nuevo
 * │   │   └── com/empresa/api/
 * │   │
 * │   └── com.empresa.impl/
 * │       ├── module-info.java      ← Nuevo
 * │       └── com/empresa/impl/
 * │
 * └── mods/                         ← Salida compilada
 * 
 * VENTAJAS:
 * ✓ Encapsulación fuerte
 * ✓ Mejor visibilidad de dependencias
 * ✓ Verificación en tiempo de compilación
 * ✓ Mejor rendimiento (compile-time)
 * ✓ Facilita análisis estático
 */
