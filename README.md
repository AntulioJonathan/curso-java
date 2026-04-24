# Ejemplos de Código Java - Temario Completo

Este repositorio contiene **ejemplos prácticos de código** cubriendo todo el temario de Java (hasta versión 21) con introducción a desarrollo web.

## 📚 Estructura

```
01_fundamentos/          - Sintaxis básica, tipos, operadores, arrays, cadenas
02_poo/                  - Clases, herencia, interfaces, enumerados
03_excepciones/          - Manejo de excepciones y excepciones personalizadas
04_colecciones/          - List, Set, Map, ordenación
05_genericos/            - Clases genéricas, métodos genéricos, wildcards
06_io/                   - I/O, serialización, NIO.2
07_concurrencia/         - Hilos, sincronización, ExecutorService
08_streams_lambda/       - Lambdas, Stream API, Optional
09_fecha_hora/           - LocalDate, LocalTime, ZonedDateTime, formateo
10_modulos/              - Definición de módulos (Java 9+)
11_novedades/            - Características destacadas por versión (Java 8-21)
12_jdbc/                 - Conexión a BD, CRUD, transacciones
13_web/                  - Servlets, REST, Spring Boot conceptual
14_buenas_practicas/     - SOLID, patrones, Optional, logging
```

## 🚀 Cómo usar los ejemplos

### Opción 1: Ejecutar directamente (requiere Java 21)
```bash
cd 01_fundamentos
javac SintaxisBasica.java
java fundamentos.SintaxisBasica
```

### Opción 2: Compilar todo
```bash
javac -d . 01_fundamentos/*.java
java fundamentos.SintaxisBasica
```

### Opción 3: Usar en un IDE (recomendado)
1. Abre VS Code o IntelliJ IDEA
2. Abre la carpeta `curso-java`
3. Navega a cualquier archivo y presiona `Run`

## 📋 Contenido por Sección

### 1️⃣ **Fundamentos**
- `SintaxisBasica.java` - Variables, tipos, operadores
- `EstructurasControl.java` - if, switch, for, while
- `ArraysYCadenas.java` - Arrays, ArrayList, String, StringBuilder

### 2️⃣ **POO**
- `Persona.java` - Clases, atributos, métodos, constructores
- `CuentaBancaria.java` - Encapsulación, getters/setters
- `Animal.java` - Herencia, clases abstractas, polimorfismo
- `Interfaz.java` - Interfaces simples y múltiples
- `Enum.java` - Enumerados con atributos y métodos

### 3️⃣ **Excepciones**
- `EjemplosExcepciones.java` - try/catch/finally, try-with-resources, excepciones personalizadas

### 4️⃣ **Colecciones**
- `EjemplosColecciones.java` - ArrayList, LinkedList, HashSet, TreeSet, HashMap, Comparable, Comparator

### 5️⃣ **Genéricos**
- `EjemplosGenericos.java` - Clases genéricas, métodos genéricos, wildcards (? extends, ? super)

### 6️⃣ **I/O**
- `EjemplosIO.java` - FileInputStream/OutputStream, BufferedReader/PrintWriter, Serialización, NIO.2

### 7️⃣ **Concurrencia**
- `EjemplosConcurrencia.java` - Thread, Runnable, synchronized, ExecutorService, Atomic, Lock

### 8️⃣ **Streams y Lambdas**
- `EjemplosStreamsLambda.java` - Lambdas, Stream API, filter, map, collect, Optional

### 9️⃣ **Fecha y Hora**
- `EjemplosFechaHora.java` - LocalDate, LocalTime, ZonedDateTime, Period, Duration, formateo

### 🔟 **Módulos**
- `EjemplosModulos.java` - Module-info.java, exports, requires, opens, provides/uses

### 1️⃣1️⃣ **Novedades**
- `NovedadesJava.java` - var (Java 10), switch expressions, records, pattern matching, virtual threads

### 1️⃣2️⃣ **JDBC**
- `EjemplosJDBC.java` - Conexiones, CRUD, PreparedStatement, transacciones

### 1️⃣3️⃣ **Desarrollo Web**
- `EjemplosWeb.java` - Arquitectura MVC, Servlets, REST, Spring Boot conceptual

### 1️⃣4️⃣ **Buenas Prácticas**
- `BuenasPracticas.java` - SOLID, patrones (Singleton, Factory, Builder), Optional, logging

## 🎯 Ejemplos Destacados

### Expresión Lambda + Stream
```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);
numeros.stream()
       .filter(n -> n % 2 == 0)
       .map(n -> n * 2)
       .forEach(System.out::println);  // Output: 4, 8
```

### Try-with-Resources
```java
try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
    String linea;
    while ((linea = br.readLine()) != null) {
        System.out.println(linea);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

### Records (Java 16+)
```java
record Persona(String nombre, int edad) { }
// Genera automáticamente: constructor, getters, equals, hashCode, toString
```

### Pattern Matching Switch (Java 21)
```java
String resultado = switch(valor) {
    case Integer i when i > 0 -> "Positivo";
    case Integer i when i < 0 -> "Negativo";
    case String s -> "Texto: " + s;
    default -> "Otro";
};
```

## 📝 Notas Importantes

### Convenciones
- Todos los archivos **Java** usan nomenclatura de paquetes (`package`)
- Los nombres de clases están en **PascalCase** (`MiClase`)
- Los métodos están en **camelCase** (`miMetodo()`)
- Las constantes están en **UPPER_CASE** (`MI_CONSTANTE`)

### Requisitos
- **Java 21** LTS (recomendado) o Java 8+
- IDE: VS Code, IntelliJ IDEA, Eclipse
- Maven o Gradle (para proyectos complejos)

### Para Ejecutar Ejemplos con Dependencias
Algunos ejemplos (como JDBC, Web) requieren librerías adicionales:

```bash
# Con Maven
mvn clean install

# Con Gradle
gradle build
```

## 🔗 Temas Relacionados

- **JDBC avanzado**: Connection pooling con HikariCP
- **JPA/Hibernate**: ORM para base de datos
- **Spring Framework**: IoC, DI, RestTemplate
- **Spring Boot**: Aplicaciones standalone
- **Pruebas**: JUnit 5, Mockito, AssertJ
- **Build**: Maven (pom.xml), Gradle
- **Control de versiones**: Git

## 📚 Referencias

- [Oracle Java Documentation](https://docs.oracle.com/en/java/)
- [Java SE 21 JDK](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Guía oficial de módulos](https://openjdk.org/projects/jigsaw/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## ✨ Tips para Estudiar

1. **Comienza con fundamentos**: Ejecuta los ejemplos de `01_fundamentos`
2. **POO primero**: Entiende bien clases, herencia e interfaces
3. **Práctica**: Modifica los ejemplos y crea variantes
4. **Gradual**: Sigue el orden del temario
5. **Experimenta**: Combina conceptos (ej: Threads + Streams)

---

**Última actualización**: Abril 2026  
**Versión de Java**: 21 LTS  
**Temario**: Repaso completo hasta Java 21
