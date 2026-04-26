# Herramientas de Construcción: Maven y Gradle

## Maven

### Ciclo de Vida
- **clean**: Elimina archivos generados
- **compile**: Compila el código fuente
- **test**: Ejecuta tests
- **package**: Empaqueta en JAR/WAR
- **install**: Instala en repositorio local
- **deploy**: Despliega a repositorio remoto

### Comandos Básicos
```bash
# Compilar
mvn compile

# Ejecutar tests
mvn test

# Empaquetar
mvn package

# Limpiar y compilar
mvn clean compile

# Todo el ciclo
mvn clean install

# Con perfil específico
mvn clean install -Pprod
```

### Estructura de Directorios
```
proyecto/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       ├── java/
│       └── resources/
└── target/
```

## Gradle

### Tareas Básicas
- **build**: Construye el proyecto completo
- **clean**: Limpia archivos generados
- **compileJava**: Compila código Java
- **test**: Ejecuta tests
- **jar**: Crea JAR
- **run**: Ejecuta la aplicación

### Comandos Básicos
```bash
# Construir
gradle build

# Ejecutar aplicación
gradle run

# Ejecutar tests
gradle test

# Limpiar
gradle clean

# Tarea personalizada
gradle helloWorld

# Con propiedades
gradle build -Pprofile=prod
```

### Estructura de Directorios
```
proyecto/
├── build.gradle
├── settings.gradle (para multi-proyecto)
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       ├── java/
│       └── resources/
└── build/
```

## Comparativa

### Maven
- **Ventajas**:
  - Estandarizado en empresas
  - XML verboso pero predecible
  - Excelente gestión de dependencias
  - Plugins maduros

- **Desventajas**:
  - XML muy verboso
  - Más lento en builds grandes
  - Curva de aprendizaje inicial

### Gradle
- **Ventajas**:
  - DSL flexible (Groovy/Kotlin)
  - Más rápido (cache, builds incrementales)
  - Scripts más legibles
  - Mejor para builds complejos

- **Desventajas**:
  - Menos estándar en algunas empresas
  - Curva de aprendizaje para DSL
  - Menos plugins que Maven

## Cuándo Usar Cada Uno

- **Maven**: Proyectos empresariales tradicionales, equipos grandes, necesidad de estándares estrictos
- **Gradle**: Proyectos modernos, Android, builds complejos, equipos que buscan flexibilidad

Ambos son compatibles con Spring Boot y la mayoría de frameworks Java modernos.