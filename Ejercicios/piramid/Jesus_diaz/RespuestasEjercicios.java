import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.time.*;
import java.nio.file.*;

public class RespuestasEjercicios{

    public static void main(String[] args){
        System.out.println("Propuestas a los ejercicios");
        desafio1();
        desafio2();
        desafio3();
        desafio4();
        desafio5();
        desafio6();
        desafio7();
        desafio8();
        desafio9();
        desafio10();
    }

// Desafíos de stream y lambdas en Java Todos:
 
// ---
 
// Desafío 1: Transformación y filtrado múltiple con objetos complejos
 
// Enunciado:  

// Tienes una lista de Persona (nombre, edad, ciudad, salario, lista de hobbies).  

// Debes obtener:  

// - Las 3 personas más jóvenes que vivan en "Madrid" o "Barcelona", con salario > 30000, ordenadas por salario descendente.  

// - De ellas, extraer un Map<String, List<String>> donde la clave es el nombre y el valor es la lista de hobbies en mayúsculas.
 
// Requisito técnico:  

// Usar Collectors.toMap, skip, limit, filter, map, sorted.
 
// ---
    static void desafio1(){
        System.out.println("> Desafio 1");
        // Clase Persona al final de archivo

        //Generamos la lista Persona
        List<Persona> personas = new ArrayList<>(Arrays.asList(
            // Personas que cumplen las condiciones
            new Persona("Hugo", 23, "Madrid", 34000.0,
                Arrays.asList("fotografía", "viajar")),
                
            new Persona("Marta", 24, "Madrid", 32000.0,
                Arrays.asList("correr", "cocinar")),

            new Persona("Diego", 22, "Barcelona", 31000.0,
                Arrays.asList("basket", "música")),


            // Personas que no cumplen las condiciones  
            new Persona("Alana", 23, "Yucatan", 20000.0,
                Arrays.asList("leer", "pintar", "viajar")),

            new Persona("Carlos", 29, "Madrid", 35000.0,
                Arrays.asList("futbol", "cine", "cocinar")),

            new Persona("Lucia", 27, "Barcelona", 42000.0,
                Arrays.asList("dibujo", "yoga", "cantar")),

            new Persona("Sofia", 32, "Madrid", 50000.0,
                Arrays.asList("viajar", "fotografia", "lectura")),

            new Persona("Javier", 26, "Valencia", 31000.0,
                Arrays.asList("guitarra", "running")),

            new Persona("Ana", 31, "Barcelona", 33000.0,
                Arrays.asList("pintura", "yoga")),

            new Persona("Pablo", 25, "Madrid", 29999.0,
                Arrays.asList("música", "jardinería")),

            new Persona("Elena", 28, "Barcelona", 28000.0,
                Arrays.asList("cine", "lectura"))
        ));

        //Mostrar lo que debe imprimir mi ejercicio
        System.out.println("--------La respuesta la respuestas debe ser:-------------");
        personas.stream()
                .limit(3)
                .forEach(System.out::println);

        //Implementación de filtros
        System.out.println("\nRespuesta de mi ejercicio");
        Map<String, List<String>> listaFiltrada = personas.stream()
                .filter( n -> n.salario > 30_000 && (n.ciudad.equals("Madrid") || n.ciudad.equals("Barcelona")))
                .sorted(Comparator.comparing(n -> n.edad))
                .limit(3)
                .sorted(Comparator.comparing((Persona n) -> n.salario).reversed())
                .peek(n -> System.out.println("ANTES DEL COLLECT: " + n))
                .collect(Collectors.toMap(
                    (Persona n) -> n.nombre,
                    (Persona n) -> n.hobbies.stream()
                                            .map(String::toUpperCase)
                                            .collect(Collectors.toList()),
                                            (a, b) -> b, // remueve duplicados, pero lo uso para que compile
                                            LinkedHashMap::new // Garantizar el orden de inserción
                ));
        System.out.println(listaFiltrada);
    }
    

// Desafío 2: Estadísticas en tiempo real sobre streams infinitos
 
// Enunciado:  

// Genera un stream infinito de números enteros aleatorios entre 1 y 100 (usa Random.ints()).  

// Procesa los primeros 10,000 números y calcula:  

// - Promedio de los números pares.  

// - Número más alto entre los múltiplos de 7.  

// - Cantidad de números que son primos.  

// Todo en un solo pipeline sin crear colecciones intermedias.
 
// Requisito técnico:  

// Usar IntStream, limit, filter, summaryStatistics o reduce / collect.
 
// ---
static void desafio2(){
    //Uso de Random para el stream infinito
    Random random = new Random();

    //Uso de clase Analisis para aplicar en cada collect
    //clase analisis definida fuera de la clase main
    
    Analisis resultado = random.ints(1,101)
            .limit(10_000)
            .collect(
                Analisis::new,
                (analisis, n) -> analisis.actualizar(n),
                (a1, a2) -> {} // solo para que compile
            );
    System.out.println("\n> Desafio 2\n");
    
    System.out.println("Promedio de los números pares: " + resultado.paresSuma / resultado.paresContador);
    System.out.println("Máximo múltiplo de 7: " + resultado.multiplo7);
    System.out.println("Cantidad de números que son primos: " + resultado.primo);
}

 
// Desafío 3: Aplanamiento y agrupación compleja
 
// Enunciado:  

// Dada una lista de Empresa (nombre, lista de Departamento, cada departamento tiene lista de Empleado con nombre y salario).  

