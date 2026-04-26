package desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ejemplo de aplicación de escritorio con Swing
 * - JFrame, JPanel, JButton, JTextField
 * - Layout managers
 * - Modelo de delegación de eventos
 */
public class EjemploSwing extends JFrame {

    private JTextField textField;
    private JButton button;
    private JLabel label;

    public EjemploSwing() {
        // Configuración básica de la ventana
        setTitle("Ejemplo Swing - Aplicación de Escritorio");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        // Crear componentes
        textField = new JTextField(20);
        button = new JButton("Saludar");
        label = new JLabel("Ingresa tu nombre:");

        // Panel principal con BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior para el label y textfield
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(label);
        topPanel.add(textField);

        // Panel inferior para el botón
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(button);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Agregar panel a la ventana
        add(panel);

        // Event listener para el botón
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField.getText();
                JOptionPane.showMessageDialog(EjemploSwing.this,
                    "¡Hola, " + nombre + "!", "Saludo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        // Ejecutar en EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            new EjemploSwing().setVisible(true);
        });
    }
}