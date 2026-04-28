/*
Ejercicio: Pirámide de números "escalera" con reglas cambiantes
Escribe un programa en Java que:
 
Solicite al usuario un número entero n (altura de la pirámide, entre 1 y 20).
 
Solicite un carácter opcion que puede ser '1', '2' o '3'.
 
Genere e imprima una pirámide según la opción:

1.	Pirámide de asteriscos centrada ejemplo: 
    *<br> 
   ***<br>
  *****<br>
 *******
2.	Pirámide de números donde cada fila i contiene el número i repetido i veces, 
centrada	ejemplo: 
1<br> 
2 2<br>
3 3 3<br> 
4 4 4 4
3.	Pirámide de números multiplicador: 
cada fila i contiene números desde i hasta i*i con incrementos de i. 
Centrada.	ejemplo: Para i=3: 3 6 9
Para n=4: 3<br> 4 8<br> 5 10 15<br>6 12 18 24
*/



import java.util.Scanner;

public class PiramidBuilder{
    public static void main(String[] args){
        //Mensajes de bienvenida
        System.out.println("\n Bienvenido al generador de pirámides \n");
        System.out.println("¿Qué quieres hacer?\nSelecciona una opción:\n");
        System.out.println("1- Pirámide de asteriscos centrada");
        System.out.println("2- Pirámide de números sencilla");
        System.out.println("3- Pirámide de números multiplicador");

        // Entrada del usuario 
        System.out.print("\nIngresa solo el número:\n>");
        Scanner scanner = new Scanner(System.in);

        // System.out.print(">");
        int opcion = scanner.nextInt();
        

        //Llamada a métodos
        if (opcion == 1){
            piramideAsteriscos();
        } else if(opcion == 2){
            piramideNumeros();
        } else if (opcion == 3){
            priamideMultiplicador();
        } else {
            System.out.println("Opción no válida, prueba de nuevo");
        }
    }

    public static void piramideAsteriscos(){
        System.out.println("\nPirámide de asteríscos centrada");
        System.out.print("Ingresa la altura de la piramide, (1 a 20)\n");

        Scanner scanner = new Scanner(System.in);
        System.out.print(">");
        int opcion = scanner.nextInt();
        scanner.close();

        for(int i = 1; i <= opcion; i++){
            String line = "";
            line = " ".repeat(opcion - i) + "*".repeat((i * 2) -1);
            System.out.println(line);
        }
    }

    public static void piramideNumeros(){
        System.out.println("\nPirámide de números centrada");
        System.out.print("Ingresa la altura de la piramide, (1 a 20)\n");

        Scanner scanner = new Scanner(System.in);
        System.out.print(">");
        int opcion = scanner.nextInt();
        scanner.close();

        String line = "";
        for (int i = 1; i <= opcion; i++){
            line = " ".repeat(opcion - i) + String.valueOf(i).repeat(i);
            System.out.println(line);
        }
    }

    public static void priamideMultiplicador(){
        System.out.println("\nPirámide de números multiplicadora");
        System.out.print("Ingresa la altura de la piramide, (1 a 20)\n");

        Scanner scanner = new Scanner(System.in);
        System.out.print(">");
        int opcion = scanner.nextInt();
        scanner.close();

        for (int i = 1; i <= opcion; i++){
            StringBuilder sb = new StringBuilder();
            sb.append(" ".repeat(opcion - i));
            for(int sI = i; sI <= i*i; sI += i){
                sb.append(sI + " ");
            }
            String line = sb.toString();
            System.out.println(line);
        }
    }

}