    // Calcula el salario promedio por empresa, pero excluyendo al empleado mejor pagado de cada departamento (outlier).
 
// Requisito técnico:  

// Usar flatMap, groupingBy, collectingAndThen, maxBy, filter sobre streams internos.
 
// ---
static void desafio3(){
    System.out.println("\n> Desafio 3\n");

    // Implementación de lista  Empresa
    List<Empresa> empresas = new ArrayList<>(Arrays.asList(
    new Empresa("TechCorp", Arrays.asList(
        new Departamento("Recursos Humanos", Arrays.asList(
            new Empleado("Ana", 32000),
            new Empleado("Luis", 40000)
        )),
        new Departamento("IT", Arrays.asList(
            new Empleado("Bea", 31000),
            new Empleado("Juan", 38000)
        ))
    )),
    new Empresa("FinanPlus", Arrays.asList(
        new Departamento("Finanzas", Arrays.asList(
            new Empleado("Laura", 45000),
            new Empleado("Carlos", 39000)
        ))
    ))
    ));
    
    //Salario promedio
    Map<String, Double> salarioPromedio = empresas.stream()
        .collect(Collectors.toMap(
            emp -> emp.nombre,
            emp -> emp.departamentos.stream()
                        .flatMap(dep -> {
                            Optional<Empleado> mejorPagado = dep.empleados.stream()
                                .max(Comparator.comparing(e -> e.salario));
                            return dep.empleados.stream()
                                .filter(e -> !mejorPagado.isPresent() || !e.equals(mejorPagado.get()));
                        })
                        .mapToDouble(e -> e.salario)
                        .average()
                        .orElse(0.0)
        ));

    for (Map.Entry<String, Double> entry : salarioPromedio.entrySet()) {
    System.out.println("Empresa: " + entry.getKey() + " promedio: " + entry.getValue());
}
}

 
// Desafío 4: Particionamiento y transformación paralela
 
// Enunciado:  

// Tienes un List<String> de frases largas.  

// Debes:  

// 1. Dividir en dos grupos (partición): frases con más de 10 palabras y el resto.  

// 2. De cada grupo, quedarte con las 5 frases más cortas (por longitud de caracteres).  

// 3. Transformar cada frase a Map<Character, Long> (frecuencia de cada letra ignorando espacios).  

// 4. Combinar los mapas de cada partición sumando frecuencias de letras.
 
// Requisito técnico:  

// Usar partitioningBy, collect personalizado, toMap con fusión, parallelStream.
 
// ---
static void desafio4(){
    System.out.println("\n> Desafío 4");
    List<String> frasesLargas = new ArrayList<String>();
    frasesLargas.add("Esto es un ejemplo de frase larga de diez palabras o más");
    frasesLargas.add("Ejercicios de streams y lambdas");
    frasesLargas.add("Las dificultades no son obstáculos, son el camino");
    frasesLargas.add("Solo un necio prueba la profundidad de un lago con ambos pies");
    frasesLargas.add("Para avanzar hacia adelante, hay que dejar algo atrás");
    frasesLargas.add("No te tomes la vida muy enserio, nunca saldrás vivo de ella");
    frasesLargas.add("Cada persona es el promedio de las 5 personas con las que más tiempo pasa");
    frasesLargas.add("Los sabios se corrigen en el instante, pues saben que los cementerios están llenos de personas que pensaron tener más tiempo");
    frasesLargas.add("No busques la muerte ni el amor, ellos te encontraran a su tiempo");
    frasesLargas.add("Todo lo que tengo lo gané perdiendo");

    Map<Boolean, List<String>> particiones = frasesLargas.stream()
        .collect(Collectors.partitioningBy(frase -> frase.split("\\s+").length > 10));
    
    List<String> grupoTrue = particiones.get(true);
    List<String> grupoFalse = particiones.get(false);


    //Grupos con 5 frases más cortas
    List<String> cincoCortasTrue = grupoTrue.parallelStream()
        .sorted(Comparator.comparingInt(String::length))
        .limit(5)
        .collect(Collectors.toList());

    List<String> cincoCortasFalse = grupoFalse.parallelStream  ()
        .sorted(Comparator.comparingInt(String::length))
        .limit(5)
        .collect(Collectors.toList());

    //Grupos de frecuencias de letras
    List<Map<Character,Long>> mapaGrupoTrue = cincoCortasTrue.parallelStream()
        .map(frase -> frase.replaceAll("\\s+", "")
            .chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
        )
        .collect(Collectors.toList());

    List<Map<Character,Long>> mapaGrupoFalse = cincoCortasFalse.parallelStream()
        .map(frase -> frase.replaceAll("\\s+", "")
            .chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
        )
        .collect(Collectors.toList());

    //Fusión de grupos
    Map<Character,Long> fusionTrue = mapaGrupoTrue.stream()
        .flatMap(map -> map.entrySet().stream())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            Long::sum
        ));

    Map<Character,Long> fusionFalse = mapaGrupoFalse.stream()
        .flatMap(map -> map.entrySet().stream())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            Long::sum
        ));

    Map<Character,Long> fusionTotal = new HashMap<>(fusionTrue);

    fusionFalse.forEach((letra,frecuencia) -> 
        fusionTotal.merge(letra, frecuencia,Long::sum)
    );

    System.out.println("---- Frases con más de 10 palabras: " + cincoCortasTrue.size());
    cincoCortasTrue.forEach(System.out::println);
    // mapaGrupoTrue.forEach(System.out::println);
    // System.out.println(fusionTrue);

    System.out.println("---- Frases con 10 o menos palabras: " + cincoCortasFalse.size());
    cincoCortasFalse.forEach(System.out::println);
    // mapaGrupoFalse.forEach(System.out::println);
    // System.out.println(fusionFalse);

    System.out.println("\nFusión de frecuencias de letras en ambos grupos:" + fusionTotal);

}
 
