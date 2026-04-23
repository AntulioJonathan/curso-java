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
}
