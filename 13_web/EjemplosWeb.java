package web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Ejemplos de Desarrollo Web con Java
 * - Servlets básicos
 * - REST con Spring Boot (conceptual)
 */

public class EjemplosWeb {
    
    /**
     * SERVLET SIMPLE (sin framework)
     * 
     * Un servlet es una clase que procesa peticiones HTTP
     * En una aplicación real se usaría dentro de un contenedor (Tomcat, etc.)
     */
    
    // Simulación de un servlet (ver abajo para ejemplo real)
    abstract static class HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException { }
        
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException { }
    }
    
    // Clases simuladas de petición/respuesta
    interface HttpServletRequest {
        String getParameter(String name);
        String getPath();
    }
    
    interface HttpServletResponse {
        void setContentType(String type);
        void setStatus(int code);
        PrintWriter getWriter();
    }
    
    /**
     * EJEMPLO DE SERVLET
     */
    static class HolaServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
            resp.setContentType("text/html; charset=UTF-8");
            resp.setStatus(200);
            
            PrintWriter out = resp.getWriter();
            out.println("<html>");
            out.println("<head><title>Hola</title></head>");
            out.println("<body>");
            out.println("<h1>¡Bienvenido al Servlet!</h1>");
            out.println("<p>Esta es una respuesta HTTP</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== DESARROLLO WEB CON JAVA ===\n");
        
        ejemploArquitecturaWeb();
        ejemploConceptoREST();
        ejemploSpringBootConceptual();
        ejemploServidorTCP();
        ejemploJEEConceptos();
        ejemploEJBConceptual();
        ejemploJPAConceptual();
        ejemploJMSConceptual();
    }
    
    // Ejemplo 1: Arquitectura Web
    static void ejemploArquitecturaWeb() {
        System.out.println("--- Arquitectura Web MVC ---");
        System.out.println("1. MODEL: Gestiona datos (clases de negocio)");
        System.out.println("2. VIEW: Presenta los datos (HTML, templates)");
        System.out.println("3. CONTROLLER: Procesa peticiones (Servlets, @RestController)");
        
        // Simulación
        System.out.println("\nFlujo:");
        System.out.println("  1. Usuario hace petición GET /usuarios");
        System.out.println("  2. Controller (@RequestMapping) recibe la petición");
        System.out.println("  3. Model obtiene usuarios de la BD");
        System.out.println("  4. View renderiza la respuesta HTML o JSON");
        System.out.println("  5. Respuesta se envía al navegador\n");
    }
    
    // Ejemplo 2: Conceptos REST
    static void ejemploConceptoREST() {
        System.out.println("--- Principios REST ---");
        System.out.println("REST = Representational State Transfer");
        System.out.println("\nVerbos HTTP:");
        System.out.println("  GET    /usuarios        (Obtener lista)");
        System.out.println("  GET    /usuarios/1      (Obtener uno)");
        System.out.println("  POST   /usuarios        (Crear nuevo)");
        System.out.println("  PUT    /usuarios/1      (Actualizar completo)");
        System.out.println("  PATCH  /usuarios/1      (Actualizar parcial)");
        System.out.println("  DELETE /usuarios/1      (Eliminar)");
        
        System.out.println("\nCódigos de estado HTTP:");
        System.out.println("  200: OK");
        System.out.println("  201: Created");
        System.out.println("  400: Bad Request");
        System.out.println("  404: Not Found");
        System.out.println("  500: Internal Server Error\n");
    }
    
    // Ejemplo 3: Spring Boot conceptual
    static void ejemploSpringBootConceptual() {
        System.out.println("--- Spring Boot REST API ---");
        
        System.out.println("""
            // Controlador Spring
            @RestController
            @RequestMapping("/api/usuarios")
            public class UsuarioController {
                
                @Autowired
                private UsuarioService usuarioService;
                
                // GET /api/usuarios
                @GetMapping
                public List<Usuario> obtenerTodos() {
                    return usuarioService.obtenerTodos();
                }
                
                // GET /api/usuarios/{id}
                @GetMapping("/{id}")
                public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
                    return usuarioService.obtenerPorId(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
                }
                
                // POST /api/usuarios
                @PostMapping
                public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
                    Usuario guardado = usuarioService.guardar(usuario);
                    return ResponseEntity.status(201).body(guardado);
                }
                
                // PUT /api/usuarios/{id}
                @PutMapping("/{id}")
                public ResponseEntity<Usuario> actualizar(
                    @PathVariable Long id,
                    @RequestBody Usuario usuario) {
                    return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
                }
                
                // DELETE /api/usuarios/{id}
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> eliminar(@PathVariable Long id) {
                    usuarioService.eliminar(id);
                    return ResponseEntity.noContent().build();
                }
            }
            """);
    }
    
    // Ejemplo 4: Servidor TCP simple
    static void ejemploServidorTCP() {
        System.out.println("--- Servidor TCP Simple ---");
        System.out.println("Nota: Este es un ejemplo conceptual\n");
        
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Servidor escuchando en puerto 8080...");
            
            // Esperar una conexión (con timeout corto para no bloquear)
            serverSocket.setSoTimeout(1000);
            Socket socket = null;
            
            try {
                socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());
                
                // Leer petición
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input));
                String linea = reader.readLine();
                System.out.println("Petición recibida: " + linea);
                
                // Enviar respuesta HTTP
                OutputStream output = socket.getOutputStream();
                String respuesta = "HTTP/1.1 200 OK\\r\\n" +
                                 "Content-Type: text/html\\r\\n" +
                                 "Content-Length: 28\\r\\n" +
                                 "\\r\\n" +
                                 "<h1>¡Hola desde el servidor!</h1>";
                output.write(respuesta.getBytes());
                output.flush();
                
            } catch (java.net.SocketTimeoutException e) {
                System.out.println("Timeout - No hay conexiones en este momento");
            } finally {
                if (socket != null) socket.close();
                serverSocket.close();
            }
            
        } catch (IOException e) {
            System.out.println("Error del servidor: " + e.getMessage());
        }
    }

    // Ejemplo 5: Conceptos JEE (Jakarta EE)
    static void ejemploJEEConceptos() {
        System.out.println("--- Java EE / Jakarta EE ---");
        System.out.println("Plataforma para aplicaciones empresariales");
        System.out.println("\nTecnologías principales:");
        System.out.println("• EJB (Enterprise Java Beans): Componentes de negocio");
        System.out.println("• JPA (Java Persistence API): ORM estándar");
        System.out.println("• JTA (Transaction API): Transacciones distribuidas");
        System.out.println("• CDI (Contexts and Dependency Injection): Inyección de dependencias");
        System.out.println("• JMS (Java Message Service): Mensajería asíncrona");
        System.out.println("• JAX-RS: APIs RESTful");
        System.out.println("• Servlets/JSP: Web tradicional");
        System.out.println("\nContenedores: WildFly, GlassFish, Payara");
        System.out.println("Alternativa moderna: Spring Framework (más ligero)\n");
    }

    // Ejemplo 6: EJB conceptual
    static void ejemploEJBConceptual() {
        System.out.println("--- Enterprise Java Beans (EJB) ---");
        System.out.println("""
            Tipos de EJB:
            • Session Beans: Lógica de negocio
              - Stateless: Sin estado, pool de instancias
              - Stateful: Con estado, una instancia por cliente
              - Singleton: Una instancia compartida

            • Message-Driven Beans (MDB): Procesan mensajes JMS

            Ejemplo conceptual:

            @Stateless
            public class CalculadoraEJB {
                public double sumar(double a, double b) {
                    return a + b;
                }
            }

            @Singleton
            public class ContadorEJB {
                private int contador = 0;

                public int incrementar() {
                    return ++contador;
                }
            }
            """);
    }

    // Ejemplo 7: JPA conceptual
    static void ejemploJPAConceptual() {
        System.out.println("--- Java Persistence API (JPA) ---");
        System.out.println("""
            ORM estándar para Java EE

            @Entity
            @Table(name = "usuarios")
            public class Usuario {
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                @Column(name = "nombre", nullable = false)
                private String nombre;

                @Column(name = "email", unique = true)
                private String email;

                // Getters y setters...
            }

            @Repository
            public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
                List<Usuario> findByNombre(String nombre);
                Optional<Usuario> findByEmail(String email);
            }

            Servicio:

            @Service
            public class UsuarioService {
                @Autowired
                private UsuarioRepository repository;

                @Transactional
                public Usuario guardar(Usuario usuario) {
                    return repository.save(usuario);
                }
            }
            """);
    }

    // Ejemplo 8: JMS conceptual
    static void ejemploJMSConceptual() {
        System.out.println("--- Java Message Service (JMS) ---");
        System.out.println("""
            Mensajería asíncrona

            Producer (envía mensaje):

            @Inject
            private JMSContext context;

            @Resource(lookup = "java:/jms/queue/MiCola")
            private Queue queue;

            public void enviarMensaje(String mensaje) {
                context.createProducer().send(queue, mensaje);
            }

            Consumer (Message-Driven Bean):

            @MessageDriven(
                activationConfig = {
                    @ActivationConfigProperty(
                        propertyName = "destination",
                        propertyValue = "java:/jms/queue/MiCola"
                    )
                }
            )
            public class ProcesadorMensajes implements MessageListener {
                @Override
                public void onMessage(Message message) {
                    try {
                        String contenido = message.getBody(String.class);
                        System.out.println("Mensaje recibido: " + contenido);
                        // Procesar mensaje...
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
            """);
    }
}