// Desafío 5: Simulación de pipeline ETL con logging y errores
 
// Enunciado:  

// Simula un stream de String que representan transacciones bancarias: "ID, monto, tipo(DEP/WD)"  

// Ejemplo: "T1001,250.50,DEP", "T1002,0,WD" (monto 0 inválido).  

// Procesa validando:  

// - Si monto <= 0 → lanza una excepción chequeada personalizada, pero captúrala dentro del stream y loguea el error sin detener el flujo.  

// - Convierte a Transaction objeto.  

// - Filtra solo DEP y suma montos agrupando por ID (en realidad agrupa por rango de montos: <100, 100-500, >500).
 
// Requisito técnico:  

// Usar flatMap para manejo de errores con Try (de Vavr o simular con Optional), groupingBy con clasificador personalizado.
 
// ---
static void desafio5(){
    System.out.println("\n> Desafío 5");
    //Genero una clase que herede de Exception al final 

    //Lista simulada de transacciones
    List<String> transacciones = Arrays.asList(
        "T1001,250.50,DEP",
        "T1002,0,WD",        
        "T1003,-10,DEP",      
        "T1004,520.70,DEP",  
        "T1005,80.00,DEP",    
        "T1006,100.00,WD",    
        "T1007,150.00,DEP"    
    );

    Map<String, Double> montosPorRango = transacciones.stream()
        .flatMap(registro -> {
            try{
                Transaccion obj = Transaccion.fromString(registro);
                return Stream.of(obj);
            } catch (MontoInvalidoException e){
                System.out.println("Error: " + e.getMessage());
                return Stream.empty(); 
            }
        })
        .filter(transaccion -> transaccion.tipo.equals("DEP"))
        .collect(Collectors.groupingBy(
            tx -> HelperDesafio5.rango(tx.monto), 
            Collectors.summingDouble(tx -> tx.monto)
        ));
        

    System.out.println(montosPorRango);
}

