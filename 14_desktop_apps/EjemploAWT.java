package desktop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ejemplo de aplicación de escritorio con AWT
 * - Frame, Button, TextField, Label
 * - Layout managers
 * - Event listeners
 *
 * Nota: AWT es obsoleto para proyectos nuevos, usar Swing o JavaFX
 */
public class EjemploAWT extends Frame implements ActionListener {

    private TextField textField;
    private Button button;
    private Label label;

    public EjemploAWT() {
        // Configuración básica
        setTitle("Ejemplo AWT - Aplicación de Escritorio");
        setSize(400, 200);
        setLayout(new FlowLayout());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Crear componentes
        label = new Label("Ingresa tu nombre:");
        textField = new TextField(20);
        button = new Button("Saludar");

        // Agregar componentes
        add(label);
        add(textField);
        add(button);

        // Event listener
        button.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = textField.getText();
        label.setText("¡Hola, " + nombre + "!");
    }

    public static void main(String[] args) {
        new EjemploAWT();
    }
}