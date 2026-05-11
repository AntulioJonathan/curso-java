public class Inquisidor {
    public static void main(String args[]){
        //Numero aleatorio
        var numero  = (int)(Math.random() * 6) + 1;

        //Switch casos con nombres de mis compañeros
        switch (numero){
            case 1:
                System.out.println("Jesús");
                break;
            case 2: 
                System.out.println("Marco");
                break;
            case 3: 
                System.out.println("Hazel");
                break;
            case 4: 
                System.out.println("Sebastian");
                break;
            case 5: 
                System.out.println("Daniel");
                break;
            case 6: 
                System.out.println("Ezequiel");   
                break;
        }
    }
}