// Desafío 6: Custom Collector para mediana y moda
 
// Enunciado:  

// Implementa un collector personalizado que calcule simultáneamente:  

// - Mediana de una lista de Double.  

// - Moda (valor más frecuente).  
 
// Luego aplícalo a un stream de 1 millón de números aleatorios.
 
// Requisito técnico:  

// Crear Collector<T, A, R> manual con Supplier, Accumulator, Combiner, Finisher.
 
// ---

static void desafio6 (){
    System.out.println("\n> Desafío 6");

    List<Double> unMillon = new Random().doubles(1_000_000)
        .boxed()
        .collect(Collectors.toList());
    Estadisticas resultado = unMillon.stream().collect(new CollectorMedianaModa());
    System.out.println(resultado);

}

 
// Desafío 7: Ventana deslizante temporal sobre logs
 
// Enunciado:  

// Tienes un archivo enorme de logs (no cabe en memoria). Cada línea: timestamp, usuario, acción.  

// Usa Files.lines() para procesar en streaming.  

// Define una ventana deslizante de 5 minutos: por cada nuevo evento, calcula cuántas acciones realizó el mismo usuario en los últimos 5 minutos.  

// Imprime solo si supera 100 acciones en la ventana.
 
// Requisito técnico:  

// Usar PriorityQueue dentro de un collect personalizado o map con estado.
 
// ---

