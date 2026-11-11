import java.util.Locale;
import java.util.Scanner;

public class SistemaCLI {

    private Scanner sc = new Scanner(System.in);


    public int menu(){
        System.out.println("\n--- MENU DE FIGURAS ---");
        System.out.println("1. Circulo");
        System.out.println("2. Rectangulo");
        System.out.println("3. Triangulo");
        System.out.println("4. Salir");
        System.out.print("Escoja una opcion: ");
        int opcion = sc.nextInt();
        return opcion;
    }

    public void circulo() {
        System.out.print("Ingrese el radio: ");
        double radio = sc.nextDouble();

        Circulo circulo = new Circulo(radio);
        mostrarResultados(circulo);
        System.out.printf(Locale.US, "Diametro: %.2f%n", circulo.calcularDiametro());
    }

    public void rectangulo() {
        System.out.print("Ingrese la base: ");
        double base = sc.nextDouble();
        System.out.print("Ingrese la altura: ");
        double altura = sc.nextDouble();

        Rectangulo rectangulo = new Rectangulo(base, altura);
        mostrarResultados(rectangulo);
    }

    public void triangulo() {
        System.out.print("Ingrese el primer lado: ");
        double lado1 = sc.nextDouble();
        System.out.print("Ingrese el segundo lado: ");
        double lado2 = sc.nextDouble();
        System.out.print("Ingrese el tercer lado: ");
        double lado3 = sc.nextDouble();

        Triangulo triangulo = new Triangulo(lado1, lado2, lado3);
        mostrarResultados(triangulo);
    }

    private void mostrarResultados(Figura figura) {
        // Recibe cualquier hija y ejecuta sus calculos mediante la clase padre.
        System.out.println("\nFigura: " + figura.getNombre());
        System.out.println("Numero de lados: " + figura.getLados());
        System.out.printf(Locale.US, "Area: %.2f%n", figura.calcularArea());
        System.out.printf(Locale.US, "Perimetro: %.2f%n", figura.calcularPerimetro());
    }

    public void salir() {
        System.out.println("Programa finalizado.");
    }
}
