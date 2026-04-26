package desktop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Ejemplo de aplicación de escritorio con JavaFX
 * - Stage, Scene, VBox
 * - Button, TextField, Label
 * - Event handlers
 *
 * Nota: Requiere JavaFX SDK en el classpath
 */
public class EjemploJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear componentes
        Label label = new Label("Ingresa tu nombre:");
        TextField textField = new TextField();
        Button button = new Button("Saludar");

        // Layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(label, textField, button);

        // Event handler
        button.setOnAction(e -> {
            String nombre = textField.getText();
            label.setText("¡Hola, " + nombre + "!");
        });

        // Escena y stage
        Scene scene = new Scene(vbox, 300, 150);
        primaryStage.setTitle("Ejemplo JavaFX - Aplicación de Escritorio");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}