static void desafio7() {
    System.out.println("\n> Desafío 7");

    Map<String, PriorityQueue<Evento>> ventanas = new HashMap<>();

     try (Stream<String> lineas = Files.lines(Paths.get("logs.txt"))) {
        lineas.map(Evento::parse)
            .forEach(evt -> {
                PriorityQueue<Evento> ventana = ventanas.computeIfAbsent(
                    evt.usuario,
                    k -> new PriorityQueue<>(Comparator.comparing(e -> e.timestamp))
                );

                while (!ventana.isEmpty() &&
                    ventana.peek().timestamp.isBefore(evt.timestamp.minusMinutes(5))){
                        ventana.poll();
                }

                ventana.offer(evt);

                if (ventana.size() > 3){
                    System.out.println("Más de 100 acciones en ventana: " + evt);
                }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
}
 
// Desafío 8: Stream de combinaciones y permutaciones
 
// Enunciado:  

// Dada una lista de enteros [1,2,3,4,5], genera todas las combinaciones de tamaño 3 (no repetición).  

// Para cada combinación, calcula:  

// - Suma de elementos.  

// - Producto.  

// - ¿Es la suma un número primo?  
 
// Filtra solo combinaciones donde el producto sea mayor que 10 y la suma sea par.  

// Devuelve el resultado como List<int[]>.
 
// Requisito técnico:  

// Usar IntStream.range + flatMap anidados para generar combinaciones.  

// Nada de bucles for explícitos.
 
// ---


static void desafio8(){
    System.out.println("\n> Desafío 8");
    // Lista dada 
    List<Integer> lista = Arrays.asList(1,2,3,4,5);

    // Reutilización del metodo para primos del desafío 2
    Analisis analizador = new Analisis();

        //Combinaciones
        List<int[]> combinaciones = IntStream.range(0, lista.size()).boxed()
            .flatMap(i -> IntStream.range(i + 1 , lista.size()).boxed()
                .flatMap(j -> IntStream.range(j + 1, lista.size())
                    .mapToObj(k -> new int[]{lista.get(i), lista.get(j), lista.get(k)})
                )
            )
            .map(arr -> new Object[]{
                arr,
                Arrays.stream(arr).sum(),
                Arrays.stream(arr).reduce(1, (a,b) -> a * b)
            })
            .filter(obj -> (int) obj[2] > 10 && ((int) obj[1]) % 2 == 0)
            .peek(obj -> System.out.println(
                "Combinación después del filtro: " + Arrays.toString((int[]) obj[0]) +
                ", suma: " + obj[1] +
                ", producto: " + obj[2] + 
                ", ¿la suma es primo?: " + analizador.esPrimo((int) obj[1])
            ))
            .map(obj -> (int[]) obj[0])
            .collect(Collectors.toList());
}


// Desafío 9: Cache con expiración usando Streams
 
// Enunciado:  

// Implementa un caché simple Map<String, Long> donde el valor es el timestamp de expiración (now + TTL).  

// Cada vez que accedes, debes:  

// - Filtrar entradas no expiradas.  

// - Devolver el valor más recientemente insertado (por clave ordenada por inserción).  
 
// Hazlo usando un stream que opere en el entrySet y maneje la expiración.
 
// Requisito técnico:  

// Usar removeIf antes de cualquier operación, luego max con comparador personalizado.
 
// ---

static void desafio9(){
    System.out.println("\n> Desafío 9");
    

    Map<String, Long> cache = new LinkedHashMap<>();

    long now = System.currentTimeMillis();
    long TTL = 1000;  

    cache.put("A", now + TTL);
    cache.put("B", now + 2 * TTL);
    cache.put("C", now + 3 * TTL);
    cache.put("Z", now - 10000); // caducado

    System.out.println("Cache inicial: " + cache);

    cache.entrySet().removeIf(e -> e.getValue() <= System.currentTimeMillis());
    System.out.println("Cache después de limpiar expirados: " + cache);

    Map.Entry<String, Long> entradaMasReciente = cache.entrySet().stream()
    .max(Comparator.comparing(Map.Entry::getValue))
    .orElse(null);  

    if (entradaMasReciente != null) {
        System.out.println("Más reciente (sin limpiar expirados): " +
            entradaMasReciente.getKey() + " => " + entradaMasReciente.getValue());
    } else {
        System.out.println("Cache vacío");
    }

    long current = System.currentTimeMillis();

    Optional<Map.Entry<String, Long>> ultimaOpt = cache.entrySet().stream()
        .filter(e -> e.getValue() > current)
        .reduce((a, b) -> b);

    if (ultimaOpt.isPresent()) {
        Map.Entry<String, Long> ultima = ultimaOpt.get();
        System.out.println("Más recientemente insertada y no expirada: " +
            ultima.getKey() + " => " + ultima.getValue());
    } else {
        System.out.println("Cache vacío o todo expirado");
    }
}
 
// Desafío 10: Reducción compleja con fusiones
 
// Enunciado:  

// Tienes un stream de Venta (producto, cantidad, precioUnitario, fecha).  

// Debes agrupar por producto y calcular:  

// - Ingreso total (cantidad * precio).  

// - Mes con mayor ingreso para ese producto.  

// - Tendencia: comparar ingreso primer trimestre vs último trimestre.
 
// Devuelve Map<Producto, Resumen> donde Resumen es una clase con esos tres campos.
 
// Requisito técnico:  

// Usar Collectors.toMap con funciones de merge, Collectors.groupingBy anidado, maxBy con fecha, reducing.
 
// NOTA: subir sus archivos fuente al repositorio de curso java en su carpeta respectiva, favor de ahora si generar una rama de trabajo de master y 
// un pull request donde me pondrar como reviwer.

static void desafio10(){
    System.out.println("\n> Desafío 10");

    List<Venta> ventasEjemplo = Arrays.asList(
        new Venta("Manzana", 10, 2.5, LocalDate.of(2024, 1, 15)),
        new Venta("Manzana", 5, 3.0, LocalDate.of(2024, 3, 20)),
        new Venta("Manzana", 8, 2.8, LocalDate.of(2024, 12, 10)),
        new Venta("Pera", 7, 3.5, LocalDate.of(2024, 2, 28)),
        new Venta("Pera", 10, 3.2, LocalDate.of(2024, 10, 22)),
        new Venta("Pera", 15, 3.1, LocalDate.of(2024, 4, 12)),
        new Venta("Uva", 20, 2.0, LocalDate.of(2024, 1, 5)),
        new Venta("Uva", 5, 2.2, LocalDate.of(2024, 11, 19)),
        new Venta("Uva", 8, 2.3, LocalDate.of(2024, 6, 23))
    );
    // System.out.println(ventasEjemplo);

    Map<String, Map<Integer, Double>> ingresosPorProductoYMES = ventasEjemplo.stream()
    .collect(Collectors.groupingBy(
        v -> v.producto,
        Collectors.groupingBy(
            v -> v.fecha.getMonthValue(),
            Collectors.summingDouble(Venta::ingreso)
        )
    ));

    Map<String, Resumen> resumenPorProducto = ingresosPorProductoYMES.entrySet().stream()
    .collect(Collectors.toMap(
        Map.Entry::getKey,
        e -> {
            Map<Integer, Double> ingresosPorMes = e.getValue();

            double ingresosQ1 = ingresosPorMes.entrySet().stream()
                .filter(me -> me.getKey() >= 1 && me.getKey() <= 3)
                .mapToDouble(Map.Entry::getValue)
                .sum();

            double ingresosQ4 = ingresosPorMes.entrySet().stream()
                .filter(me -> me.getKey() >= 10 && me.getKey() <= 12)
                .mapToDouble(Map.Entry::getValue)
                .sum();

            
            double ingresoTotal = ingresosPorMes.values().stream().mapToDouble(Double::doubleValue).sum();

            
            int mesMayorIngreso = ingresosPorMes.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);

            String tendencia = ingresosQ4 > ingresosQ1 ? "Sube" :
                               ingresosQ4 < ingresosQ1 ? "Baja" : "Igual";
            return new Resumen(ingresoTotal, mesMayorIngreso, tendencia);
        }
    ));

    System.out.println("Resumen por producto:");
    resumenPorProducto.forEach((producto, resumen) -> {
        System.out.println("Producto: " + producto + " --> " + resumen);
    });
}

}

//Desafio 1 Generamos clase a modo de plantilla para la lista Persona
    class Persona{
        String nombre;
        int edad;
        String ciudad;
        Double salario;
        List<String> hobbies;

        //constructor
        Persona(String nombre, int edad, String ciudad, Double salario, List<String> hobbies) {
            this.nombre = nombre;
            this.edad = edad;
            this.ciudad = ciudad;
            this.salario = salario;
            this.hobbies = hobbies;
        }   

        //sobrescribimos para que no salga una refencia a la clase persona
        @Override
        public String toString() {
            return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", ciudad='" + ciudad + '\'' +
                ", salario=" + salario +
                ", hobbies=" + hobbies +
                '}';
        }

    }

//Clase Analisis para desafío 2
class Analisis {
    
    long paresContador = 0;
    long paresSuma = 0;
    int multiplo7 = 0;
    long primo = 0;

    void actualizar(int n){
        // obtener promedio numeros paresContador
        if(n % 2 == 0){
            paresContador++;
            paresSuma += n;
        } 
        //contar numeros primos
        if (esPrimo(n)){
            primo += 1;
        }
        //obtener multiplo de 7 mayor
        if(n % 7 == 0 && n > multiplo7){
            multiplo7 = n;
        }
        
    }
    //Función de apoyo para descubrir numeros primos
        boolean esPrimo(int n) {
            if (n <= 1) return false;
            if (n == 2) return true;
            if (n % 2 == 0) return false;
            int raiz = (int)Math.sqrt(n);
            for (int i = 3; i <= raiz; i += 2) {
                if (n % i == 0) return false;
            }
            return true;
        }
}

// Desafio 3 Clases de plantilla para la lista Empresa
// Clase Empleado
class Empleado {
    String nombre;
    double salario;

    Empleado(String nombre, double salario) {
        this.nombre = nombre;
        this.salario = salario;
    }

    @Override
    public String toString() {
        return nombre + " ($" + salario + ")";
    }
}

// Clase Departamento
class Departamento {
    String nombre;
    List<Empleado> empleados;

    Departamento(String nombre, List<Empleado> empleados) {
        this.nombre = nombre;
        this.empleados = empleados;
    }

    @Override
    public String toString() {
        return nombre + ": " + empleados.toString();
    }
}

// Clase Empresa
class Empresa {
    String nombre;
    List<Departamento> departamentos;

    Empresa(String nombre, List<Departamento> departamentos) {
        this.nombre = nombre;
        this.departamentos = departamentos;
    }

    @Override
    public String toString() {
        return nombre + " >> " + departamentos.toString();
    }
}

// Desafío 5 - Excepciones personalizadas
class MontoInvalidoException  extends Exception{
    public MontoInvalidoException(String mensaje) {
        super(mensaje);
    }
}

class Transaccion {
    String id;
    double monto;
    String tipo;

    public Transaccion(String id, double monto, String tipo) {
        this.id = id;
        this.monto = monto;
        this.tipo = tipo;
    }

    public static Transaccion fromString(String linea) throws MontoInvalidoException {
        String[] partes = linea.split(",");
        String id = partes[0].trim();
        double monto = Double.parseDouble(partes[1].trim());
        String tipo = partes[2].trim();

        if (monto <= 0) {
            throw new MontoInvalidoException("Monto inválido: " + monto + " en transacción: " + id);
        }

        return new Transaccion(id, monto, tipo);
    }

    @Override
    public String toString() {
        return "Transaccion{id='" + id + "', monto=" + monto + ", tipo='" + tipo + "'}";
    }
}

class HelperDesafio5 {
    static String rango(double monto) {
        if (monto < 100) return "<100";
        else if (monto <= 500) return "100-500";
        else return ">500";
    }
}

//-------- Desafío 6
// Clase para plantear los valores numéricos de moda y mediana
class Estadisticas {
    double mediana;
    double moda;

    Estadisticas(double mediana, double moda){
        this.mediana = mediana;
        this.moda = moda;
    }

    @Override
    public String toString(){
        return "Mediana: " + mediana + ", Moda: " + moda;
    }
}

//clase acumulador
class AcumuladorMedianaModa{
    List<Double> valores = new ArrayList<>();
    Map<Double, Integer> frecuencias = new HashMap<>();
}

//Collector <T, A, R >
class CollectorMedianaModa implements Collector<Double, AcumuladorMedianaModa, Estadisticas> {
    
    @Override
    public Supplier<AcumuladorMedianaModa> supplier() {
        return AcumuladorMedianaModa::new;
    }

    @Override
    public BiConsumer<AcumuladorMedianaModa, Double> accumulator() {
        return (acum, value) -> {
            acum.valores.add(value);
            acum.frecuencias.merge(value, 1, Integer::sum);
        };
    }

    @Override
    public BinaryOperator<AcumuladorMedianaModa> combiner() {
        return (a1, a2) -> {
            a1.valores.addAll(a2.valores);
            a2.frecuencias.forEach((k, v) -> a1.frecuencias.merge(k, v, Integer::sum));
            return a1;
        };
    }

    @Override
    public Function<AcumuladorMedianaModa, Estadisticas> finisher() {
        return acum -> {
            List<Double> ordenados = new ArrayList<>(acum.valores);
            Collections.sort(ordenados);

            int n = ordenados.size();
            double mediana;
            if (n == 0) {
                mediana = Double.NaN;
            } else if (n % 2 == 0) {
                mediana = (ordenados.get(n/2 - 1) + ordenados.get(n/2)) / 2;
            } else {
                mediana = ordenados.get(n/2);
            }

            Double moda = acum.frecuencias.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Double.NaN);

            return new Estadisticas(mediana, moda);
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}

// ----- Clases auxiliares para el desfío 7
class Evento {
    LocalDateTime timestamp;
    String usuario;
    String accion;

    Evento(LocalDateTime timestamp, String usuario, String accion){
        this.timestamp = timestamp;
        this.usuario = usuario;
        this.accion = accion;
    }

    static Evento parse(String linea){
        String[] partes = linea.split(",");
        LocalDateTime timestamp = LocalDateTime.parse(partes[0]);
        String usuario = partes[1];
        String accion = partes[2];
        return new Evento(timestamp, usuario, accion);
    }

    @Override
    public String toString() {
        return "Evento{" +
            "timestamp=" + timestamp +
            ", usuario='" + usuario + '\'' +
            ", accion='" + accion + '\'' +
            '}';
    }
}

// clases de apoyo para el desafío 10
class Venta {
    String producto;       
    int cantidad;
    double precioUnitario;
    LocalDate fecha;

    Venta(String producto, int cantidad, double precioUnitario, LocalDate fecha) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
    }

    double ingreso() {
        return cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return "Venta{" + producto + ", " + cantidad + ", " + precioUnitario + ", " + fecha + '}';
    }
}
class Resumen {
    double ingresoTotal;
    int mesMayorIngreso;         
    String tendencia;            // "Sube", "Baja", "Igual"

    Resumen(double ingresoTotal, int mesMayorIngreso, String tendencia) {
        this.ingresoTotal = ingresoTotal;
        this.mesMayorIngreso = mesMayorIngreso;
        this.tendencia = tendencia;
    }

    @Override
    public String toString() {
        return "Resumen{ingresoTotal=" + ingresoTotal +
               ", mesMayorIngreso=" + mesMayorIngreso +
               ", tendencia='" + tendencia + '\'' +
               '}';
    }
}
