import java.util.Scanner;

public class EjercicioPiramides {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int altura = pedirAltura(scanner);
        if (altura < 1 || altura > 20) {
            System.out.println("La altura debe estar entre 1 y 20.");
            scanner.close();
            return;
        }
        mostrarMenu();
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                imprimirPiramideAsteriscos(altura);
                break;

            case 2:
                imprimirPiramideNumeros(altura);
                break;

            case 3:
                imprimirPiramideMultiplicacion(altura);
                break;

            default:
                System.out.println("Opcion no valida.");
                break;
        }
        scanner.close();
    }

    public static int pedirAltura(Scanner scanner) {
        System.out.print("Ingresa la altura de la piramide (1 a 20): ");
        return scanner.nextInt();
    }

    public static void mostrarMenu() {
        System.out.println("Elige una opcion:");
        System.out.println("1.Piramide de asteriscos");
        System.out.println("2.Piramide de numeros repetidos");
        System.out.println("3.Piramide de numeros multiplicados");
        System.out.print("Opcion: ");
    }

    public static void imprimirEspacios(int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            System.out.print(" ");
        }
    }

    public static void imprimirPiramideAsteriscos(int altura) {
        for (int fila = 1; fila <= altura; fila++) {
            imprimirEspacios(altura - fila);

            for (int columna = 1; columna <= (fila * 2 - 1); columna++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }

    public static void imprimirPiramideNumeros(int altura) {
        for (int fila = 1; fila <= altura; fila++) {
            imprimirEspacios(altura - fila);

            for (int columna = 1; columna <= fila; columna++) {
                System.out.print(fila + " ");
            }

            System.out.println();
        }
    }

    public static void imprimirPiramideMultiplicacion(int altura) {
        for (int fila = 1; fila <= altura; fila++) {
            imprimirEspacios(altura - fila);

            for (int columna = 1; columna <= fila; columna++) {
                System.out.print((fila * columna) + " ");
            }

            System.out.println();
        }
    }
}